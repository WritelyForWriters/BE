package writeon.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import writeon.domain.auth.ChangePasswordToken;

public interface ChangePasswordTokenRedisRepository extends CrudRepository<ChangePasswordToken, String> {

}