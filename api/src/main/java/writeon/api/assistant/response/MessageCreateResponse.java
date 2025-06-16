package writeon.api.assistant.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MessageCreateResponse {

    @Schema(title = "어시스턴트 ID")
    private final UUID assistantId;

    public MessageCreateResponse(UUID assistantId) {
        this.assistantId = assistantId;
    }
}
