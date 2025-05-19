package writeon.api.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMemoCompletedRequest {

    @Schema(title = "완료 여부")
    private Boolean isCompleted;
}
