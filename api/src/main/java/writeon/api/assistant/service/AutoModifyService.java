package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import writeon.api.assistant.request.AssistantAutoModifyMessageRequest;
import writeon.api.assistant.response.MessageCreateResponse;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.LogUtil;
import writeon.api.product.service.ProductQueryService;
import writeon.assistantapiclient.AssistantApiClient;
import writeon.assistantapiclient.request.AutoModifyRequest;
import writeon.assistantapiclient.request.UserSetting;
import writeon.domain.assistant.Assistant;
import writeon.domain.assistant.automodify.AutoModifyMessage;
import writeon.domain.assistant.automodify.AutoModifyMessageJpaRepository;
import writeon.domain.assistant.enums.AssistantException;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.domain.assistant.enums.AssistantType;
import writeon.domain.assistant.enums.MessageSenderRole;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutoModifyService {

    private final long TIMEOUT = 180_000L;

    private final AutoModifyMessageJpaRepository autoModifyMessageRepository;
    private final AssistantApiClient assistantApiClient;
    private final AssistantService assistantService;
    private final ProductQueryService productQueryService;

    @Transactional
    public MessageCreateResponse createMessage(AssistantAutoModifyMessageRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UUID assistantId = assistantService.create(request.getProductId(), AssistantType.AUTO_MODIFY);
        AutoModifyMessage memberMessage = new AutoModifyMessage(assistantId, MessageSenderRole.MEMBER, request.getContent());
        UUID messageId = autoModifyMessageRepository.save(memberMessage).getId();

        return new MessageCreateResponse(assistantId, messageId);
    }

    public SseEmitter streamAutoModify(UUID assistantId, UUID messageId) {
        Assistant assistant = assistantService.getById(assistantId);

        verifyAnswered(assistantId);
        productQueryService.verifyExist(assistant.getProductId());

        AutoModifyMessage message = getById(messageId);

        UserSetting userSetting = new UserSetting(productQueryService.getById(assistant.getProductId()));
        AutoModifyRequest request = new AutoModifyRequest(
            assistant.getProductId().toString().replaceAll("-", ""), userSetting, message.getContent()
        );

        SseEmitter emitter = new SseEmitter(TIMEOUT);
        StringBuilder responseBuilder = new StringBuilder();

        assistantApiClient.streamAutoModify(request)
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
                    AutoModifyMessage assistantMessage = new AutoModifyMessage(message.getAssistantId(), MessageSenderRole.ASSISTANT, responseBuilder.toString());
                    autoModifyMessageRepository.save(assistantMessage);
                    assistant.updateStatus(AssistantStatus.IN_PROGRESS);
                    emitter.complete();
                }
            );

        return emitter;
    }

    private AutoModifyMessage getById(UUID messageId) {
        return autoModifyMessageRepository.findById(messageId)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST_MESSAGE));
    }

    private void verifyAnswered(UUID assistantId) {
        if (autoModifyMessageRepository.existsByAssistantIdAndMessageContent_Role(assistantId, MessageSenderRole.ASSISTANT)) {
            throw new BaseException(AssistantException.ALREADY_ANSWERED);
        }
    }
}
