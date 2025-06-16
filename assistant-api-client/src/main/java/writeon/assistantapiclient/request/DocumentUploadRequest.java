package writeon.assistantapiclient.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DocumentUploadRequest {

    private final String tenantId;
    private final String content;

    public DocumentUploadRequest(String tenantId, String content) {
        this.tenantId = tenantId;
        this.content = content;
    }
}
