package com.writely.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class JoinTokenRequest {
    @Schema(title = "회원가입 토큰")
    private String joinToken;
}
