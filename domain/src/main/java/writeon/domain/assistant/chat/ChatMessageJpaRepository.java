package writeon.domain.assistant.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.assistant.enums.MessageSenderRole;

import java.util.Optional;
import java.util.UUID;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, UUID> {

    boolean existsByAssistantIdAndMessageContent_Role(UUID assistantId, MessageSenderRole role);

    Optional<ChatMessage> findByAssistantIdAndMessageContent_Role(UUID assistantId, MessageSenderRole role);
}
