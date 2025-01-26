package com.writely.auth.repository;

import com.writely.auth.domain.JoinToken;
import org.springframework.data.repository.CrudRepository;

public interface JoinTokenRedisRepository extends CrudRepository<JoinToken, String> {

}
