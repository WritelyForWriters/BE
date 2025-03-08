package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import writeon.api.assistant.request.UserModifyMessageRequest;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.LogUtil;
import writeon.api.product.service.ProductQueryService;
import writeon.assistantapiclient.AssistantApiClient;
import writeon.assistantapiclient.request.UserModifyRequest;
import writeon.assistantapiclient.request.UserSetting;
import writeon.domain.assistant.enums.AssistantException;
import writeon.domain.assistant.enums.MessageSenderRole;
import writeon.domain.assistant.usermodify.UserModifyMessage;
import writeon.domain.assistant.usermodify.UserModifyMessageJpaRepository;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserModifyService {

    private final long TIMEOUT = 180_000L;

    private final UserModifyMessageJpaRepository userModifyMessageRepository;
    private final ProductQueryService productQueryService;
    private final AssistantApiClient assistantApiClient;

    @Transactional
    public UUID createMessage(UserModifyMessageRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UserModifyMessage memberMessage =
            new UserModifyMessage(request.getProductId(), MessageSenderRole.MEMBER, request.getContent(), request.getPrompt());
        return userModifyMessageRepository.save(memberMessage).getId();
    }

    public SseEmitter streamUserModify(UUID productId, UUID messageId) {
        productQueryService.verifyExist(productId);
        UserModifyMessage message = getById(messageId);

        UserSetting userSetting = new UserSetting(productQueryService.getById(productId));
        UserModifyRequest request = new UserModifyRequest(userSetting, message.getContent(), message.getPrompt());

        SseEmitter emitter = new SseEmitter(TIMEOUT);
        StringBuilder responseBuilder = new StringBuilder();

        assistantApiClient.streamUserModify(request)
            .subscribe(
                data -> {
                    try {
                        emitter.send(SseEmitter.event().data(data));
                        responseBuilder.append(data);
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
                () -> {
                    UserModifyMessage assistantMessage = new UserModifyMessage(productId, MessageSenderRole.ASSISTANT, responseBuilder.toString(), null);
                    userModifyMessageRepository.save(assistantMessage);
                    emitter.complete();
                }
            );

        return emitter;
    }

    private UserModifyMessage getById(UUID messageId) {
        return userModifyMessageRepository.findById(messageId)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST_MESSAGE));
    }
}
