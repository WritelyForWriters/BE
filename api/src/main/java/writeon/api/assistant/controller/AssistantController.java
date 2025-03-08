package writeon.api.assistant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import writeon.api.assistant.request.AutoModifyMessageRequest;
import writeon.api.assistant.request.FeedbackMessageRequest;
import writeon.api.assistant.request.UserModifyMessageRequest;
import writeon.api.assistant.service.AutoModifyService;
import writeon.api.assistant.service.FeedbackService;
import writeon.api.assistant.service.UserModifyService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assistant")
@Tag(name = "AI 어시스턴트")
public class AssistantController {

    private final AutoModifyService autoModifyService;
    private final FeedbackService feedbackService;
    private final UserModifyService userModifyService;

    @Operation(summary = "자동 수정 메세지 저장")
    @PostMapping("/auto-modify/messages")
    public UUID createAutoModifyMessage(@RequestBody AutoModifyMessageRequest request) {
        return autoModifyService.createMessage(request);
    }

    @Operation(summary = "구간 피드백 메세지 저장")
    @PostMapping("/feedback/messages")
    public UUID createFeedbackMessage(@RequestBody FeedbackMessageRequest request) {
        return feedbackService.createMessage(request);
    }

    @Operation(summary = "수동 수정 메세지 저장")
    @PostMapping("/user-modify/messages")
    public UUID createUserModifyMessage(@RequestBody UserModifyMessageRequest request) {
        return userModifyService.createMessage(request);
    }

    @Operation(summary = "자동 수정 스트리밍")
    @GetMapping("/auto-modify/stream")
    public SseEmitter streamAutoModify(
        @RequestParam UUID productId,
        @RequestParam UUID messageId
    ) {
        SseEmitter emitter = autoModifyService.streamAutoModify(productId, messageId);
        setResponseHeaderForSSE();
        return emitter;
    }

    @Operation(summary = "구간 피드백 스트리밍")
    @GetMapping("/feedback/stream")
    public SseEmitter streamFeedback(
        @RequestParam UUID productId,
        @RequestParam UUID messageId
    ) {
        SseEmitter emitter = feedbackService.streamFeedback(productId, messageId);
        setResponseHeaderForSSE();
        return emitter;
    }

    @Operation(summary = "수동 수정 스트리밍")
    @GetMapping("/user-modify/stream")
    public SseEmitter streamUserModify(
        @RequestParam UUID productId,
        @RequestParam UUID messageId
    ) {
        SseEmitter emitter = userModifyService.streamUserModify(productId, messageId);
        setResponseHeaderForSSE();
        return emitter;
    }

    private void setResponseHeaderForSSE() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        if (response != null) {
            response.setContentType("text/event-stream; charset=UTF-8");
        }
    }
}
