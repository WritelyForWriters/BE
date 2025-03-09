package writeon.domain.assistant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssistantJpaRepository extends JpaRepository<Assistant, UUID> {
}
