package writeon.api.assistant.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import writeon.tables.records.AssistantMessageRecord;
import writeon.tables.records.AssistantRecord;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AssistantHistoryResponse {

    private final UUID id;
    @Schema(title = "어시스턴트 타입", description = "auto modify, feedback, chat, user modify")
    private final String type;
    private final MemberMessage memberMessage;
    private final AssistantMessage assistantMessage;
    private final LocalDateTime createdAt;

    public AssistantHistoryResponse(AssistantRecord assistant, AssistantMessageRecord memberMessage, Boolean isFavoritedPrompt, AssistantMessageRecord assistantMessage) {
        this.id = assistant.getId();
        this.type = assistant.getType();
        this.memberMessage = new MemberMessage(memberMessage, isFavoritedPrompt);
        this.assistantMessage = new AssistantMessage(assistantMessage, assistant.getIsApplied());
        this.createdAt = assistant.getCreatedAt();
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MemberMessage {

        @Schema(title = "메세지 ID")
        private final UUID id;
        @Schema(title = "사용자가 선택한 구간")
        private final String content;
        @Schema(title = "사용자가 입력한 프롬프트")
        private final String prompt;
        @Schema(title = "즐겨찾기 적용 여부")
        private final Boolean isFavoritedPrompt;

        public MemberMessage(AssistantMessageRecord message, Boolean isFavoritedPrompt) {
            this.id = message.getId();
            this.content = message.getContent();
            this.prompt = message.getPrompt();
            this.isFavoritedPrompt = isFavoritedPrompt;
        }
    }

    @Getter
    public static class AssistantMessage {

        @Schema(title = "메세지 ID")
        private final UUID id;
        @Schema(title = "Assistant 답변")
        private final String content;
        @Schema(title = "답변 적용 여부")
        private final Boolean isApplied;

        public AssistantMessage(AssistantMessageRecord message, Boolean isApplied) {
            this.id = message.getId();
            this.content = message.getContent();
            this.isApplied = isApplied;
        }
    }
}
