package writeon.domain.assistant.usermodify;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.assistant.enums.MessageSenderRole;

import java.util.UUID;

public interface UserModifyMessageJpaRepository extends JpaRepository<UserModifyMessage, UUID> {

    boolean existsByAssistantIdAndMessageContent_Role(UUID assistantId, MessageSenderRole role);
}
