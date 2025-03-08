package writeon.api.product.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import writely.tables.records.ProductRecord;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ProductResponse {

    private final UUID id;
    @Schema(title = "제목")
    private final String title;
    @Schema(title = "수정일시")
    private final LocalDateTime updatedAt;

    public ProductResponse(ProductRecord product) {
        this.id = product.getId();
        this.title = product.getContent();
        this.updatedAt = product.getUpdatedAt();
    }
}
