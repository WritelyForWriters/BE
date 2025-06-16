package writeon.api.product.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProductFixedMessageResponse {

    @Schema(title = "메세지 ID")
    private final UUID messageId;
    private final String content;

    public ProductFixedMessageResponse(UUID messageId, String content) {
        this.messageId = messageId;
        this.content = content;
    }
}
