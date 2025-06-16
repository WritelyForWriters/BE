package writeon.domain.assistant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssistantEvaluationJpaRepository extends JpaRepository<AssistantEvaluation, UUID> {
    List<AssistantEvaluation> getByCreatedBy(UUID createdBy);
}
