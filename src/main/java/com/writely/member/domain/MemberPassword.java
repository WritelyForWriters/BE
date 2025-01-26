package com.writely.member.domain;

import com.writely.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member_password")
public class MemberPassword extends BaseTimeEntity {
    @Id
    @Column(name = "member_id")
    private UUID memberId;

    @Column(nullable = false)
    private String password;
}
