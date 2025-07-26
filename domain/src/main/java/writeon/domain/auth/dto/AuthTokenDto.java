package writeon.domain.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthTokenDto {
    private final String accessToken;
    private final String refreshToken;
    private final LocalDateTime previousTokenIssuedAt;
}
