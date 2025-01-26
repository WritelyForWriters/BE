package com.writely.common.exception;

import com.writely.common.enums.code.CodeInfo;
import com.writely.common.response.BaseResponse;
import com.writely.common.util.LogUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<BaseResponse<Void>> handle(Exception e) {
    if(!(e instanceof BaseException) || ((BaseException)e).getCodeInfo() == null) {
      String message = getStackTrace(e);
      LogUtil.error(message);

      BaseResponse<Void> response = BaseResponse.failure(message);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    LogUtil.error(getStackTrace(e));
    CodeInfo codeInfo = ((BaseException) e).getCodeInfo();
    BaseResponse<Void> response = BaseResponse.failure(codeInfo);
    return new ResponseEntity<>(response, codeInfo.getStatus());
  }

  private String getStackTrace(Throwable throwable) {
    if (throwable == null) {
      return "No exception provided.";
    }
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    throwable.printStackTrace(printWriter);
    return stringWriter.toString();
  }
}
