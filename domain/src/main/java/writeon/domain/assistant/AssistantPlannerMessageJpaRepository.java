package writeon.domain.assistant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssistantPlannerMessageJpaRepository extends JpaRepository<AssistantPlannerMessage, UUID> {
}
