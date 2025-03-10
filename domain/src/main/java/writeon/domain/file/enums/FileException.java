package writeon.domain.file.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import writeon.domain.common.enums.CodeInfo;

@Getter
@RequiredArgsConstructor
public enum FileException implements CodeInfo {
    UNSUPPORTED_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "FILE-001", "지원하지 않는 컨텐츠 타입입니다."),
    MAX_FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "FILE-002", "업로드 가능한 파일 최대 크기를 초과했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
