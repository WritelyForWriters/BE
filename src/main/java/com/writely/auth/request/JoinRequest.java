package com.writely.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
    @Schema(title = "회원가입 토큰")
    private String joinToken;
}
