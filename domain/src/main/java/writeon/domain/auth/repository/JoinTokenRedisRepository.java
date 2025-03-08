package writeon.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import writeon.domain.auth.JoinToken;

public interface JoinTokenRedisRepository extends CrudRepository<JoinToken, String> {

}
