package writeon.api.product.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import writeon.domain.product.Product;
import writeon.domain.product.ProductMemo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductDetailResponse {

    private final UUID id;
    @Schema(title = "제목")
    private final String title;
    @Schema(title = "내용")
    private final String content;
    @Schema(title = "수정일시")
    private final LocalDateTime updatedAt;
    private final List<ProductMemoResponse> memos;

    public ProductDetailResponse(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.content = product.getContent();
        this.updatedAt = product.getUpdatedAt();
        this.memos = product.getMemos().stream()
            .map(ProductMemoResponse::new)
            .toList();
    }
}
