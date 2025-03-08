package writeon.assistantapiclient.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserModifyRequest {

    private final String tenantId;
    private final UserSetting userSetting;
    private final String query;
    private final String howPolish;

    public UserModifyRequest(UserSetting userSetting, String query, String howPolish) {
        this.tenantId = "1";
        this.userSetting = userSetting;
        this.query = query;
        this.howPolish = howPolish;
    }
}
