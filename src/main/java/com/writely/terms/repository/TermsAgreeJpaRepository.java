package com.writely.terms.repository;

import com.writely.terms.domain.TermsAgree;
import org.springframework.data.repository.CrudRepository;

public interface TermsAgreeJpaRepository extends CrudRepository<TermsAgree, String> {

}
