package writeon.domain.assistant.chat;

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
@Table(name = "chat_message")
public class ChatMessage extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private final UUID id = UUID.randomUUID();

    @Column(name = "assistant_id", nullable = false)
    private UUID assistantId;

    @Embedded
    private MessageContent messageContent;

    @Column(name = "prompt", nullable = false)
    private String prompt;

    public ChatMessage(UUID assistantId, MessageSenderRole role, String content, String prompt) {
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
