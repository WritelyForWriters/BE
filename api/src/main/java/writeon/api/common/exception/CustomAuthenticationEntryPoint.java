package writeon.api.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import writeon.api.common.response.BaseResponse;
import writeon.api.common.util.LogUtil;
import writeon.domain.auth.enums.AuthException;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(AuthException.ACCESS_TOKEN_NOT_VALID.getStatus().value());

        try {
            String jsonResponse = objectMapper.writeValueAsString(
                    BaseResponse.failure(AuthException.ACCESS_TOKEN_NOT_VALID)
            );
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
        } catch (Exception exception) {
            LogUtil.error(exception);
        }
    }
}
