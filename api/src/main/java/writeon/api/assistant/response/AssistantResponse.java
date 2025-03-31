package writeon.api.assistant.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AssistantResponse {

    private final UUID id;
    @Schema(title = "답변 내용")
    private final String answer;

    public AssistantResponse(UUID id, String answer) {
        this.id = id;
        this.answer = answer;
    }
}
