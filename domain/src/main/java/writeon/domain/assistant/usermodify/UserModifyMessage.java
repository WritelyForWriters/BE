package writeon.domain.assistant.usermodify;

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
@Table(name = "user_modify_message")
public class UserModifyMessage extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private final UUID id = UUID.randomUUID();

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "assistant_id", nullable = false)
    private UUID assistantId;

    @Embedded
    private MessageContent messageContent;

    @Column(name = "prompt")
    private String prompt;

    public UserModifyMessage(UUID productId, UUID assistantId, MessageSenderRole role, String content, String prompt) {
        this.productId = productId;
        this.assistantId = assistantId;
        this.messageContent = new MessageContent(role, content);
        this.prompt = prompt;
    }

    public MessageSenderRole getRole() {
        return messageContent.getRole();
    }

    public String getContent() {
        return messageContent.getContent();
    }
}
