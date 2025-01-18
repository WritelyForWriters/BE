package com.writely.product.domain.enums;

import com.writely.common.enums.code.CodeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductException implements CodeInfo {

    NOT_EXIST(HttpStatus.NOT_FOUND, "PRD-001", "존재하지 않는 작품입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
