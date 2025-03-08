package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import writeon.api.assistant.request.FeedbackMessageRequest;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.LogUtil;
import writeon.api.product.service.ProductQueryService;
import writeon.assistantapiclient.AssistantApiClient;
import writeon.assistantapiclient.request.FeedbackRequest;
import writeon.assistantapiclient.request.UserSetting;
import writeon.domain.assistant.enums.AssistantException;
import writeon.domain.assistant.enums.MessageSenderRole;
import writeon.domain.assistant.feedback.FeedbackMessage;
import writeon.domain.assistant.feedback.FeedbackMessageJpaRepository;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final long TIMEOUT = 180_000L;

    private final FeedbackMessageJpaRepository feedbackMessageRepository;
    private final ProductQueryService productQueryService;
    private final AssistantApiClient assistantApiClient;

    @Transactional
    public UUID createMessage(FeedbackMessageRequest request) {
        productQueryService.verifyExist(request.getProductId());

        FeedbackMessage memberMessage = new FeedbackMessage(request.getProductId(), MessageSenderRole.MEMBER, request.getContent());
        return feedbackMessageRepository.save(memberMessage).getId();
    }

    public SseEmitter streamFeedback(UUID productId, UUID messageId) {
        productQueryService.verifyExist(productId);
        FeedbackMessage message = getById(messageId);

        UserSetting userSetting = new UserSetting(productQueryService.getById(productId));
        FeedbackRequest request = new FeedbackRequest(userSetting, message.getContent());

        SseEmitter emitter = new SseEmitter(TIMEOUT);
        StringBuilder responseBuilder = new StringBuilder();

        assistantApiClient.streamFeedback(request)
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
                    FeedbackMessage assistantMessage = new FeedbackMessage(productId, MessageSenderRole.ASSISTANT, responseBuilder.toString());
                    feedbackMessageRepository.save(assistantMessage);
                    emitter.complete();
                }
            );

        return emitter;
    }

    private FeedbackMessage getById(UUID messageId) {
        return feedbackMessageRepository.findById(messageId)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST_MESSAGE));
    }
}
