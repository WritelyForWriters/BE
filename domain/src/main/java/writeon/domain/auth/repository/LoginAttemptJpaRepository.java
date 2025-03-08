package writeon.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import writeon.domain.auth.LoginAttempt;

import java.util.List;
import java.util.UUID;

public interface LoginAttemptJpaRepository extends CrudRepository<LoginAttempt, UUID> {
    List<LoginAttempt> findTop5ByEmailOrderByCreatedAtDesc(String email);
}
