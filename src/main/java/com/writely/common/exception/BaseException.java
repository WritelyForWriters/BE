package com.writely.common.exception;

import com.writely.common.enums.code.CodeInfo;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

  private CodeInfo codeInfo;

  public BaseException() {
  }

  public BaseException(CodeInfo codeInfo) {
    this.codeInfo = codeInfo;
  }

  @Override
  public String toString() {
    return "BaseException [status=" + codeInfo.getStatus().value() + ", code=" + codeInfo.getCode() + ", message=" + codeInfo.getMessage() + "]";
  }
}
