package writeon.api.assistant.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AssistantResponse {

    @Schema(title = "어시스턴트 ID")
    private final UUID assistantId;
    @Schema(title = "답변 내용")
    private final String answer;

    public AssistantResponse(UUID assistantId, String answer) {
        this.assistantId = assistantId;
        this.answer = answer;
    }
}
