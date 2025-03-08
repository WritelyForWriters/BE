package writeon.domain.assistant.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import writeon.domain.common.enums.CodeInfo;

@Getter
@RequiredArgsConstructor
public enum AssistantException implements CodeInfo {

    NOT_EXIST_MESSAGE(HttpStatus.NOT_FOUND, "AI-001", "존재하지 않는 메세지입니다."),
    SSE_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AI-002", "SSE 데이터 전송 중 오류가 발생했습니다."),
    WEBCLIENT_REQUEST_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AI-003", "WebClient 요청 중 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
