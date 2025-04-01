package writeon.api.assistant.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import writeon.tables.records.AssistantMessageRecord;
import writeon.tables.records.AssistantRecord;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AssistantHistoryResponse {

  private final UUID id;
  private final String type;
  private final MemberMessage memberMessage;
  private final AssistantMessage assistantMessage;
  private final LocalDateTime createdAt;

  public AssistantHistoryResponse(AssistantRecord assistant, AssistantMessageRecord memberMessage, AssistantMessageRecord assistantMessage) {
    this.id = assistant.getId();
    this.type = assistant.getType();
    this.memberMessage = new MemberMessage(memberMessage.getContent(), memberMessage.getPrompt());
    this.assistantMessage = new AssistantMessage(assistantMessage.getContent());
    this.createdAt = assistant.getCreatedAt();
  }

  @Getter
  public static class MemberMessage {

    @Schema(title = "사용자가 선택한 구간")
    private final String content;
    @Schema(title = "사용자가 입력한 프롬프트")
    private final String prompt;

    public MemberMessage(String content, String prompt) {
      this.content = content;
      this.prompt = prompt;
    }
  }

  @Getter
  public static class AssistantMessage {

    @Schema(title = "Assistant 답변")
    private final String content;

    public AssistantMessage(String content) {
      this.content = content;
    }
  }
}
