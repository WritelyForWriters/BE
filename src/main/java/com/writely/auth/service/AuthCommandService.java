package com.writely.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.writely.auth.domain.JwtPayload;
import com.writely.auth.helper.JwtHelper;
import com.writely.auth.request.JoinRequest;
import com.writely.auth.request.LoginRequest;
import com.writely.auth.response.AuthTokenResponse;
import com.writely.common.enums.code.ResultCodeInfo;
import com.writely.common.exception.BaseException;
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
    private final JwtHelper jwtHelper;

    public AuthTokenResponse login(LoginRequest request) {
        // todo: verification

        // todo
        return generateAuthTokens(UUID.randomUUID());
    }

    public AuthTokenResponse join(JoinRequest request) {
        // todo: verification

        Member member = Member.builder()
                .email("wichan7@naver.com")
                .realname("강위찬")
                .nickname("위차니")
                .build();
        MemberPassword memberPassword = MemberPassword.builder()
                .memberId(member.getId())
                .password("passwdpaasswd")
                .build();

        memberRepository.save(member);
        memberPasswordRepository.save(memberPassword);

        // todo
        return generateAuthTokens(UUID.randomUUID());
    }


    public AuthTokenResponse generateAuthTokens(UUID memberId) {
        try {
            JwtPayload jwtPayload = JwtPayload.builder().build();
            String accessToken = jwtHelper.generateAccessToken(jwtPayload);
            String refreshToken = jwtHelper.generateRefreshToken(jwtPayload);

            return new AuthTokenResponse(accessToken, refreshToken);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();

            throw new BaseException(ResultCodeInfo.FAILURE);
        }
    }
}
