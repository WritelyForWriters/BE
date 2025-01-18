package com.writely.common.exception;

import com.writely.common.response.BaseResponse;
import com.writely.common.util.LogUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({Exception.class})
  public BaseResponse<Void> handle(Exception e) {
    if(!(e instanceof BaseException) || ((BaseException)e).getCodeInfo() == null) {
      String message = getStackTrace(e);
      LogUtil.error(message);
      return BaseResponse.failure(message);
    }

    return BaseResponse.failure(((BaseException) e).getCodeInfo());
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
