package writeon.domain.terms.repository;

import org.springframework.data.repository.CrudRepository;
import writeon.domain.terms.TermsAgreement;

public interface TermsAgreeJpaRepository extends CrudRepository<TermsAgreement, String> {

}
