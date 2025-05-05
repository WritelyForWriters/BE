package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import writeon.api.assistant.request.AssistantAutoModifyRequest;
import writeon.api.assistant.response.AssistantResponse;
import writeon.api.assistant.response.MessageCreateResponse;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.LogUtil;
import writeon.api.common.util.MemberUtil;
import writeon.api.product.service.ProductQueryService;
import writeon.assistantapiclient.AssistantApiClient;
import writeon.assistantapiclient.request.AutoModifyRequest;
import writeon.assistantapiclient.request.UserSetting;
import writeon.domain.assistant.Assistant;
import writeon.domain.assistant.AssistantMessage;
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

    private final AssistantApiClient assistantApiClient;
    private final AssistantService assistantService;
    private final ProductQueryService productQueryService;

    @Transactional
    public AssistantResponse autoModify(AssistantAutoModifyRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UUID assistantId = assistantService.create(request.getProductId(), AssistantType.AUTO_MODIFY);

        AssistantMessage memberMessage = AssistantMessage.builder()
            .assistantId(assistantId)
            .role(MessageSenderRole.MEMBER)
            .content(request.getContent())
            .createdBy(MemberUtil.getMemberId())
            .build();
        assistantService.createMessage(memberMessage);

        UserSetting userSetting = new UserSetting(productQueryService.getById(request.getProductId()));
        AutoModifyRequest autoModifyRequest = new AutoModifyRequest(
            "t" + request.getProductId().toString().replaceAll("-", ""),
            userSetting,
            memberMessage.getContent()
        );

        String answer = assistantApiClient.autoModify(autoModifyRequest).block();

        AssistantMessage assistantMessage = AssistantMessage.builder()
            .assistantId(assistantId)
            .role(MessageSenderRole.ASSISTANT)
            .content(answer)
            .createdBy(MemberUtil.getMemberId())
            .build();
        assistantService.createMessage(assistantMessage);

        assistantService.modifyStatus(assistantId, AssistantStatus.IN_PROGRESS);

        return new AssistantResponse(assistantId, answer);
    }

    @Transactional
    public MessageCreateResponse createMessage(AssistantAutoModifyRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UUID assistantId = assistantService.create(request.getProductId(), AssistantType.AUTO_MODIFY);

        AssistantMessage memberMessage = AssistantMessage.builder()
            .assistantId(assistantId)
            .role(MessageSenderRole.MEMBER)
            .content(request.getContent())
            .createdBy(MemberUtil.getMemberId())
            .build();
        assistantService.createMessage(memberMessage);

        return new MessageCreateResponse(assistantId);
    }

    public SseEmitter streamAutoModify(UUID assistantId) {
        Assistant assistant = assistantService.getById(assistantId, MemberUtil.getMemberId());

        assistantService.verifyAnswered(assistantId);
        productQueryService.verifyExist(assistant.getProductId());

        AssistantMessage memberMessage = assistantService.getMessageByAssistantId(assistantId, MessageSenderRole.MEMBER);

        UserSetting userSetting = new UserSetting(productQueryService.getById(assistant.getProductId()));
        AutoModifyRequest request = new AutoModifyRequest(
            "t" + assistant.getProductId().toString().replaceAll("-", ""),
            userSetting,
            memberMessage.getContent()
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
                    emitter.completeWithError(new BaseException(AssistantException.WEBCLIENT_REQUEST_ERROR));
                },
                () -> {
                    String answer = responseBuilder.toString().replace("[DONE]", "").trim();
                    AssistantMessage assistantMessage = AssistantMessage.builder()
                        .assistantId(assistantId)
                        .role(MessageSenderRole.ASSISTANT)
                        .content(answer)
                        .createdBy(assistant.getCreatedBy())
                        .build();
                    assistantService.createMessage(assistantMessage);

                    assistantService.modifyStatus(assistantId, AssistantStatus.IN_PROGRESS);
                    emitter.complete();
                }
            );

        return emitter;
    }
}
