package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import writeon.api.assistant.request.AssistantChatMessageRequest;
import writeon.api.assistant.response.MessageCreateResponse;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.LogUtil;
import writeon.api.product.service.ProductQueryService;
import writeon.assistantapiclient.AssistantApiClient;
import writeon.assistantapiclient.request.ChatRequest;
import writeon.assistantapiclient.request.UserSetting;
import writeon.domain.assistant.Assistant;
import writeon.domain.assistant.chat.ChatMessage;
import writeon.domain.assistant.chat.ChatMessageJpaRepository;
import writeon.domain.assistant.enums.AssistantException;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.domain.assistant.enums.AssistantType;
import writeon.domain.assistant.enums.MessageSenderRole;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final long TIMEOUT = 180_000L;

    private final ChatMessageJpaRepository chatMessageRepository;
    private final AssistantApiClient assistantApiClient;
    private final AssistantService assistantService;
    private final ProductQueryService productQueryService;

    @Transactional
    public MessageCreateResponse createMessage(AssistantChatMessageRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UUID assistantId = assistantService.create(request.getProductId(), AssistantType.USER_MODIFY);
        ChatMessage memberMessage =
            new ChatMessage(assistantId, MessageSenderRole.MEMBER, request.getContent(), request.getPrompt());
        chatMessageRepository.save(memberMessage);

        return new MessageCreateResponse(assistantId);
    }

    public SseEmitter streamChat(UUID assistantId, String sessionId) {
        Assistant assistant = assistantService.getById(assistantId);

        verifyAnswered(assistantId);
        productQueryService.verifyExist(assistant.getProductId());

        ChatMessage message = getMemberMessage(assistantId);

        UserSetting userSetting = new UserSetting(productQueryService.getById(assistant.getProductId()));
        ChatRequest request = new ChatRequest(
            userSetting, message.getContent(), message.getPrompt(), sessionId
        );

        SseEmitter emitter = new SseEmitter(TIMEOUT);
        StringBuilder responseBuilder = new StringBuilder();

        assistantApiClient.streamChat(request)
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
                    ChatMessage assistantMessage = new ChatMessage(message.getAssistantId(), MessageSenderRole.ASSISTANT, responseBuilder.toString(), null);
                    chatMessageRepository.save(assistantMessage);

                    assistantService.modifyStatus(assistantId, AssistantStatus.IN_PROGRESS);
                    emitter.complete();
                }
            );

        return emitter;
    }

    private ChatMessage getMemberMessage(UUID assistantId) {
        return chatMessageRepository.findByAssistantIdAndMessageContent_Role(assistantId, MessageSenderRole.MEMBER)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST_MESSAGE));
    }

    private void verifyAnswered(UUID assistantId) {
        if (chatMessageRepository.existsByAssistantIdAndMessageContent_Role(assistantId, MessageSenderRole.ASSISTANT)) {
            throw new BaseException(AssistantException.ALREADY_ANSWERED);
        }
    }
}
