package writeon.api.assistant.request;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantHistoryListRequest {

    @Parameter(required = true)
    private UUID productId;

    @Parameter(description = "cursor pagination 기준값")
    private UUID assistantId;

    @Positive(message = "한 페이지에 조회 할 데이터 수는 양수여야 합니다.")
    private int size = 10;
}
