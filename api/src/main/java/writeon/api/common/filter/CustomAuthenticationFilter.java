package writeon.api.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import writeon.api.auth.helper.JwtHelper;
import writeon.domain.auth.JwtPayload;
import writeon.domain.common.MemberSession;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String[] parts = authorization.split(" ");
        if (parts.length != 2) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = parts[1];
        if (!jwtHelper.isTokenValid(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        JwtPayload payload = jwtHelper.getPayload(accessToken);
        MemberSession memberSession = new MemberSession(payload.getMemberId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberSession, accessToken, memberSession.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
