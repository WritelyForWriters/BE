package com.writely.assistant.service;

import com.writely.assistant.domain.automodify.AutoModifyMessage;
import com.writely.assistant.domain.automodify.AutoModifyMessageJpaRepository;
import com.writely.assistant.domain.enums.AssistantException;
import com.writely.assistant.domain.enums.MessageSenderRole;
import com.writely.assistant.request.AutoModifyRequest;
import com.writely.assistant.request.ChatRequest;
import com.writely.common.exception.BaseException;
import com.writely.product.domain.enums.ProductException;
import com.writely.product.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutoModifyService {

    private final long TIMEOUT = 180_000L;

    private final AutoModifyMessageJpaRepository autoModifyMessageRepository;
    private final ProductJpaRepository productRepository;

    @Transactional
    public UUID createMessage(AutoModifyRequest request) {
        verifyExistProduct(request.getProductId());

        AutoModifyMessage autoModifyMemberMessage = new AutoModifyMessage(request.getProductId(), MessageSenderRole.MEMBER, request.getContent());
        return autoModifyMessageRepository.save(autoModifyMemberMessage).getId();
    }

    public SseEmitter sendMessage(UUID productId, UUID messageId) {
        verifyExistProduct(productId);
        AutoModifyMessage message = getById(messageId);

        SseEmitter emitter = new SseEmitter(TIMEOUT);
        ChatRequest chatRequest = new ChatRequest(message.getContent());
        WebClient.create("http://localhost:8000/chat/stream")
            .post()
            .bodyValue(chatRequest)
            .retrieve()
            .bodyToFlux(String.class)
            .subscribe(
                data -> {
                    try {
                        emitter.send(SseEmitter.event().data(data));
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                },
                error -> emitter.completeWithError(error),
                emitter::complete
            );

        return emitter;
    }

    private AutoModifyMessage getById(UUID messageId) {
        return autoModifyMessageRepository.findById(messageId)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST_MESSAGE));
    }

    private void verifyExistProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new BaseException(ProductException.NOT_EXIST);
        }
    }
}
