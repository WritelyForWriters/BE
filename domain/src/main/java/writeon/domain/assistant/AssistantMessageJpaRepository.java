package writeon.domain.assistant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

import writeon.domain.assistant.enums.MessageSenderRole;

public interface AssistantMessageJpaRepository extends JpaRepository<AssistantMessage, UUID> {

  boolean existsByAssistantIdAndMessageContent_Role(UUID assistantId, MessageSenderRole role);

  Optional<AssistantMessage> findByAssistantIdAndMessageContent_Role(UUID assistantId, MessageSenderRole role);
}
