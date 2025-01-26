package com.writely.member.repository;

import com.writely.member.domain.MemberPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberPasswordRepository extends JpaRepository<MemberPassword, UUID> {
    Optional<MemberPassword> findByPassword(String password);
}
