package com.writely.common.enums.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCodeInfo implements CodeInfo {

    SUCCESS(HttpStatus.OK, "RESULT-001", "성공적으로 수행하였습니다."),
    FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "RESULT-002", "알 수 없는 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "RESULT-003", "잘못된 요청입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
