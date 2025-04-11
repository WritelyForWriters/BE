package writeon.domain.product.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import writeon.domain.common.enums.CodeInfo;

@Getter
@RequiredArgsConstructor
public enum ProductException implements CodeInfo {

    NOT_EXIST(HttpStatus.NOT_FOUND, "PRD-001", "존재하지 않는 작품입니다."),
    NOT_EXIST_CHARACTER(HttpStatus.NOT_FOUND, "PRD-002", "존재하지 않는 등장인물 입니다."),
    NOT_EXIST_CUSTOM_FIELD(HttpStatus.NOT_FOUND, "PRD-003", "존재하지 않는 커스텀 필드입니다."),
    NOT_EXIST_MEMO(HttpStatus.NOT_FOUND, "PRD-004", "존재하지 않는 메모입니다."),
    NOT_EXIST_FAVORITE_PROMPT(HttpStatus.NOT_FOUND, "PRD-005", "존재하지 않는 프롬프트입니다.."),
    ALREADY_EXIST_FAVORITE_PROMPT(HttpStatus.BAD_REQUEST, "PRD-006", "이미 즐겨찾기에 추가한 프롬프트입니다."),
    INVALID_FAVORITE_PROMPT_TYPE(HttpStatus.BAD_REQUEST, "PRD-007", "자유 대화 기능의 프롬프트만 즐겨찾기에 등록할 수 있습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
