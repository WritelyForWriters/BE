package writeon.api.assistant.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssistantResearchRequest {

    @Schema(title = "작품 ID")
    private UUID productId;
    @Schema(title = "세션 ID")
    private String sessionId;
    @Schema(title = "내용", nullable = true)
    private String content;
    @Schema(title = "프롬프트")
    private String prompt;
}
