package writeon.api.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFavoritePromptCreateRequest {

  @Schema(title = "프롬프트")
  private String prompt;
}
