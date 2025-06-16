package writeon.domain.assistant;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.assistant.enums.MessageSenderRole;

import java.util.Optional;
import java.util.UUID;

public interface AssistantMessageJpaRepository extends JpaRepository<AssistantMessage, UUID> {

  boolean existsByAssistantIdAndMessageContent_Role(UUID assistantId, MessageSenderRole role);

  Optional<AssistantMessage> findByAssistantIdAndMessageContent_Role(UUID assistantId, MessageSenderRole role);
}
