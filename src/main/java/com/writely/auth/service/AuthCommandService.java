package com.writely.auth.service;

import com.writely.auth.domain.*;
import com.writely.auth.domain.enums.AuthException;
import com.writely.auth.helper.JwtHelper;
import com.writely.auth.helper.MailHelper;
import com.writely.auth.repository.ChangePasswordTokenRedisRepository;
import com.writely.auth.repository.JoinTokenRedisRepository;
import com.writely.auth.repository.RefreshTokenRedisRepository;
import com.writely.auth.request.*;
import com.writely.auth.response.AuthTokenResponse;
import com.writely.common.enums.code.ResultCodeInfo;
import com.writely.common.exception.BaseException;
import com.writely.common.util.CryptoUtil;
import com.writely.member.domain.Member;
import com.writely.member.domain.MemberPassword;
import com.writely.member.repository.MemberPasswordJpaRepository;
import com.writely.member.repository.MemberJpaRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthCommandService {
    private final MemberJpaRepository memberJpaRepository;
    private final MemberPasswordJpaRepository memberPasswordJpaRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final JoinTokenRedisRepository joinTokenRedisRepository;
    private final ChangePasswordTokenRedisRepository changePasswordTokenRedisRepository;
    private final JwtHelper jwtHelper;
    private final MailHelper mailHelper;
    private final CryptoUtil cryptoUtil;

    /**
     * 토큰 재발급
     */
    public AuthTokenResponse reissueToken(ReissueRequest request) {
        // 리프래시 토큰이 비유효하면
        if (!jwtHelper.isTokenValid(request.getRefreshToken())) {
            throw new BaseException(AuthException.REFRESH_TOKEN_NOT_VALID);
        }

        // 레디스 검사
        RefreshToken oldRefreshToken = refreshTokenRedisRepository.findById(request.getRefreshToken())
                .orElseThrow(() -> new BaseException(AuthException.REFRESH_TOKEN_NOT_VALID));
        this.invalidateToken(oldRefreshToken);

        JwtPayload payload = jwtHelper.getPayload(request.getRefreshToken());
        return generateAuthTokens(payload.getMemberId());
    }

    /**
     * 로그인
     */
    public AuthTokenResponse login(LoginRequest request) {
        String passwordHash = cryptoUtil.hash(request.getPassword());

        MemberPassword memberPassword = memberPasswordJpaRepository.findByPassword(passwordHash)
                .orElseThrow(() -> new BaseException(AuthException.LOGIN_FAILED));

        return generateAuthTokens(memberPassword.getMemberId());
    }

    /**
     * 로그아웃
     */
    public void logout(UUID memberId) {
        refreshTokenRedisRepository.findByMemberId(memberId)
                .ifPresent(this::invalidateToken);
    }

    /**
     * 회원 가입
     */
    public void join(JoinRequest request) {
        // 이미 있는 회원인지 검사
        if (memberJpaRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BaseException(AuthException.JOIN_ALREADY_EXIST_MEMBER);
        }

        // 회원 정보 설정
        String passwordHash = cryptoUtil.hash(request.getPassword());
        Member member = Member.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .build();
        MemberPassword memberPassword = MemberPassword.builder()
                .memberId(member.getId())
                .password(passwordHash)
                .build();
        String jwtString = jwtHelper.generateJoinToken(
                JwtPayload.builder().memberId(member.getId()).build()
        );

        // joinToken redis 저장
        JoinToken joinToken = new JoinToken(jwtString, member, memberPassword);
        joinTokenRedisRepository.save(joinToken);

        try {
            // 이메일 전송
            mailHelper.send(
                    MailHelper.MailType.JOIN,
                    request.getEmail(),
                    MailHelper.MailData.builder()
                            .nickname(request.getNickname())
                            .token(joinToken.getTokenString())
                            .build()
            );
        } catch (MessagingException e) {
            throw new BaseException(AuthException.MAIL_SEND_FAILED);
        }

    }

    /**
     * 회원가입 완료
     */
    public AuthTokenResponse completeJoin(JoinCompletionRequest request) {
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

        // 토큰 무효화
        this.invalidateToken(joinToken);

        return generateAuthTokens(joinToken.getMember().getId());
    }

    /**
     * 비밀번호 변경
     */
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
                            .token(changePasswordToken.getTokenString())
                            .build()
            );
        } catch (MessagingException e) {
            throw new BaseException(AuthException.CHANGE_PASSWORD_EMAIL_NOT_FOUND);
        }

    }

    /**
     * 비밀번호 변경 완료
     */
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
                .orElseThrow(() -> new BaseException(ResultCodeInfo.FAILURE));
        String passwordHash = cryptoUtil.hash(request.getPassword());

        // 이전 비밀번호와 동일한 경우
        if (passwordHash.equals(memberPassword.getPassword())) {
            throw new BaseException(AuthException.CHANGE_PASSWORD_USED_PASSWORD_BEFORE);
        }

        // 비밀번호 변경
        memberPassword.setPassword(passwordHash);

        // 토큰 무효화
        this.invalidateToken(changePasswordToken);
    }

    /**
     * 인증 토큰 발급 (액세스 + 리프래시)
     */
    private AuthTokenResponse generateAuthTokens(UUID memberId) {
        JwtPayload jwtPayload = JwtPayload.builder()
                .memberId(memberId)
                .build();
        String accessToken = jwtHelper.generateAccessToken(jwtPayload);
        String refreshToken = jwtHelper.generateRefreshToken(jwtPayload);
        refreshTokenRedisRepository.save(new RefreshToken(refreshToken, memberId));;

        return new AuthTokenResponse(accessToken, refreshToken);
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
