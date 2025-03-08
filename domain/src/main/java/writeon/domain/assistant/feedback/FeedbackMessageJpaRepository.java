package writeon.domain.assistant.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeedbackMessageJpaRepository extends JpaRepository<FeedbackMessage, UUID> {
}
