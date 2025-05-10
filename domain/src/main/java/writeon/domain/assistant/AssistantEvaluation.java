package writeon.domain.assistant;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import writeon.domain.assistant.enums.FeedbackType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "assistant_evaluation")
public class AssistantEvaluation {

    @Id
    @Column(name = "assistant_id", updatable = false)
    private UUID assistantId;

    @Column(name = "is_good", nullable = false)
    private Boolean isGood;

    @Convert(converter = FeedbackType.TypeCodeConverter.class)
    @Column(name = "feedback_type")
    private FeedbackType feedbackType;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "created_at", updatable = false, nullable = false)
    protected final LocalDateTime createdAt = LocalDateTime.now();

    @CreatedBy
    @Column(name = "created_by", updatable = false, nullable = false)
    protected UUID createdBy = UUID.randomUUID();

    @Builder
    public AssistantEvaluation(UUID assistantId, Boolean isGood, FeedbackType feedbackType, String feedback) {
        this.assistantId = assistantId;
        this.isGood = isGood;
        this.feedbackType = feedbackType;
        this.feedback = feedback;
    }
}
