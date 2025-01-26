package com.writely.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReissueRequest {
    @Schema(title = "리프래시 토큰")
    private String refreshToken;

}
