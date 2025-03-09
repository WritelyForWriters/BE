package writeon.api.assistant.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import writeon.domain.assistant.enums.FeedbackType;

import java.util.UUID;

@Getter
@Setter
public class AssistantEvaluationRequest {

    @Schema(title = "어시스턴트 ID")
    private UUID assistantId;
    @Schema(title = "만족 여부")
    private Boolean isGood;
    @Schema(title = "피드백 타입", nullable = true)
    private FeedbackType feedbackType;
    @Schema(title = "피드백", nullable = true)
    private String feedback;
}
