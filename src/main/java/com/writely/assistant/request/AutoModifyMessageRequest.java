package com.writely.assistant.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AutoModifyMessageRequest {

    @Schema(title = "작품 ID")
    private UUID productId;
    @Schema(title = "내용")
    private String content;
}
