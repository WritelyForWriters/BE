package writeon.api.common.request;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OffsetRequest {

    @Positive(message = "페이지는 양수여야 합니다.")
    private int page = 1;

    @Positive(message = "한 페이지에 조회 할 데이터 수는 양수여야 합니다.")
    private int size = 10;
}
