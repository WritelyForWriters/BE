package writeon.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import writeon.domain.auth.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByMemberId(UUID memberId);
}
