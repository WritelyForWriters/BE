package com.writely.terms.repository;

import com.writely.terms.domain.Terms;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsJpaRepository extends CrudRepository<Terms, String> {

}
