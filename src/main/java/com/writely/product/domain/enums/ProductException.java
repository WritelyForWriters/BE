package com.writely.product.domain.enums;

import com.writely.common.enums.code.CodeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductException implements CodeInfo {

    NOT_EXIST(HttpStatus.NOT_FOUND, "PRD-001", "존재하지 않는 작품입니다."),
    NOT_EXIST_CHARACTER(HttpStatus.NOT_FOUND, "PRD-002", "존재하지 않는 등장인물 입니다."),
    NOT_EXIST_CUSTOM_FIELD(HttpStatus.NOT_FOUND, "PRD-003", "존재하지 않는 커스텀 필드입니다."),
    NOT_EXIST_MEMO(HttpStatus.NOT_FOUND, "PRD-004", "존재하지 않는 메모입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
