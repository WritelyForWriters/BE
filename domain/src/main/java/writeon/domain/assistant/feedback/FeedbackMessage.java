package writeon.domain.assistant.feedback;

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
@Table(name = "feedback_message")
public class FeedbackMessage extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Embedded
    private MessageContent messageContent;

    public FeedbackMessage(UUID productId, MessageSenderRole role, String content) {
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
