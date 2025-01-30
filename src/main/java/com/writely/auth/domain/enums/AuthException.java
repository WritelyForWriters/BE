package com.writely.auth.domain.enums;

import com.writely.common.enums.code.CodeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthException implements CodeInfo {
    ACCESS_TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "AUTH-001", "액세스 토큰이 유효하지 않습니다."),
    REFRESH_TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "AUTH-002", "리프레시 토큰이 유효하지 않습니다."),
    JOIN_TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "AUTH-101", "가입 토큰이 유효하지 않습니다."),
    JOIN_ALREADY_EXIST_MEMBER(HttpStatus.CONFLICT, "AUTH-102", "이미 있는 회원입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "AUTH-201", "로그인에 실패하였습니다."),
    MAIL_SEND_FAILED(HttpStatus.UNAUTHORIZED, "AUTH-301", "이메일 발송에 실패했습니다."),
    CHPW_TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "AUTH-401", "비밀번호 변경 토큰이 유효하지 않습니다."),
    CHPW_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH-402", "가입되지 않은 이메일입니다."),
    CHPW_USED_PASSWORD_BEFORE(HttpStatus.BAD_REQUEST, "AUTH-403", "이전에 사용된 비밀번호입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
