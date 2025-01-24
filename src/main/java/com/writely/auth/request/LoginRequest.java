package com.writely.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Schema(title = "이메일")
    private String email;

    @Schema(title = "비밀번호")
    private String password;
}
