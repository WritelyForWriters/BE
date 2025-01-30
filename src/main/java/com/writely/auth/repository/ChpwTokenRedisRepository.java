package com.writely.auth.repository;

import com.writely.auth.domain.ChpwToken;
import org.springframework.data.repository.CrudRepository;

public interface ChpwTokenRedisRepository extends CrudRepository<ChpwToken, String> {

}