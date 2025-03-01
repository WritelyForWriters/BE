package com.writely.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginFailResponse {
    @Schema(title = "남은 로그인 시도 횟수", requiredMode = Schema.RequiredMode.REQUIRED, example = "4")
    private int remainingAttempts;
}
