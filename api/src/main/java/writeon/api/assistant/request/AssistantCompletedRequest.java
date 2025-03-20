package writeon.api.assistant.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantCompletedRequest {

    @Schema(title = "응답 반영 여부")
    private Boolean isReflected;
}
