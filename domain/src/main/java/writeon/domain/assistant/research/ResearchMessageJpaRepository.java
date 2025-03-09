package writeon.domain.assistant.research;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResearchMessageJpaRepository extends JpaRepository<ResearchMessage, UUID> {
}
