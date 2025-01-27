package com.writely.common.filter;

import com.writely.common.util.HttpRequestUtil;
import com.writely.common.util.MDCUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Profile("!local")
@Component
public class MDCFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        MDCUtil.set(MDCUtil.REQUEST_METHOD_MDC, HttpRequestUtil.getRequestMethod(request));
        MDCUtil.set(MDCUtil.REQUEST_URI_MDC, HttpRequestUtil.getRequestUri(request));
        MDCUtil.set(MDCUtil.REQUEST_IP_MDC, HttpRequestUtil.getUserIP(Objects.requireNonNull(request)));
        MDCUtil.setJsonValue(MDCUtil.HEADER_MAP_MDC, HttpRequestUtil.getHeaderMap(request));
        MDCUtil.setJsonValue(MDCUtil.PARAMETER_MAP_MDC, HttpRequestUtil.getParamMap(request));

        chain.doFilter(request, response);
    }
}
