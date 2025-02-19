package com.writely.terms.domain.enums;

import com.writely.common.enums.code.CodeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TermsException implements CodeInfo {
    TERMS_NOT_FOUND(HttpStatus.NOT_FOUND, "TERMS-001", "약관을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
