package writeon.api.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductFavoritePromptCreateRequest {

  @Schema(title = "어시스턴트 ID")
  private UUID assistantId;
}
