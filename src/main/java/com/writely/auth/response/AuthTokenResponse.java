package com.writely.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthTokenResponse {
    @Schema(title = "액세스 토큰")
    private final String accessToken;
    @Schema(title = "리프레시 토큰")
    private final String refreshToken;

    public AuthTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
