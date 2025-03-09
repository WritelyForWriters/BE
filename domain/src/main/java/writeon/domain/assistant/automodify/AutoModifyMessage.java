package writeon.domain.assistant.automodify;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import writeon.domain.assistant.MessageContent;
import writeon.domain.assistant.enums.MessageSenderRole;
import writeon.domain.common.BaseAuditTimeEntity;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "auto_modify_message")
public class AutoModifyMessage extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private final UUID id = UUID.randomUUID();

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "assistant_id", nullable = false)
    private UUID assistantId;

    @Embedded
    private MessageContent messageContent;

    public AutoModifyMessage(UUID productId, UUID assistantId, MessageSenderRole role, String content) {
        this.productId = productId;
        this.assistantId = assistantId;
        this.messageContent = new MessageContent(role, content);
    }

    public MessageSenderRole getRole() {
        return messageContent.getRole();
    }

    public String getContent() {
        return messageContent.getContent();
    }
}
