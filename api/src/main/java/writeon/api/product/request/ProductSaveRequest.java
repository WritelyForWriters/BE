package writeon.api.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSaveRequest {

    @Schema(title = "제목")
    private String title;
    @Schema(title = "내용")
    private String content;
    @Schema(title = "자동저장 여부")
    private Boolean isAutoSave;
}
