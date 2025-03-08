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
    private UUID id = UUID.randomUUID();

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Embedded
    private MessageContent messageContent;

    public AutoModifyMessage(UUID productId, MessageSenderRole role, String content) {
        this.productId = productId;
        this.messageContent = new MessageContent(role, content);
    }

    public MessageSenderRole getRole() {
        return messageContent.getRole();
    }

    public String getContent() {
        return messageContent.getContent();
    }
}
