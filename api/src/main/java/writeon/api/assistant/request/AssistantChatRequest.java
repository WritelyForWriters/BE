package writeon.api.assistant.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssistantChatRequest {

    @Schema(title = "작품 ID")
    private UUID productId;
    @Schema(title = "선택 구간", nullable = true)
    private String content;
    @Schema(title = "프롬프트")
    private String prompt;
    @Schema(title = "세션 ID", nullable = true)
    private String sessionId;
}
