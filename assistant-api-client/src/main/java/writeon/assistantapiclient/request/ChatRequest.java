package writeon.assistantapiclient.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChatRequest {

    private final UserSetting userSetting;
    private final String query;
    private final String userInput;
    private final String sessionId;

    public ChatRequest(UserSetting userSetting, String query, String userInput, String sessionId) {
        this.userSetting = userSetting;
        this.query = query;
        this.userInput = userInput;
        this.sessionId = sessionId;
    }
}
