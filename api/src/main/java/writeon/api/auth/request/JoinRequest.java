package writeon.api.auth.request;

import writeon.api.common.validation.IsEmail;
import writeon.api.common.validation.IsPassword;
import writeon.api.terms.request.TermsAgreeRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JoinRequest {
    @NotBlank
    @IsEmail
    @Schema(title = "이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "example@domain.com")
    private String email;

    @NotBlank
    @IsPassword
    @Schema(title = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "Writely4Writers!@")
    private String password;

    @NotBlank
    @Size(max = 10)
    @Schema(title = "닉네임", requiredMode = Schema.RequiredMode.REQUIRED, example = "노래하는뱁새")
    private String nickname;

    @NotNull
    @Schema(title = "약관 동의 목록", requiredMode = Schema.RequiredMode.REQUIRED, example = "[{termsCd: \"1001\", isAgreed: true}, {termsCd: \"1002\", isAgreed: false}]")
    private List<TermsAgreeRequest> termsList;

}
