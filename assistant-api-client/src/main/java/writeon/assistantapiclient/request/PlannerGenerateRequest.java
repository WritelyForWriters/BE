package writeon.assistantapiclient.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlannerGenerateRequest {

    private final String tenantId;
    private final String genre;
    private final String logline;
    private final String section;
    private final String prompt;

    @Builder
    public PlannerGenerateRequest(String tenantId, String genre, String logline, String section, String prompt) {
        this.tenantId = tenantId;
        this.genre = genre;
        this.logline = logline;
        this.section = section;
        this.prompt = prompt;
    }
}
