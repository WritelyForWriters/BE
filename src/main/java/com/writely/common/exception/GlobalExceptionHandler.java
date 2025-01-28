package com.writely.common.exception;

import com.writely.common.enums.code.CodeInfo;
import com.writely.common.enums.code.ResultCodeInfo;
import com.writely.common.response.BaseResponse;
import com.writely.common.util.LogUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<BaseResponse<?>> handle(Exception e) {
    HttpStatus status = null;
    BaseResponse<?> response = null;

    if (e instanceof BaseException ex) {
      CodeInfo codeInfo = ex.getCodeInfo();

      status = codeInfo.getStatus();
      response = BaseResponse.failure(codeInfo);
    } else if (e instanceof MethodArgumentNotValidException ex) {
      CodeInfo codeInfo = ResultCodeInfo.BAD_REQUEST;

      status = codeInfo.getStatus();
      response = BaseResponse.failure(codeInfo);
      String responseMessage = codeInfo.getMessage()
              + " "
              + ex.getBindingResult()
              .getFieldErrors()
              .stream()
              .map(error -> error.getField() + " 이(가)" + error.getDefaultMessage())
              .collect(Collectors.joining(" "));
      response.setMessage(responseMessage);
    }

    if (status == null) {
      status = ResultCodeInfo.FAILURE.getStatus();
    }
    if (response == null) {
      response = BaseResponse.failure();
    }

    LogUtil.error(e);
    return new ResponseEntity<>(response, status);
  }

}
