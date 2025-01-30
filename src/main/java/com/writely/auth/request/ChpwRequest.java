package com.writely.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChpwRequest {
    @NotBlank
    @Schema(title = "이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "example@domain.com")
    private String email;
}
