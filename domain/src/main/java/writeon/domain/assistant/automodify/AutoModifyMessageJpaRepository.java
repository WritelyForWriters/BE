package writeon.domain.assistant.automodify;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutoModifyMessageJpaRepository extends JpaRepository<AutoModifyMessage, UUID> {
}
