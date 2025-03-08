package writeon.domain.terms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import writeon.domain.terms.Terms;

@Repository
public interface TermsJpaRepository extends CrudRepository<Terms, String> {

}
