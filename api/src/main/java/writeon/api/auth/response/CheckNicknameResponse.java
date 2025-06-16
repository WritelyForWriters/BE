package writeon.api.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CheckNicknameResponse {
    @Schema(title = "닉네임", requiredMode = Schema.RequiredMode.REQUIRED, example = "노래하는뱁새")
    private String email;
    @Schema(title = "존재 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    private boolean exists;
}
