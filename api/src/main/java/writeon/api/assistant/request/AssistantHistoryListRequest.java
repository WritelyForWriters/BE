package writeon.api.assistant.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import writeon.api.common.request.OffsetRequest;

@Getter
@Setter
public class AssistantHistoryListRequest extends OffsetRequest {

    private LocalDateTime createdAt;
}
