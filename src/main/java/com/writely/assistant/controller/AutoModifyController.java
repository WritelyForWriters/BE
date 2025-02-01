package com.writely.assistant.controller;

import com.writely.assistant.request.AutoModifyRequest;
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
@RequestMapping("/auto-modify")
@Tag(name = "AI 어시스턴트 - 자동 수정")
public class AutoModifyController {

    private final AutoModifyService autoModifyService;

    @Operation(summary = "메세지 저장")
    @PostMapping("/stream/messages")
    public UUID createMessage(@RequestBody AutoModifyRequest request) {
        return autoModifyService.createMessage(request);
    }

    @Operation(summary = "메세지 전송 및 응답(SSE)")
    @GetMapping("/stream/messages")
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
