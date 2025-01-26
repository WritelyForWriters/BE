package com.writely.common.enums.exception;

import com.writely.common.enums.code.CodeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InternalServerException implements CodeInfo {

    DISCORD_APPENDER_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER-001", "Discord WebHook에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
