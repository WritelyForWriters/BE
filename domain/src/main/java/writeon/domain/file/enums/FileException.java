package writeon.domain.file.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import writeon.domain.common.enums.CodeInfo;

@Getter
@RequiredArgsConstructor
public enum FileException implements CodeInfo {
    UNSUPPORTED_EXTENSION(HttpStatus.NOT_FOUND, "FILE-001", "지원하지 않는 확장자입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
