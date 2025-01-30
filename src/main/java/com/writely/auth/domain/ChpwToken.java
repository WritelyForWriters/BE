package com.writely.auth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "joinToken", timeToLive = 60 * 60)
public class ChpwToken extends BaseToken {
    public ChpwToken(String tokenString) {
        super(tokenString);
    }
}