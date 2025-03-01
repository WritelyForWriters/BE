package com.writely.common.exception;

import com.writely.common.enums.code.CodeInfo;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

  private CodeInfo codeInfo;
  private Object extraParams;

  public BaseException() {
  }

  public BaseException(CodeInfo codeInfo) {
    this.codeInfo = codeInfo;
  }

  public BaseException(CodeInfo codeInfo, Object extraParams) {
    this.codeInfo = codeInfo;
    this.extraParams = extraParams;
  }

  @Override
  public String toString() {
    return "BaseException [status=" + codeInfo.getStatus().value() + ", code=" + codeInfo.getCode() + ", message=" + codeInfo.getMessage() + "]";
  }
}
