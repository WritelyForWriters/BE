package com.writely.auth.domain;

import com.writely.member.domain.Member;
import com.writely.member.domain.MemberPassword;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "joinToken", timeToLive = 60 * 60)
public class JoinToken extends BaseToken {
    private Member member;
    private MemberPassword memberPassword;

    public JoinToken(String tokenString, Member member, MemberPassword memberPassword) {
        super(tokenString);
        this.member = member;
        this.memberPassword = memberPassword;
    }
}
