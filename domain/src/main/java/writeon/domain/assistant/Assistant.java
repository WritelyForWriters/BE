package writeon.domain.assistant;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.domain.assistant.enums.AssistantType;
import writeon.domain.common.BaseTimeEntity;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "assistant")
public class Assistant extends BaseTimeEntity {

    @Id
    @Column(name = "id", updatable = false)
    private final UUID id = Generators.timeBasedEpochGenerator().generate();

    @Column(name = "product_id", updatable = false, nullable = false)
    private UUID productId;

    @Convert(converter = AssistantType.TypeCodeConverter.class)
    @Column(name = "type", nullable = false)
    private AssistantType type;

    @Convert(converter = AssistantStatus.TypeCodeConverter.class)
    @Column(name = "status", nullable = false)
    private AssistantStatus status = AssistantStatus.DRAFT;

    @Column(name = "is_applied", nullable = false)
    private Boolean isApplied = Boolean.FALSE;

    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived = Boolean.FALSE;

    @Column(name = "created_by", updatable = false, nullable = false)
    private UUID createdBy;

    @Builder
    public Assistant(UUID productId, AssistantType type, UUID createdBy) {
        this.productId = productId;
        this.type = type;
        this.createdBy = createdBy;
    }

    public void apply() {
        this.isApplied = Boolean.TRUE;
    }

    public void archive() {
        this.isArchived = Boolean.TRUE;
    }

    public void updateStatus(AssistantStatus status) {
        this.status = status;
    }
}
