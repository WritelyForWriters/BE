package writeon.assistantapiclient.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ResearchResponse {

    @Schema(title = "답변 내용")
    private final String answer;
    @Schema(title = "출처")
    private final List<String> sources;

    public ResearchResponse(String answer, List<String> sources) {
        this.answer = answer;
        this.sources = sources;
    }
}
