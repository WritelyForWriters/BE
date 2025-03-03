package com.writely.assistant.domain.usermodify;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserModifyMessageJpaRepository extends JpaRepository<UserModifyMessage, UUID> {
}
