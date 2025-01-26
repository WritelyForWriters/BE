package com.writely.auth.domain.enums;

import com.writely.common.enums.code.CodeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthException implements CodeInfo {

    JOIN_TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "AUTH-001", "가입 토큰이 유효하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
