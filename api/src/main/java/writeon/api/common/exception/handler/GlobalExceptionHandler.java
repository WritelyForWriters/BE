package writeon.api.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import writeon.api.common.enums.code.ResultCodeInfo;
import writeon.api.common.exception.BaseException;
import writeon.api.common.response.BaseResponse;
import writeon.api.common.util.LogUtil;
import writeon.domain.common.enums.CodeInfo;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<BaseResponse<?>> handle(Exception e) {
    LogUtil.error(e);

    HttpStatus status = null;
    BaseResponse<?> response = null;

    switch (e) {
      case BaseException ex -> {
        status = ex.getCodeInfo().getStatus();
        response = BaseResponse.failure(ex.getCodeInfo(), ex.getExtraParams());
        if (ex.getCustomMessage() != null) {
          response.setMessage(ex.getCustomMessage());
        }
      }

      case MethodArgumentNotValidException ex -> {
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

      default -> {
        status = ResultCodeInfo.FAILURE.getStatus();
        response = BaseResponse.failure(ResultCodeInfo.FAILURE);
      }
    }

    return new ResponseEntity<>(response, status);
  }

}
