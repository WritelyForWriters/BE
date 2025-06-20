package writeon.api.assistant.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssistantUserModifyRequest {

    @Schema(title = "작품 ID")
    private UUID productId;
    @Schema(title = "선택 구간")
    private String content;
    @Schema(title = "프롬프트", nullable = true)
    private String prompt;
    @Schema(title = "설정 반영 여부", nullable = true)
    private Boolean shouldApplySetting = true;
}
