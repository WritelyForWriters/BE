package writeon.api.auth.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthCookieHelper {
    public final JwtHelper jwtHelper;

    @Value("${spring.profiles.active}")
    private String profile; // todo: 프로필에 따른 쿠키 설정 분기, 운영기는 (secure: true && samesite: strict)

    public String createNewCookie(String refreshToken) {
        return ResponseCookie
                .from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(jwtHelper.getRefreshTokenExpirationPeriod())
                .sameSite("None")
                .secure(true)
                .build().toString();
    }

    public String createExpiredCookie() {
        return ResponseCookie
                .from("refreshToken", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0L)
                .sameSite("None")
                .secure(true)
                .build().toString();
    }
}
