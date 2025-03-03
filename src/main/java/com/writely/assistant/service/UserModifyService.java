package com.writely.assistant.service;

import com.writely.assistant.domain.enums.AssistantException;
import com.writely.assistant.domain.enums.MessageSenderRole;
import com.writely.assistant.domain.usermodify.UserModifyMessage;
import com.writely.assistant.domain.usermodify.UserModifyMessageJpaRepository;
import com.writely.assistant.request.UserModifyMessageRequest;
import com.writely.assistant.request.UserSetting;
import com.writely.common.exception.BaseException;
import com.writely.common.util.LogUtil;
import com.writely.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserModifyService {

    @Value("${service.assistant.url}")
    private String assistantUrl;

    private final long TIMEOUT = 180_000L;

    private final UserModifyMessageJpaRepository userModifyMessageJpaRepository;
    private final ProductQueryService productQueryService;

    @Transactional
    public UUID createMessage(UserModifyMessageRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UserModifyMessage userModifyMemberMessage =
            new UserModifyMessage(request.getProductId(), MessageSenderRole.MEMBER, request.getContent(), request.getPrompt());
        return userModifyMessageJpaRepository.save(userModifyMemberMessage).getId();
    }

    public SseEmitter sendMessage(UUID productId, UUID messageId) {
        productQueryService.verifyExist(productId);
        UserModifyMessage message = getById(messageId);

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("tenant_id", "1");
        requestMap.put("user_setting", new UserSetting(productQueryService.getById(productId)));
        requestMap.put("query", message.getContent());
        requestMap.put("how_polish", message.getPrompt());

        SseEmitter emitter = new SseEmitter(TIMEOUT);
        WebClient.create(assistantUrl + "/v1/assistant/user-modify/stream")
            .post()
            .bodyValue(requestMap)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> LogUtil.error("Error response: " + errorBody)) // 오류 응답 출력
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody))) // 예외로 변환
            )
            .bodyToFlux(String.class)
            .subscribe(
                data -> {
                    try {
                        emitter.send(SseEmitter.event().data(data));
                    } catch (IOException e) {
                        emitter.completeWithError(new BaseException(AssistantException.SSE_SEND_ERROR));
                    }
                },
                error -> {
                    LogUtil.error(error);
                    BaseException exception = new BaseException(AssistantException.WEBCLIENT_REQUEST_ERROR);
                    emitter.completeWithError(exception);
                    throw exception;
                },
                emitter::complete
            );

        return emitter;
    }

    private UserModifyMessage getById(UUID messageId) {
        return userModifyMessageJpaRepository.findById(messageId)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST_MESSAGE));
    }
}
