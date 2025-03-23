package writeon.domain.assistant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AssistantJpaRepository extends JpaRepository<Assistant, UUID> {

    Optional<Assistant> findByIdAndCreatedBy(UUID assistantId, UUID createdBy);

    boolean existsByIdAndCreatedBy(UUID assistantId, UUID createdBy);
}
