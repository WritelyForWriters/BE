package writeon.api.product.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import writeon.domain.product.ProductFavoritePrompt;
import writeon.domain.product.ProductMemo;

@Getter
public class ProductFavoritePromptResponse {

    private final UUID id;
    private final String prompt;
    private final LocalDateTime createdAt;

    public ProductFavoritePromptResponse(ProductFavoritePrompt prompt) {
        this.id = prompt.getId();
        this.prompt = prompt.getPrompt();
        this.createdAt = prompt.getCreatedAt();
    }
}
