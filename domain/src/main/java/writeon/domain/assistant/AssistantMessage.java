package writeon.domain.assistant;

import com.fasterxml.uuid.Generators;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import writeon.domain.assistant.enums.MessageSenderRole;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "assistant_message")
public class AssistantMessage {

    @Id
    @Column(updatable = false, nullable = false)
    private final UUID id = Generators.timeBasedEpochGenerator().generate();

    @Column(name = "assistant_id", nullable = false)
    private UUID assistantId;

    @Embedded
    private MessageContent messageContent;

    @Column(name = "prompt")
    private String prompt;

    @Column(name = "created_at", updatable = false, nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "created_by", updatable = false)
    private UUID createdBy;

    @Builder
    public AssistantMessage(UUID assistantId, MessageSenderRole role, String content, String prompt, UUID createdBy) {
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
