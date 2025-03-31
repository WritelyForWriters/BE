package writeon.domain.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthTokenDto {
    private final String accessToken;
    private final String refreshToken;
}
