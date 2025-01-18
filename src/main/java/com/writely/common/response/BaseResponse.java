package com.writely.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.writely.common.enums.code.CodeInfo;
import com.writely.common.enums.code.ResultCodeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    @Schema(title = "코드", description = "API 처리 결과 코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "RESULT-001")
    private String code;

    @Schema(title = "결과 데이터", description = "API 처리 결과 데이터", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private T result;

    @Schema(title = "메시지", description = "API 처리 결과 메시지", requiredMode = Schema.RequiredMode.REQUIRED, example = "성공적으로 수행하였습니다.")
    private String message;

    protected BaseResponse() {}

    public static BaseResponse<Void> success() {
        BaseResponse<Void> res = new BaseResponse<>();
        res.code = ResultCodeInfo.SUCCESS.getCode();
        res.message = ResultCodeInfo.SUCCESS.getMessage();
        return res;
    }

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> res = new BaseResponse<>();
        res.code = ResultCodeInfo.SUCCESS.getCode();
        res.result = data;
        res.message = ResultCodeInfo.SUCCESS.getMessage();
        return res;
    }

    public static <T> BaseResponse<T> failure() {
        BaseResponse<T> res = new BaseResponse<>();
        res.message = ResultCodeInfo.FAILURE.getMessage();
        return res;
    }

    public static <T> BaseResponse<T> failure(String message) {
        BaseResponse<T> res = new BaseResponse<>();
        res.message = message;
        return res;
    }

    public static <T> BaseResponse<T> failure(CodeInfo codeInfo) {
        BaseResponse<T> res = new BaseResponse<>();
        res.code = codeInfo.getCode();
        res.message = codeInfo.getMessage();
        return res;
    }
}


