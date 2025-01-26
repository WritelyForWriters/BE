package com.writely.auth.domain;

import com.writely.member.domain.Member;
import com.writely.member.domain.MemberPassword;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "joinToken", timeToLive = 60 * 60)
public class JoinToken {
    @Id
    private String tokenString;
    private Member member;
    private MemberPassword memberPassword;
}
