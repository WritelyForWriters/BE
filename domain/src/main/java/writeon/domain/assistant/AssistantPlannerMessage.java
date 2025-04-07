package writeon.domain.assistant;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "assistant_planner_message")
public class AssistantPlannerMessage {

    @Id
    @Column(name = "assistant_id", updatable = false, nullable = false)
    private UUID assistantId;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "logline", nullable = false)
    private String logline;

    @Column(name = "section", nullable = false)
    private String section;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "assistant_id")
    private AssistantMessage assistantMessage;

    @Builder
    public AssistantPlannerMessage(UUID assistantId, String genre, String logline, String section) {
        this.assistantId = assistantId;
        this.genre = genre;
        this.logline = logline;
        this.section = section;
    }
}
