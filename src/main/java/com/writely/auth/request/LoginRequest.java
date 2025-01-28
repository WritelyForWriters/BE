package com.writely.auth.request;

import com.writely.common.validation.IsEmail;
import com.writely.common.validation.IsPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    @Size(max = 255)
    @IsEmail
    @Schema(title = "이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "example@domain.com")
    private String email;

    @NotBlank
    @Size(max = 255)
    @IsPassword
    @Schema(title = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "Writely4Writers!@")
    private String password;
}
