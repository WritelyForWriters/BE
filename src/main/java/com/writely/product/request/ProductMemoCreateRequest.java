package com.writely.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMemoCreateRequest {

    @Schema(title = "내용")
    private String content;
}
