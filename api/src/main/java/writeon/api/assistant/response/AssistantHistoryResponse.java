package writeon.api.assistant.response;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AssistantHistoryResponse {

    private final UUID id;
    private final String type;
    private final MemberMessage memberMessage;
    private final AssistantMessage assistantMessage;
    private final LocalDateTime createdAt;

    public AssistantHistoryResponse(UUID id, String type, Boolean isApplied, LocalDateTime createdAt,
            String memberMessageContent, String memberMessagePrompt, String assistantMessageContent) {
        this.id = id;
        this.type = type;
        this.memberMessage = new MemberMessage(memberMessageContent, memberMessagePrompt);
        this.assistantMessage = new AssistantMessage(assistantMessageContent, isApplied);
        this.createdAt = createdAt;
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
        @Schema(title = "답변 적용 여부")
        private final Boolean isApplied;

        public AssistantMessage(String content, Boolean isApplied) {
            this.content = content;
            this.isApplied = isApplied;
        }
    }
}
