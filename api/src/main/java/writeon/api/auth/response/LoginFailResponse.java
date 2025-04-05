package writeon.api.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LoginFailResponse {
    @Schema(title = "남은 로그인 시도 횟수", requiredMode = Schema.RequiredMode.REQUIRED, example = "4")
    private int remainingAttempts;

    @Schema(title = "로그인 가능 시각", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2025-04-05T14:41:10.626943")
    private LocalDateTime loginAvailableAt;
}
