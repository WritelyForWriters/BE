package writeon.domain.assistant.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import writeon.domain.common.enums.CodeInfo;

@Getter
@RequiredArgsConstructor
public enum AssistantException implements CodeInfo {

    NOT_EXIST(HttpStatus.NOT_FOUND, "AI-001", "존재하지 않는 Assistant입니다."),
    NOT_EXIST_MESSAGE(HttpStatus.NOT_FOUND, "AI-002", "존재하지 않는 메세지입니다."),
    SSE_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AI-003", "SSE 데이터 전송 중 오류가 발생했습니다."),
    ALREADY_ANSWERED(HttpStatus.BAD_REQUEST, "AI-004", "이미 응답된 메세지입니다."),
    ALREADY_EVALUATED(HttpStatus.BAD_REQUEST, "AI-005", "이미 평가되었습니다."),
    CANNOT_BE_COMPLETED(HttpStatus.BAD_REQUEST, "AI-006", "완료처리 할 수 없는 상태입니다."),
    WEBCLIENT_REQUEST_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AI-007", "WebClient 요청 중 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
