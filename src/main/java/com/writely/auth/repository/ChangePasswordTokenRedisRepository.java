package com.writely.auth.repository;

import com.writely.auth.domain.ChangePasswordToken;
import org.springframework.data.repository.CrudRepository;

public interface ChangePasswordTokenRedisRepository extends CrudRepository<ChangePasswordToken, String> {

}