package writeon.api.product.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import writeon.tables.records.ProductRecord;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ProductResponse {

    private final UUID id;
    @Schema(title = "제목", nullable = true)
    private final String title;
    @Schema(title = "장르", nullable = true)
    private final String genre;
    @Schema(title = "생성일시")
    private final LocalDateTime createdAt;
    @Schema(title = "수정일시")
    private final LocalDateTime updatedAt;

    public ProductResponse(ProductRecord product, String genre) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.genre = genre;
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}
