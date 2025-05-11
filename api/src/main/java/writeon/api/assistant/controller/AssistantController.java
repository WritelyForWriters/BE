package writeon.api.assistant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import writeon.api.assistant.request.*;
import writeon.api.assistant.response.AssistantHistoryResponse;
import writeon.api.assistant.response.AssistantResponse;
import writeon.api.assistant.response.MessageCreateResponse;
import writeon.api.assistant.service.*;
import writeon.api.common.response.CursorResponse;

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
    private final PlannerService plannerService;

    @Operation(summary = "어시스턴트 답변 적용")
    @PostMapping("/{assistantId}/apply")
    public void apply(@PathVariable UUID assistantId) {
        assistantService.apply(assistantId);
    }

    @Operation(summary = "자동 수정")
    @PostMapping("/auto-modify")
    public AssistantResponse autoModify(@RequestBody AssistantAutoModifyRequest request) {
        return autoModifyService.autoModify(request);
    }

    @Operation(summary = "자동 수정 메세지 저장")
    @PostMapping("/auto-modify/messages")
    public MessageCreateResponse createAutoModifyMessage(@RequestBody AssistantAutoModifyRequest request) {
        return autoModifyService.createMessage(request);
    }

    @Operation(summary = "자유 대화")
    @PostMapping("/chat")
    public AssistantResponse chat(@RequestBody AssistantChatRequest request) {
        return chatService.chat(request);
    }

    @Operation(summary = "자유 대화 메세지 저장")
    @PostMapping("/chat/messages")
    public MessageCreateResponse createChatMessage(@RequestBody AssistantChatRequest request) {
        return chatService.createMessage(request);
    }

    @Operation(summary = "어시스턴트 답변 평가")
    @PostMapping("/{assistantId}/evaluate")
    public void evaluate(
        @PathVariable UUID assistantId,
        @RequestBody AssistantEvaluateRequest request) {
        assistantEvaluationService.evaluate(assistantId, request);
    }

    @Operation(summary = "구간 피드백")
    @PostMapping("/feedback")
    public AssistantResponse feedback(@RequestBody AssistantFeedbackRequest request) {
        return feedbackService.feedback(request);
    }

    @Operation(summary = "구간 피드백 메세지 저장")
    @PostMapping("/feedback/messages")
    public MessageCreateResponse createFeedbackMessage(@RequestBody AssistantFeedbackRequest request) {
        return feedbackService.createMessage(request);
    }

    @Operation(summary = "플래너 AI 완성")
    @PostMapping("/planner")
    public AssistantResponse planner(@RequestBody AssistantPlannerRequest request) {
        return plannerService.planner(request);
    }

    @Operation(summary = "플래너 AI 완성 메세지 저장")
    @PostMapping("/planner/messages")
    public MessageCreateResponse createPlannerMessage(@RequestBody AssistantPlannerRequest request) {
        return plannerService.createMessage(request);
    }

    @Operation(summary = "수동 수정")
    @PostMapping("/user-modify")
    public AssistantResponse userModify(@RequestBody AssistantUserModifyRequest request) {
        return userModifyService.userModify(request);
    }

    @Operation(summary = "수동 수정 메세지 저장")
    @PostMapping("/user-modify/messages")
    public MessageCreateResponse createUserModifyMessage(@RequestBody AssistantUserModifyRequest request) {
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

    @Operation(summary = "플래너 AI 완성 스트리밍")
    @GetMapping("/planner/stream")
    public SseEmitter streamPlanner(@RequestParam UUID assistantId) {
        SseEmitter emitter = plannerService.streamPlanner(assistantId);
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

    @Operation(summary = "어시스턴트 사용 내역 조회")
    @GetMapping("/histories")
    public CursorResponse<AssistantHistoryResponse> getHistories(@ParameterObject AssistantHistoryListRequest request) {
        return assistantService.getHistories(request);
    }

    @Operation(summary = "어시스턴트 답변 영구 보관")
    @PutMapping("/{assistantId}/archive")
    public void archive(@PathVariable UUID assistantId) {
        assistantService.archive(assistantId);
    }

    @Operation(summary = "어시스턴트 기능 완료 처리")
    @PutMapping("/{assistantId}/completed")
    public void completed(@PathVariable UUID assistantId) {
        assistantService.completed(assistantId);
    }

    private void setResponseHeaderForSSE() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        if (response != null) {
            response.setContentType("text/event-stream; charset=UTF-8");
        }
    }
}
