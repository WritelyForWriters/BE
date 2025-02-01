package com.writely.auth.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.writely.auth.domain.JwtPayload;
import com.writely.common.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.io.IOException;
import java.time.Instant;

@Component
public class JwtHelper {
    private final Algorithm algorithm;
    private final Long accessTokenExpirationPeriod;
    private final Long refreshTokenExpirationPeriod;
    private final Long joinTokenExpirationPeriod;
    private final Long changePasswordTokenExpirationPeriod;
    private final CryptoUtil cryptoUtil;

    public JwtHelper (
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration-period}") Long accessTokenExpirationPeriod,
            @Value("${jwt.refresh-token-expiration-period}") Long refreshTokenExpirationPeriod,
            @Value("${jwt.join-token-expiration-period}") Long joinTokenExpirationPeriod,
            @Value("${jwt.change-password-token-expiration-period}") Long changePasswordTokenExpirationPeriod,
            @Autowired CryptoUtil cryptoUtil
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.accessTokenExpirationPeriod = accessTokenExpirationPeriod;
        this.refreshTokenExpirationPeriod = refreshTokenExpirationPeriod;
        this.joinTokenExpirationPeriod = joinTokenExpirationPeriod;
        this.changePasswordTokenExpirationPeriod = changePasswordTokenExpirationPeriod;
        this.cryptoUtil = cryptoUtil;
    }

    public String generateAccessToken(JwtPayload payload) {
        return generateJwt(this.accessTokenExpirationPeriod, payload);
    }

    public String generateRefreshToken(JwtPayload payload) {

        return generateJwt(this.refreshTokenExpirationPeriod, payload);
    }

    public String generateJoinToken(JwtPayload payload) {

        return generateJwt(this.joinTokenExpirationPeriod, payload);
    }

    public String generateChangePasswordToken(JwtPayload payload) {

        return generateJwt(this.changePasswordTokenExpirationPeriod, payload);
    }

    public JwtPayload getPayload(String token) throws JWTVerificationException {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            String payloadEncoded = verifier.verify(token).getPayload();
            byte[] payloadDecoded = cryptoUtil.decodeBase64(payloadEncoded);

            return new ObjectMapper().readValue(payloadDecoded, JwtPayload.class);
        } catch (IOException ex) {
            throw new JWTVerificationException("JwtPayload object mapping has failed.");
        }
    }

    public boolean isTokenValid(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);

            return true;
        } catch (JWTVerificationException ex) {

            return false;
        }
    }

    private String generateJwt(Long expPeriod, JwtPayload payload) {
        try {
            String jsonPayload = new ObjectMapper().writeValueAsString(payload);

            return JWT.create()
                    .withPayload(jsonPayload)
                    .withExpiresAt(Instant.ofEpochSecond(Instant.now().getEpochSecond() + expPeriod))
                    .withIssuedAt(Instant.now())
                    .sign(algorithm);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}