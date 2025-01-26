package com.writely.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.writely.auth.domain.JoinToken;
import com.writely.auth.domain.JwtPayload;
import com.writely.auth.domain.enums.AuthException;
import com.writely.auth.helper.JwtHelper;
import com.writely.auth.repository.JoinTokenRedisRepository;
import com.writely.auth.request.JoinRequest;
import com.writely.auth.request.JoinTokenRequest;
import com.writely.auth.request.LoginRequest;
import com.writely.auth.response.AuthTokenResponse;
import com.writely.common.enums.code.ResultCodeInfo;
import com.writely.common.exception.BaseException;
import com.writely.common.util.CryptoUtil;
import com.writely.common.util.LogUtil;
import com.writely.member.domain.Member;
import com.writely.member.domain.MemberPassword;
import com.writely.member.repository.MemberPasswordRepository;
import com.writely.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthCommandService {
    private final MemberRepository memberRepository;
    private final MemberPasswordRepository memberPasswordRepository;
    private final JoinTokenRedisRepository joinTokenRedisRepository;
    private final JwtHelper jwtHelper;
    private final CryptoUtil cryptoUtil;
    private final String salt = "test"; // todo: salt 처리

    public AuthTokenResponse login(LoginRequest request) {
        String passwordHash;
        try {
            passwordHash = cryptoUtil.hash(request.getPassword(), this.salt);
        } catch (Exception ex) {
            throw new BaseException(ResultCodeInfo.FAILURE);
        }

        MemberPassword memberPassword = memberPasswordRepository.findByPassword(passwordHash)
                .orElseThrow(() -> new BaseException(AuthException.LOGIN_FAILED));

        return generateAuthTokens(memberPassword.getMemberId());
    }

    public void join(JoinRequest request) {
        try {
            String passwordHash = cryptoUtil.hash(request.getPassword(), this.salt);
            Member member = Member.builder()
                    .email(request.getEmail())
                    .realname(request.getRealname())
                    .nickname(request.getNickname())
                    .build();
            MemberPassword memberPassword = MemberPassword.builder()
                    .memberId(member.getId())
                    .password(passwordHash)
                    .build();
            String jwtString = jwtHelper.generateJoinToken(
                    JwtPayload.builder().memberId(member.getId()).build()
            );
            JoinToken joinToken = new JoinToken(jwtString, member, memberPassword);
            // redis 임시 저장
            joinTokenRedisRepository.save(joinToken);

            LogUtil.info("joinTokenString: " + joinToken.getTokenString());

            // todo: send email with joinToken.getTokenString();
        } catch (Exception ex) {
            throw new BaseException(ResultCodeInfo.FAILURE);
        }

    }

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
        memberRepository.save(joinToken.getMember());
        memberPasswordRepository.save(joinToken.getMemberPassword());

        return generateAuthTokens(joinToken.getMember().getId());
    }


    public AuthTokenResponse generateAuthTokens(UUID memberId) {
        try {
            JwtPayload jwtPayload = JwtPayload.builder()
                    .memberId(memberId)
                    .build();
            String accessToken = jwtHelper.generateAccessToken(jwtPayload);
            String refreshToken = jwtHelper.generateRefreshToken(jwtPayload);

            return new AuthTokenResponse(accessToken, refreshToken);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();

            throw new BaseException(ResultCodeInfo.FAILURE);
        }
    }
}
