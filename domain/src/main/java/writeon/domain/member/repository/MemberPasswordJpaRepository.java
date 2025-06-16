package writeon.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.member.MemberPassword;

import java.util.UUID;

public interface MemberPasswordJpaRepository extends JpaRepository<MemberPassword, UUID> {

}
