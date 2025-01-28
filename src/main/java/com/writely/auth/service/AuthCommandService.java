package com.writely.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.writely.auth.domain.JoinToken;
import com.writely.auth.domain.JwtPayload;
import com.writely.auth.domain.RefreshToken;
import com.writely.auth.domain.enums.AuthException;
import com.writely.auth.helper.JwtHelper;
import com.writely.auth.helper.MailHelper;
import com.writely.auth.repository.JoinTokenRedisRepository;
import com.writely.auth.repository.RefreshTokenRedisRepository;
import com.writely.auth.request.JoinRequest;
import com.writely.auth.request.JoinTokenRequest;
import com.writely.auth.request.LoginRequest;
import com.writely.auth.request.ReissueRequest;
import com.writely.auth.response.AuthTokenResponse;
import com.writely.common.enums.code.ResultCodeInfo;
import com.writely.common.exception.BaseException;
import com.writely.common.util.CryptoUtil;
import com.writely.common.util.LogUtil;
import com.writely.member.domain.Member;
import com.writely.member.domain.MemberPassword;
import com.writely.member.repository.MemberPasswordJpaRepository;
import com.writely.member.repository.MemberJpaRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.security.GeneralSecurityException;
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
    private final JwtHelper jwtHelper;
    private final MailHelper mailHelper;
    private final CryptoUtil cryptoUtil;

    // 토큰 재발급
    public AuthTokenResponse reissueToken(ReissueRequest request) {
        // 토큰이 비유효하면
        if (!jwtHelper.isTokenValid(request.getRefreshToken())) {
            throw new BaseException(AuthException.REFRESH_TOKEN_NOT_VALID);
        }

        JwtPayload payload = jwtHelper.getPayload(request.getRefreshToken());
        return generateAuthTokens(payload.getMemberId());
    }

    // 로그인
    public AuthTokenResponse login(LoginRequest request) {
        String passwordHash;
        try {
            passwordHash = cryptoUtil.hash(request.getPassword());
        } catch (Exception ex) {
            throw new BaseException(ResultCodeInfo.FAILURE);
        }

        MemberPassword memberPassword = memberPasswordJpaRepository.findByPassword(passwordHash)
                .orElseThrow(() -> new BaseException(AuthException.LOGIN_FAILED));

        return generateAuthTokens(memberPassword.getMemberId());
    }

    // 회원가입
    public void join(JoinRequest request) {

        // 이미 있는 회원인지 검사
        if (memberJpaRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BaseException(AuthException.JOIN_ALREADY_EXIST_MEMBER);
        }

        try {
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

            // 이메일 전송
            Context emailCtx = new Context();
            emailCtx.setVariable("joinToken", joinToken.getTokenString());

            mailHelper.send(
                    request.getEmail(),
                    "[WritelyForWriters] 회원가입 안내입니다.",
                    "mail/join",
                    emailCtx
            ); // todo: 메일 디자인
        } catch (JsonProcessingException | GeneralSecurityException ex) {
            throw new BaseException(ResultCodeInfo.FAILURE);
        } catch (MessagingException e) {
            throw new BaseException(AuthException.MAIL_SEND_FAILED);
        }

    }

    // 회원가입 완료
    public AuthTokenResponse completeJoin(JoinTokenRequest request) {
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

        return generateAuthTokens(joinToken.getMember().getId());
    }
    
    // 인증 토큰 생성
    public AuthTokenResponse generateAuthTokens(UUID memberId) {
        try {
            JwtPayload jwtPayload = JwtPayload.builder()
                    .memberId(memberId)
                    .build();
            String accessToken = jwtHelper.generateAccessToken(jwtPayload);
            String refreshToken = jwtHelper.generateRefreshToken(jwtPayload);
            refreshTokenRedisRepository.save(new RefreshToken(refreshToken));

            return new AuthTokenResponse(accessToken, refreshToken);
        } catch (JsonProcessingException ex) {
            LogUtil.error(ex);

            throw new BaseException(ResultCodeInfo.FAILURE);
        }
    }
}
