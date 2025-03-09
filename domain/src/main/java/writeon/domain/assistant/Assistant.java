package writeon.domain.assistant;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.domain.assistant.enums.AssistantType;
import writeon.domain.common.BaseAuditTimeEntity;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "assistant")
public class Assistant extends BaseAuditTimeEntity {

    @Id
    @Column(name = "id", updatable = false)
    private final UUID id = UUID.randomUUID();

    @Column(name = "product_id", updatable = false, nullable = false)
    private UUID productId;

    @Convert(converter = AssistantType.TypeCodeConverter.class)
    @Column(name = "type", nullable = false)
    private AssistantType type;

    @Convert(converter = AssistantStatus.TypeCodeConverter.class)
    @Column(name = "status", nullable = false)
    private AssistantStatus status = AssistantStatus.DRAFT;

    @Builder
    public Assistant(UUID productId, AssistantType type) {
        this.productId = productId;
        this.type = type;
    }

    public void updateStatus(AssistantStatus status) {
        this.status = status;
    }
}
