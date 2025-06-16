package writeon.assistantapiclient.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FeedbackRequest {

    private final String tenantId;
    private final UserSetting userSetting;
    private final String query;

    public FeedbackRequest(String tenantId, UserSetting userSetting, String query) {
        this.tenantId = tenantId;
        this.userSetting = userSetting;
        this.query = query;
    }
}
