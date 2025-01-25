package com.writely.product.response;

import com.writely.product.domain.Product;
import com.writely.product.domain.ProductMemo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductDetailResponse {

    private final UUID id;
    @Schema(title = "내용")
    private final String content;
    @Schema(title = "수정일시")
    private final LocalDateTime updatedAt;
    private final List<Memo> memos;

    public ProductDetailResponse(Product product) {
        this.id = product.getId();
        this.content = product.getContent();
        this.updatedAt = product.getUpdatedAt();
        this.memos = product.getMemos().stream()
            .map(Memo::new)
            .toList();
    }

    @Getter
    @Setter
    public static class Memo {

        private final UUID id;
        @Schema(title = "내용")
        private final String content;

        public Memo(ProductMemo memo) {
            this.id = memo.getId();
            this.content = memo.getContent();
        }
    }
}
