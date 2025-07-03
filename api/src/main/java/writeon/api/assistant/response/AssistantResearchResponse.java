package writeon.api.assistant.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class AssistantResearchResponse {

    private final UUID id;
    @Schema(title = "답변 내용")
    private final String answer;
    @Schema(title = "출처")
    private final List<String> sources;

    public AssistantResearchResponse(UUID id, String answer, List<String> sources) {
        this.id = id;
        this.answer = answer;
        this.sources = sources;
    }
}
