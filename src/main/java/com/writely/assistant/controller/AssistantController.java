package com.writely.assistant.controller;

import com.writely.assistant.request.AutoModifyMessageRequest;
import com.writely.assistant.service.AutoModifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assistant")
@Tag(name = "AI 어시스턴트")
public class AssistantController {

    private final AutoModifyService autoModifyService;

    @Operation(summary = "자동 수정 메세지 저장")
    @PostMapping("/auto-modify/messages")
    public UUID createMessage(@RequestBody AutoModifyMessageRequest request) {
        return autoModifyService.createMessage(request);
    }

    @Operation(summary = "자동 수정 스트리밍")
    @GetMapping("/auto-modify/stream")
    public SseEmitter streamMessage(
        @RequestParam UUID productId,
        @RequestParam UUID messageId
    ) {
        SseEmitter emitter = autoModifyService.sendMessage(productId, messageId);
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
