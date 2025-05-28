package writeon.api.auth.service;

import com.amplitude.Amplitude;
import com.amplitude.Event;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import writeon.api.auth.helper.JwtHelper;
import writeon.api.auth.helper.MailHelper;
import writeon.api.auth.helper.MemberHelper;
import writeon.api.auth.request.*;
import writeon.api.auth.response.LoginFailResponse;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.DateTimeUtil;
import writeon.api.terms.request.TermsAgreeRequest;
import writeon.api.terms.service.TermsQueryService;
import writeon.domain.auth.*;
import writeon.domain.auth.dto.AuthTokenDto;
import writeon.domain.auth.enums.AuthException;
import writeon.domain.auth.enums.LoginAttemptResultType;
import writeon.domain.auth.repository.ChangePasswordTokenRedisRepository;
import writeon.domain.auth.repository.JoinTokenRedisRepository;
import writeon.domain.auth.repository.LoginAttemptJpaRepository;
import writeon.domain.auth.repository.RefreshTokenRedisRepository;
import writeon.domain.common.MemberSession;
import writeon.domain.member.Member;
import writeon.domain.member.MemberPassword;
import writeon.domain.member.repository.MemberJpaRepository;
import writeon.domain.member.repository.MemberPasswordJpaRepository;
import writeon.domain.terms.TermsAgreement;
import writeon.domain.terms.enums.TermsCode;
import writeon.domain.terms.repository.TermsAgreeJpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthCommandService {
    private final AuthQueryService authQueryService;
    private final TermsQueryService termsQueryService;
    private final MemberJpaRepository memberJpaRepository;
    private final MemberPasswordJpaRepository memberPasswordJpaRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final JoinTokenRedisRepository joinTokenRedisRepository;
    private final ChangePasswordTokenRedisRepository changePasswordTokenRedisRepository;
    private final TermsAgreeJpaRepository termsAgreeJpaRepository;
    private final LoginAttemptJpaRepository loginAttemptJpaRepository;
    private final JwtHelper jwtHelper;
    private final MailHelper mailHelper;
    private final Amplitude amplitude = Amplitude.getInstance();

    @Value("${service.web.url}")
    private String WEB_URL;

    /**
     * 토큰 재발급
     */
    @Transactional
    public AuthTokenDto reissueToken(String tokenString) {
        // 리프래시 토큰이 비유효하면
        if (!jwtHelper.isTokenValid(tokenString)) {
            throw new BaseException(AuthException.REFRESH_TOKEN_NOT_VALID);
        }

        // 레디스 검사
        RefreshToken oldRefreshToken = refreshTokenRedisRepository.findById(tokenString)
                .orElseThrow(() -> new BaseException(AuthException.REFRESH_TOKEN_NOT_VALID));
        this.invalidateToken(oldRefreshToken);

        JwtPayload payload = jwtHelper.getPayload(tokenString);
        return generateAuthTokens(payload.getMemberId());
    }

    /**
     * 로그인
     */
    public AuthTokenDto login(LoginRequest request) {
        Member member = memberJpaRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(AuthException.LOGIN_FAILED));
        MemberPassword memberPassword = memberPasswordJpaRepository.findById(member.getId())
                .orElseThrow(() -> new BaseException(AuthException.AUTH_FAILED_BY_UNKNOWN_ERROR));

        List<LoginAttempt> lastFiveAttempts = loginAttemptJpaRepository.findTop5ByEmailOrderByCreatedAtDesc(request.getEmail());
        if (!lastFiveAttempts.isEmpty()) {
            LoginAttempt lastAttempt = lastFiveAttempts.getFirst();
            // 로그인 시도가 블락 상태이고, 블락 시간으로부터 1시간이 지나지 않은 경우
            if (lastAttempt.getResult() == LoginAttemptResultType.BLOCKED && lastAttempt.getCreatedAt().isAfter(LocalDateTime.now().minusHours(1))
            ) {
                LocalDateTime loginAvailableAt = lastAttempt.getCreatedAt().plusHours(1);
                throw new BaseException(
                        AuthException.LOGIN_BLOCKED,
                        new LoginFailResponse(0, loginAvailableAt),
                        String.format("로그인 5회 실패로 [%s]까지 접속이 제한됩니다.", loginAvailableAt.format(DateTimeFormatter.ofPattern(DateTimeUtil.DATETIME_HOUR_ONLY_KR_PATTERN)))
                );
            }
        }


        LoginAttempt newLoginAttempt = new LoginAttempt();
        newLoginAttempt.setEmail(request.getEmail());

        final boolean isPasswordCorrect = BCrypt.checkpw(request.getPassword(), memberPassword.getPassword());
        if (!isPasswordCorrect) {
            int attemptsRemaining = 5 - 1;
            // 남은 시도 횟수 계산
            for (LoginAttempt attempt : lastFiveAttempts) {
                if (attempt.getResult() != LoginAttemptResultType.FAILED) break;
                attemptsRemaining -= 1;
            }

            newLoginAttempt.setResult(
                    attemptsRemaining == 0
                            ? LoginAttemptResultType.BLOCKED
                            : LoginAttemptResultType.FAILED
            );
            loginAttemptJpaRepository.save(newLoginAttempt);
            if (attemptsRemaining == 0) {
                LocalDateTime now = LocalDateTime.now();
                throw new BaseException(
                        AuthException.LOGIN_BLOCKED,
                        new LoginFailResponse(0, now),
                        String.format("로그인 5회 실패로 [%s]까지 접속이 제한됩니다.", now.format(DateTimeFormatter.ofPattern(DateTimeUtil.DATETIME_HOUR_ONLY_KR_PATTERN)))
                );
            } else if (attemptsRemaining > 2) {
                throw new BaseException(AuthException.LOGIN_FAILED, new LoginFailResponse(attemptsRemaining, null));
            } else {
                throw new BaseException(AuthException.LOGIN_FAILED_WITH_WARNING, new LoginFailResponse(attemptsRemaining, null));
            }
        }

        newLoginAttempt.setResult(LoginAttemptResultType.SUCCEED);
        loginAttemptJpaRepository.save(newLoginAttempt);
        return generateAuthTokens(memberPassword.getMemberId());
    }

    /**
     * 로그아웃
     */
    @Transactional
    public void logout() {
        MemberSession memberSession = MemberHelper.getMemberSession();

        refreshTokenRedisRepository.findByMemberId(memberSession.getMemberId())
                .ifPresent(this::invalidateToken);
    }

    /**
     * 회원 가입
     */
    @Transactional
    public void join(JoinRequest request) {
        // 필수 약관이 모두 포함되어있는지 검사
        List<TermsCode> agreedTermsList = request.getTermsList()
                .stream()
                .filter(TermsAgreeRequest::getIsAgreed)
                .map(TermsAgreeRequest::getTermsCd)
                .toList();

        // 필수 약관 검사
        if (!termsQueryService.isContainingAllRequiredTerms(agreedTermsList)) {
            throw new BaseException(AuthException.TERMS_AGREE_REQUIRED);
        }
        // 이메일 중복 검사
        if (memberJpaRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BaseException(AuthException.JOIN_ALREADY_EXIST_MEMBER);
        }
        // 닉네임 중복 검사
        if (memberJpaRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new BaseException(AuthException.NICKNAME_ALREADY_EXIST);
        }

        // 회원 정보 설정
        String passwordHash = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        Member member = Member.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .build();
        MemberPassword memberPassword = MemberPassword.builder()
                .memberId(member.getId())
                .password(passwordHash)
                .build();
        List<TermsAgreement> termsAgreementList = agreedTermsList.stream()
                .map(t -> TermsAgreement.builder()
                        .termsCd(t)
                        .memberId(member.getId())
                        .build()
                ).toList();
        String jwtString = jwtHelper.generateJoinToken(
                JwtPayload.builder().memberId(member.getId()).build()
        );

        // joinToken redis 저장
        JoinToken joinToken = new JoinToken(jwtString, member, memberPassword, termsAgreementList);
        joinTokenRedisRepository.save(joinToken);

        try {
            // 이메일 전송
            mailHelper.send(
                    MailHelper.MailType.JOIN,
                    request.getEmail(),
                    MailHelper.MailData.builder()
                            .nickname(request.getNickname())
                            .linkUrl( WEB_URL + "/join/complete?joinToken=" + joinToken.getTokenString() )
                            .build()
            );
        } catch (MessagingException e) {
            throw new BaseException(AuthException.MAIL_SEND_FAILED);
        }

        // Amplitude 이벤트 전송
        Event event = new Event("$identify", joinToken.getMember().getId().toString());
        event.userProperties = new JSONObject()
                .put("signup_date", DateTimeUtil.convertToString(LocalDate.now()))
                .put("account_activation", false);
        amplitude.logEvent(event);
    }

    /**
     * 회원가입 완료
     */
    @Transactional
    public void completeJoin(JoinCompletionRequest request) {
        final String joinTokenString = request.getJoinToken();

        // 토큰이 비유효한 경우
        if (!jwtHelper.isTokenValid(joinTokenString)) {
            throw new BaseException(AuthException.JOIN_TOKEN_NOT_VALID);
        }

        // 토큰 조회
        JoinToken joinToken = joinTokenRedisRepository.findById(joinTokenString)
                .orElseThrow(() -> new BaseException(AuthException.JOIN_TOKEN_NOT_VALID));

        // 가입 완료 처리
        memberJpaRepository.save(joinToken.getMember());
        memberPasswordJpaRepository.save(joinToken.getMemberPassword());
        joinToken.getTermsAgreementList().forEach(termsAgreeJpaRepository::save);

        // 토큰 무효화
        this.invalidateToken(joinToken);

        // Amplitude 이벤트 전송
        Event event = new Event("$identify", joinToken.getMember().getId().toString());
        event.userProperties = new JSONObject()
                .put("account_activation", true);
        amplitude.logEvent(event);
    }

    /**
     * 비밀번호 변경
     */
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        Member member = memberJpaRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(AuthException.CHANGE_PASSWORD_EMAIL_NOT_FOUND));

        // 비밀번호 변경 토큰 생성 & 저장
        JwtPayload payload = JwtPayload.builder()
                .memberId(member.getId())
                .build();
        ChangePasswordToken changePasswordToken = new ChangePasswordToken(jwtHelper.generateChangePasswordToken(payload));
        changePasswordTokenRedisRepository.save(changePasswordToken);

        // 이메일 전송
        try {
            mailHelper.send(
                    MailHelper.MailType.CHANGE_PASSWORD,
                    member.getEmail(),
                    MailHelper.MailData.builder()
                            .nickname(member.getNickname())
                            .linkUrl(changePasswordToken.getTokenString())
                            .build()
            );
        } catch (MessagingException e) {
            throw new BaseException(AuthException.CHANGE_PASSWORD_EMAIL_NOT_FOUND);
        }

    }

    /**
     * 비밀번호 변경 완료
     */
    @Transactional
    public void completeChangePassword(ChangePasswordCompletionRequest request) {
        // 토큰이 비유효한 경우
        if (!jwtHelper.isTokenValid(request.getChangePasswordToken())) {
            throw new BaseException(AuthException.CHANGE_PASSWORD_TOKEN_NOT_VALID);
        }

        // 토큰 레디스 조회
        ChangePasswordToken changePasswordToken = changePasswordTokenRedisRepository.findById(request.getChangePasswordToken())
                .orElseThrow(() -> new BaseException(AuthException.CHANGE_PASSWORD_TOKEN_NOT_VALID));
        JwtPayload payload = jwtHelper.getPayload(changePasswordToken.getTokenString());

        // 비밀번호 조회
        MemberPassword memberPassword = memberPasswordJpaRepository.findById(payload.getMemberId())
                .orElseThrow(() -> new BaseException(AuthException.AUTH_FAILED_BY_UNKNOWN_ERROR));

        final boolean isPasswordUsedBefore = BCrypt.checkpw(request.getPassword(), memberPassword.getPassword());
        if (isPasswordUsedBefore) {
            throw new BaseException(AuthException.CHANGE_PASSWORD_USED_PASSWORD_BEFORE);
        }

        // 비밀번호 변경
        final String passwordHash = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        memberPassword.setPassword(passwordHash);

        // 토큰 무효화
        this.invalidateToken(changePasswordToken);
    }

    /**
     * 인증 토큰 발급 (액세스 + 리프래시)
     */
    private AuthTokenDto generateAuthTokens(UUID memberId) {
        JwtPayload jwtPayload = JwtPayload.builder()
                .memberId(memberId)
                .build();
        String accessToken = jwtHelper.generateAccessToken(jwtPayload);
        String refreshToken = jwtHelper.generateRefreshToken(jwtPayload);
        refreshTokenRedisRepository.save(new RefreshToken(refreshToken, memberId));;

        return new AuthTokenDto(accessToken, refreshToken);
    }

    /**
     * 토큰 무효화 (Redis에서 관리하는 토큰의 경우)
     */
    private void invalidateToken(BaseToken token) {
        if (token instanceof RefreshToken refreshToken) {
            refreshTokenRedisRepository.delete(refreshToken);
        }
        else if (token instanceof JoinToken joinToken) {
            joinTokenRedisRepository.delete(joinToken);
        }
        else if (token instanceof ChangePasswordToken changePasswordToken) {
            changePasswordTokenRedisRepository.delete(changePasswordToken);
        }
    }
}
