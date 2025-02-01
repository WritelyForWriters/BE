package com.writely.auth.request;

import com.writely.common.validation.IsPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChangePasswordCompletionRequest {
    @NotBlank
    @Schema(title = "비밀번호 변경 토큰", requiredMode = Schema.RequiredMode.REQUIRED, example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3Mzc4Nzc1MDUsImV4cCI6MTc0NTY1MzUwNSwibWVtYmVySWQiOiJlZGMxNDJmNi1hODI4LTQ4MTQtODllOC1jZGM2NmU2ZTExMzMifQ.dEr2bidlj9lPe3ozoHFDc8e9IPxKaDdGiFQOl0HgiUM")
    private String changePasswordToken;

    @NotBlank
    @IsPassword
    @Schema(title = "변경할 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "Writely4Writers!@")
    private String password;
}
