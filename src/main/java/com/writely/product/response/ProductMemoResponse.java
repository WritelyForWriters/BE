package com.writely.product.response;

import com.writely.product.domain.ProductMemo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductMemoResponse {

    private final UUID id;
    @Schema(title = "내용")
    private final String content;

    public ProductMemoResponse(ProductMemo memo) {
        this.id = memo.getId();
        this.content = memo.getContent();
    }
}
