package com.writely.auth.repository;

import com.writely.auth.domain.LoginAttempt;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface LoginAttemptJpaRepository extends CrudRepository<LoginAttempt, UUID> {
    List<LoginAttempt> findTop5ByEmailOrderByCreatedAtDesc(String email);
}
