package writeon.domain.assistant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

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

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "created_at", updatable = false, nullable = false)
    protected final LocalDateTime createdAt = LocalDateTime.now();

    @CreatedBy
    @Column(name = "created_by", updatable = false, nullable = false)
    protected UUID createdBy = UUID.randomUUID();

    @Builder
    public AssistantEvaluation(UUID assistantId, Boolean isGood, String feedback) {
        this.assistantId = assistantId;
        this.isGood = isGood;
        this.feedback = feedback;
    }
}
