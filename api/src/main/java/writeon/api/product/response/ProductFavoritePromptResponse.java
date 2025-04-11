package writeon.api.product.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ProductFavoritePromptResponse {

    @Schema(title = "메세지 ID")
    private final UUID messageId;
    private final String prompt;
    private final LocalDateTime createdAt;

    public ProductFavoritePromptResponse(UUID messageId, String prompt, LocalDateTime createdAt) {
        this.messageId = messageId;
        this.prompt = prompt;
        this.createdAt = createdAt;
    }
}
