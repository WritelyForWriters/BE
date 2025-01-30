package com.writely.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CheckEmailResponse {
    @Schema(title = "이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "example@domain.com")
    private String email;
    @Schema(title = "존재 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    private boolean exists;
}
