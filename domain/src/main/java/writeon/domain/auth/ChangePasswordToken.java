package writeon.domain.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "changePasswordToken", timeToLive = 60 * 60)
public class ChangePasswordToken extends BaseToken {
    public ChangePasswordToken(String tokenString) {
        super(tokenString);
    }
}