package writeon.api.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMemoSaveRequest {

    @Schema(title = "제목", nullable = true)
    private String title;
    @Schema(title = "내용")
    private String content;
    private String selectedText;
    private Integer startIndex;
    private Integer endIndex;
    @Schema(title = "완료 여부", description = "메모 생성 시, false로 저장됩니다.", nullable = true)
    private Boolean isCompleted;
}
