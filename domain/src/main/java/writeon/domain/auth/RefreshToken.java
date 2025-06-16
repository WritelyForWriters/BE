package writeon.domain.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 90)
public class RefreshToken extends BaseToken {
    @Indexed
    private UUID memberId;

    public RefreshToken(String tokenString, UUID memberId) {
        super(tokenString);
        this.memberId = memberId;
    }
}