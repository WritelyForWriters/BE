package writeon.domain.assistant.chat;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import writeon.domain.assistant.MessageContent;
import writeon.domain.assistant.enums.MessageSenderRole;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @Column(updatable = false, nullable = false)
    private final UUID id = UUID.randomUUID();

    @Column(name = "assistant_id", nullable = false)
    private UUID assistantId;

    @Embedded
    private MessageContent messageContent;

    @Column(name = "prompt", nullable = false)
    private String prompt;

    @Column(name = "created_at", updatable = false, nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "created_by", updatable = false, nullable = false)
    private UUID createdBy;

    @Builder
    public ChatMessage(UUID assistantId, MessageSenderRole role, String content, String prompt, UUID createdBy) {
        this.assistantId = assistantId;
        this.messageContent = new MessageContent(role, content);
        this.prompt = prompt;
        this.createdBy = createdBy;
    }

    public MessageSenderRole getRole() {
        return messageContent.getRole();
    }

    public String getContent() {
        return messageContent.getContent();
    }
}
