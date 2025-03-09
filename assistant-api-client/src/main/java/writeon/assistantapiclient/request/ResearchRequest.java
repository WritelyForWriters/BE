package writeon.assistantapiclient.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResearchRequest {

    private final String sessionId;
    private final UserSetting userSetting;
    private final String query;

    public ResearchRequest(String sessionId, UserSetting userSetting, String query) {
        this.sessionId = sessionId;
        this.userSetting = userSetting;
        this.query = query;
    }
}
