package writeon.api.auth.request;

import writeon.api.common.validation.IsEmail;
import writeon.api.common.validation.IsPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    @IsEmail
    @Schema(title = "이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "example@domain.com")
    private String email;

    @NotBlank
    @IsPassword
    @Schema(title = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "Writely4Writers!@")
    private String password;
}
