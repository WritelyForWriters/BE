package com.writely.auth.domain;

import com.writely.auth.domain.enums.LoginAttemptResultType;
import com.writely.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "login_attempt")
public class LoginAttempt extends BaseTimeEntity {
    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String email;

    @Convert(converter = LoginAttemptResultType.TypeCodeConverter.class)
    @Column(nullable = false)
    private LoginAttemptResultType result;
}
