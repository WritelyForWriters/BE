package writeon.domain.assistant.automodify;

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
@Table(name = "auto_modify_message")
public class AutoModifyMessage {

    @Id
    @Column(updatable = false, nullable = false)
    private final UUID id = UUID.randomUUID();

    @Column(name = "assistant_id", nullable = false)
    private UUID assistantId;

    @Embedded
    private MessageContent messageContent;

    @Column(name = "created_at", updatable = false, nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "created_by", updatable = false, nullable = false)
    private UUID createdBy;

    @Builder
    public AutoModifyMessage(UUID assistantId, MessageSenderRole role, String content, UUID createdBy) {
        this.assistantId = assistantId;
        this.messageContent = new MessageContent(role, content);
        this.createdBy = createdBy;
    }

    public MessageSenderRole getRole() {
        return messageContent.getRole();
    }

    public String getContent() {
        return messageContent.getContent();
    }
}
