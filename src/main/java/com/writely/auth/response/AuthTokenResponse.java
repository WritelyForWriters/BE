package com.writely.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthTokenResponse {
    @Schema(title = "액세스 토큰", requiredMode = Schema.RequiredMode.REQUIRED, example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3Mzc4Nzc1MDUsImV4cCI6MTc0NTY1MzUwNSwibWVtYmVySWQiOiJlZGMxNDJmNi1hODI4LTQ4MTQtODllOC1jZGM2NmU2ZTExMzMifQ.dEr2bidlj9lPe3ozoHFDc8e9IPxKaDdGiFQOl0HgiUM")
    private final String accessToken;

    @Schema(title = "리프레시 토큰", requiredMode = Schema.RequiredMode.REQUIRED, example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3Mzc4Nzc1MDUsImV4cCI6MTc0NTY1MzUwNSwibWVtYmVySWQiOiJlZGMxNDJmNi1hODI4LTQ4MTQtODllOC1jZGM2NmU2ZTExMzMifQ.dEr2bidlj9lPe3ozoHFDc8e9IPxKaDdGiFQOl0HgiUM")
    private final String refreshToken;

    public AuthTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
