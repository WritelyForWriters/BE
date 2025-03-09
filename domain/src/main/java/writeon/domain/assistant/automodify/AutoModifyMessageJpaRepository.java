package writeon.domain.assistant.automodify;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.assistant.enums.MessageSenderRole;

import java.util.UUID;

public interface AutoModifyMessageJpaRepository extends JpaRepository<AutoModifyMessage, UUID> {

    boolean existsByAssistantIdAndMessageContent_Role(UUID assistantId, MessageSenderRole role);
}
