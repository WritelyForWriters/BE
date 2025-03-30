package writeon.domain.assistant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import writeon.domain.assistant.enums.AssistantStatus;

import java.util.Optional;
import java.util.UUID;

public interface AssistantJpaRepository extends JpaRepository<Assistant, UUID> {

    Optional<Assistant> findByIdAndCreatedBy(UUID assistantId, UUID createdBy);

    boolean existsByIdAndCreatedBy(UUID assistantId, UUID createdBy);

    @Modifying
    @Query("UPDATE Assistant a SET a.status = :status WHERE a.id = :assistantId")
    void updateStatus(@Param("assistantId") UUID assistantId, @Param("status") AssistantStatus status);
}
