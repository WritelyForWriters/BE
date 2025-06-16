package writeon.api.product.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import writeon.domain.product.ProductMemo;

@Getter
public class ProductMemoResponse {

    private final UUID id;
    private final String title;
    private final String content;
    private final String selectedText;
    private final Integer startIndex;
    private final Integer endIndex;
    private final Boolean isCompleted;
    private final LocalDateTime updatedAt;

    public ProductMemoResponse(ProductMemo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.content = memo.getContent();
        this.selectedText = memo.getSelectedText();
        this.startIndex = memo.getStartIndex();
        this.endIndex = memo.getEndIndex();
        this.isCompleted = memo.getIsCompleted();
        this.updatedAt = memo.getUpdatedAt();
    }
}
