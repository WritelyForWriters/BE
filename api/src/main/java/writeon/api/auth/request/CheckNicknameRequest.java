package writeon.api.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import writeon.api.common.validation.IsNickname;

@Getter
@Setter
public class CheckNicknameRequest {
    @NotBlank
    @IsNickname
    @Schema(title = "닉네임", requiredMode = Schema.RequiredMode.REQUIRED, example = "노래하는뱁새")
    private String nickname;
}
