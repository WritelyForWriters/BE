package writeon.domain.assistant.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.assistant.enums.MessageSenderRole;

import java.util.UUID;

public interface FeedbackMessageJpaRepository extends JpaRepository<FeedbackMessage, UUID> {

    boolean existsByAssistantIdAndMessageContent_Role(UUID assistantId, MessageSenderRole role);
}
