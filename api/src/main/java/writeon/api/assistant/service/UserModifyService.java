package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import writeon.api.assistant.request.AssistantUserModifyMessageRequest;
import writeon.api.assistant.response.MessageCreateResponse;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.LogUtil;
import writeon.api.common.util.MemberUtil;
import writeon.api.product.service.ProductQueryService;
import writeon.assistantapiclient.AssistantApiClient;
import writeon.assistantapiclient.request.UserModifyRequest;
import writeon.assistantapiclient.request.UserSetting;
import writeon.domain.assistant.Assistant;
import writeon.domain.assistant.enums.AssistantException;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.domain.assistant.enums.AssistantType;
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
    private final AssistantApiClient assistantApiClient;
    private final AssistantService assistantService;
    private final ProductQueryService productQueryService;

    @Transactional
    public MessageCreateResponse createMessage(AssistantUserModifyMessageRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UUID assistantId = assistantService.create(request.getProductId(), AssistantType.USER_MODIFY);
        UserModifyMessage memberMessage = UserModifyMessage.builder()
            .assistantId(assistantId)
            .role(MessageSenderRole.MEMBER)
            .content(request.getContent())
            .prompt(request.getPrompt())
            .createdBy(MemberUtil.getMemberId())
            .build();

        userModifyMessageRepository.save(memberMessage);

        return new MessageCreateResponse(assistantId);
    }

    public SseEmitter streamUserModify(UUID assistantId) {
        Assistant assistant = assistantService.getById(assistantId, MemberUtil.getMemberId());

        verifyAnswered(assistantId);
        productQueryService.verifyExist(assistant.getProductId());

        UserModifyMessage message = getMemberMessage(assistantId);

        UserSetting userSetting = new UserSetting(productQueryService.getById(assistant.getProductId()));
        UserModifyRequest request = new UserModifyRequest(
            assistant.getProductId().toString().replaceAll("-", ""), userSetting, message.getContent(), message.getPrompt()
        );

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
                    UserModifyMessage assistantMessage = UserModifyMessage.builder()
                        .assistantId(assistantId)
                        .role(MessageSenderRole.ASSISTANT)
                        .content(responseBuilder.toString())
                        .createdBy(message.getCreatedBy())
                        .build();

                    userModifyMessageRepository.save(assistantMessage);

                    assistantService.modifyStatus(assistantId, AssistantStatus.IN_PROGRESS);
                    emitter.complete();
                }
            );

        return emitter;
    }

    private UserModifyMessage getMemberMessage(UUID assistantId) {
        return userModifyMessageRepository.findByAssistantIdAndMessageContent_Role(assistantId, MessageSenderRole.MEMBER)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST_MESSAGE));
    }

    private void verifyAnswered(UUID assistantId) {
        if (userModifyMessageRepository.existsByAssistantIdAndMessageContent_Role(assistantId, MessageSenderRole.ASSISTANT)) {
            throw new BaseException(AssistantException.ALREADY_ANSWERED);
        }
    }
}
