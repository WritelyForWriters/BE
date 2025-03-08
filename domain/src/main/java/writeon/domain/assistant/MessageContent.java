package writeon.domain.assistant;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import writeon.domain.assistant.enums.MessageSenderRole;

@Data
@Embeddable
@NoArgsConstructor
public class MessageContent {

    @Convert(converter = MessageSenderRole.TypeCodeConverter.class)
    @Column(name = "role", nullable = false)
    private MessageSenderRole role;

    @Column(name = "content", nullable = false)
    private String content;

    public MessageContent(MessageSenderRole role, String content) {
        this.role = role;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
