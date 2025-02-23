package com.writely.terms.repository;

import com.writely.terms.domain.TermsAgreement;
import org.springframework.data.repository.CrudRepository;

public interface TermsAgreeJpaRepository extends CrudRepository<TermsAgreement, String> {

}
