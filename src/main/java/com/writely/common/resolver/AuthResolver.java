package com.writely.common.resolver;

import com.writely.auth.domain.JwtPayload;
import com.writely.auth.domain.enums.AuthException;
import com.writely.auth.helper.JwtHelper;
import com.writely.common.domain.MemberSession;
import com.writely.common.exception.BaseException;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@AllArgsConstructor
@Component
public class AuthResolver implements HandlerMethodArgumentResolver {
    private final JwtHelper jwtHelper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader("Authorization");
        if (authorization == null) {
            throw new BaseException(AuthException.ACCESS_TOKEN_NOT_VALID);
        }

        String[] parts = authorization.split(" ");
        if (parts.length != 2) {
            throw new BaseException(AuthException.ACCESS_TOKEN_NOT_VALID);
        }

        String accessToken = parts[1];
        if (!jwtHelper.isTokenValid(accessToken)) {
            throw new BaseException(AuthException.ACCESS_TOKEN_NOT_VALID);
        }

        JwtPayload payload = jwtHelper.getPayload(accessToken);
        return MemberSession.builder()
                .memberId(payload.getMemberId())
                .build();
    }
}
