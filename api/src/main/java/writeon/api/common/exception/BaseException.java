package writeon.api.common.exception;

import lombok.Getter;
import writeon.domain.common.enums.CodeInfo;

@Getter
public class BaseException extends RuntimeException {

  private CodeInfo codeInfo;
  private Object extraParams;
  private String customMessage;

  public BaseException() {
  }

  public BaseException(CodeInfo codeInfo) {
    this.codeInfo = codeInfo;
  }

  public BaseException(CodeInfo codeInfo, Object extraParams) {
    this.codeInfo = codeInfo;
    this.extraParams = extraParams;
  }

  public BaseException(CodeInfo codeInfo, Object extraParams, String customMessage) {
    this.codeInfo = codeInfo;
    this.extraParams = extraParams;
    this.customMessage = customMessage;
  }

  @Override
  public String toString() {
    return "BaseException [status=" + codeInfo.getStatus().value() + ", code=" + codeInfo.getCode() + ", message=" + codeInfo.getMessage() + "]";
  }
}
