package com.writely.product.response;

import com.writely.product.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponse {

    @Schema(title = "내용")
    private final String content;
    @Schema(title = "생성일시")
    private final LocalDateTime createdAt;

    public ProductResponse(Product product) {
        this.content = product.getContent();
        this.createdAt = product.getCreatedAt();
    }
}
