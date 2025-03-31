package writeon.api.assistant.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import writeon.tables.records.AssistantRecord;

@Getter
public class AssistantHistoryResponse {

  private final UUID id;
  private final String type;
  private final List<MessageHistory> messages;
  private final LocalDateTime createdAt;

  public AssistantHistoryResponse(AssistantRecord assistant, List<MessageHistory> messages) {
    this.id = assistant.getId();
    this.type = assistant.getType();
    this.messages = messages;
    this.createdAt = assistant.getCreatedAt();
  }

  @Getter
  public static class MessageHistory {

    @Schema(title = "메세지 송신 역할")
    private final String role;
    @Schema(title = "사용자가 선택한 구간 / Assistant 답변")
    private final String content;

    public MessageHistory(String role, String content) {
      this.role = role;
      this.content = content;
    }
  }
}
