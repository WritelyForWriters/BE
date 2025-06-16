package writeon.api.common.enums.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import writeon.domain.common.enums.CodeInfo;

@Getter
@RequiredArgsConstructor
public enum ParameterException implements CodeInfo {

  PARAMETER_NOT_EXIST(HttpStatus.BAD_REQUEST, "PARAM-001", "파라미터가 존재하지 않습니다."),
  PARAMETER_INVALID(HttpStatus.BAD_REQUEST, "PARAM-002", "유효하지 않은 파라미터입니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;
}
