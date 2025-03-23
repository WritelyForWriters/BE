package writeon.api.assistant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import writeon.api.assistant.request.*;
import writeon.api.assistant.response.AssistantResponse;
import writeon.api.assistant.response.MessageCreateResponse;
import writeon.api.assistant.service.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assistant")
@Tag(name = "AI 어시스턴트")
public class AssistantController {

    private final AssistantService assistantService;
    private final AssistantEvaluationService assistantEvaluationService;
    private final AutoModifyService autoModifyService;
    private final ChatService chatService;
    private final FeedbackService feedbackService;
    private final UserModifyService userModifyService;

    @Operation(summary = "자동 수정 메세지 저장")
    @PostMapping("/auto-modify/messages")
    public MessageCreateResponse createAutoModifyMessage(@RequestBody AssistantAutoModifyMessageRequest request) {
        return autoModifyService.createMessage(request);
    }

    @Operation(summary = "자유 대화 메세지 저장")
    @PostMapping("/chat/messages")
    public MessageCreateResponse createChatMessage(@RequestBody AssistantChatMessageRequest request) {
        return chatService.createMessage(request);
    }

    @Operation(summary = "평가")
    @PostMapping("/evaluations")
    public void evaluate(@RequestBody AssistantEvaluationRequest request) {
        assistantEvaluationService.evaluate(request);
    }

    @Operation(summary = "구간 피드백 메세지 저장")
    @PostMapping("/feedback/messages")
    public MessageCreateResponse createFeedbackMessage(@RequestBody AssistantFeedbackMessageRequest request) {
        return feedbackService.createMessage(request);
    }

    @Operation(summary = "수동 수정 메세지 저장")
    @PostMapping("/user-modify/messages")
    public MessageCreateResponse createUserModifyMessage(@RequestBody AssistantUserModifyMessageRequest request) {
        return userModifyService.createMessage(request);
    }

    @Operation(summary = "자유 대화 - 웹 검색 모드")
    @PostMapping("/chat/research")
    public AssistantResponse research(@RequestBody AssistantResearchRequest request) {
        return chatService.research(request);
    }

    @Operation(summary = "자동 수정 스트리밍")
    @GetMapping("/auto-modify/stream")
    public SseEmitter streamAutoModify(@RequestParam UUID assistantId) {
        SseEmitter emitter = autoModifyService.streamAutoModify(assistantId);
        setResponseHeaderForSSE();
        return emitter;
    }

    @Operation(summary = "자유 대화 스트리밍")
    @GetMapping("/chat/stream")
    public SseEmitter streamChat(
        @RequestParam UUID assistantId,
        @RequestParam String sessionId
    ) {
        SseEmitter emitter = chatService.streamChat(assistantId, sessionId);
        setResponseHeaderForSSE();
        return emitter;
    }

    @Operation(summary = "구간 피드백 스트리밍")
    @GetMapping("/feedback/stream")
    public SseEmitter streamFeedback(@RequestParam UUID assistantId) {
        SseEmitter emitter = feedbackService.streamFeedback(assistantId);
        setResponseHeaderForSSE();
        return emitter;
    }

    @Operation(summary = "수동 수정 스트리밍")
    @GetMapping("/user-modify/stream")
    public SseEmitter streamUserModify(@RequestParam UUID assistantId) {
        SseEmitter emitter = userModifyService.streamUserModify(assistantId);
        setResponseHeaderForSSE();
        return emitter;
    }

    @Operation(summary = "AI 어시 기능 완료 처리")
    @PutMapping("/{assistantId}/completed")
    public void completed(@PathVariable UUID assistantId, @RequestBody AssistantCompletedRequest request) {
        assistantService.completed(assistantId, request);
    }

    private void setResponseHeaderForSSE() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        if (response != null) {
            response.setContentType("text/event-stream; charset=UTF-8");
        }
    }
}
