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
    private final FeedbackService feedbackService;
    private final UserModifyService userModifyService;
    private final ResearchService researchService;

    @Operation(summary = "자동 수정 메세지 저장")
    @PostMapping("/auto-modify/messages")
    public MessageCreateResponse createAutoModifyMessage(@RequestBody AssistantAutoModifyMessageRequest request) {
        return autoModifyService.createMessage(request);
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

    @Operation(summary = "자유 대화")
    @PostMapping("/research")
    public AssistantResponse research(@RequestBody AssistantResearchRequest request) {
        return researchService.research(request);
    }

    @Operation(summary = "자동 수정 스트리밍")
    @GetMapping("/auto-modify/stream")
    public SseEmitter streamAutoModify(
        @RequestParam UUID assistantId,
        @RequestParam UUID messageId
    ) {
        SseEmitter emitter = autoModifyService.streamAutoModify(assistantId, messageId);
        setResponseHeaderForSSE();
        return emitter;
    }

    @Operation(summary = "구간 피드백 스트리밍")
    @GetMapping("/feedback/stream")
    public SseEmitter streamFeedback(
        @RequestParam UUID assistantId,
        @RequestParam UUID messageId
    ) {
        SseEmitter emitter = feedbackService.streamFeedback(assistantId, messageId);
        setResponseHeaderForSSE();
        return emitter;
    }

    @Operation(summary = "수동 수정 스트리밍")
    @GetMapping("/user-modify/stream")
    public SseEmitter streamUserModify(
        @RequestParam UUID assistantId,
        @RequestParam UUID messageId
    ) {
        SseEmitter emitter = userModifyService.streamUserModify(assistantId, messageId);
        setResponseHeaderForSSE();
        return emitter;
    }

    @Operation(summary = "응답 반영")
    @PutMapping("/reflections/{assistantId}")
    public void reflect(@PathVariable UUID assistantId) {
        assistantService.reflect(assistantId);
    }

    private void setResponseHeaderForSSE() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        if (response != null) {
            response.setContentType("text/event-stream; charset=UTF-8");
        }
    }
}
