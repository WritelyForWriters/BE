package com.writely.assistant.domain.enums;

import com.writely.common.enums.code.CodeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AssistantException implements CodeInfo {

    NOT_EXIST_MESSAGE(HttpStatus.NOT_FOUND, "AI-001", "존재하지 않는 메세지입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
