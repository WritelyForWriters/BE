package writeon.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.member.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<Member, UUID>  {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
}
