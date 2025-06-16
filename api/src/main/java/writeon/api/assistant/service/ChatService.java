package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import writeon.api.assistant.request.AssistantChatRequest;
import writeon.api.assistant.request.AssistantResearchRequest;
import writeon.api.assistant.response.AssistantResponse;
import writeon.api.assistant.response.MessageCreateResponse;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.LogUtil;
import writeon.api.common.util.MemberUtil;
import writeon.api.product.service.ProductQueryService;
import writeon.assistantapiclient.AssistantApiClient;
import writeon.assistantapiclient.request.ChatRequest;
import writeon.assistantapiclient.request.ResearchRequest;
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
public class ChatService {

    private final long TIMEOUT = 180_000L;

    private final AssistantApiClient assistantApiClient;
    private final AssistantService assistantService;
    private final ProductQueryService productQueryService;

    @Transactional
    public AssistantResponse chat(AssistantChatRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UUID assistantId = assistantService.create(request.getProductId(), AssistantType.CHAT);

        AssistantMessage memberMessage = AssistantMessage.builder()
            .assistantId(assistantId)
            .role(MessageSenderRole.MEMBER)
            .content(request.getContent())
            .prompt(request.getPrompt())
            .createdBy(MemberUtil.getMemberId())
            .build();
        assistantService.createMessage(memberMessage);

        UserSetting userSetting;
        if (request.getShouldApplySetting()) {
            userSetting = new UserSetting(productQueryService.getById(request.getProductId()));
        } else {
            userSetting = new UserSetting();
        }

        ChatRequest chatRequest = new ChatRequest(
            userSetting,
            memberMessage.getContent(),
            memberMessage.getPrompt(),
            request.getSessionId()
        );

        String answer = assistantApiClient.chat(chatRequest).block();

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
    public MessageCreateResponse createMessage(AssistantChatRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UUID assistantId = assistantService.create(request.getProductId(), AssistantType.CHAT);

        AssistantMessage memberMessage = AssistantMessage.builder()
            .assistantId(assistantId)
            .role(MessageSenderRole.MEMBER)
            .content(request.getContent())
            .prompt(request.getPrompt())
            .createdBy(MemberUtil.getMemberId())
            .build();
        assistantService.createMessage(memberMessage);

        return new MessageCreateResponse(assistantId);
    }

    public SseEmitter streamChat(UUID assistantId, String sessionId, Boolean shouldApplySetting) {
        Assistant assistant = assistantService.getById(assistantId, MemberUtil.getMemberId());

        assistantService.verifyAnswered(assistantId);
        productQueryService.verifyExist(assistant.getProductId());

        AssistantMessage memberMessage = assistantService.getMessageByAssistantId(assistantId, MessageSenderRole.MEMBER);

        UserSetting userSetting;
        if (shouldApplySetting) {
            userSetting = new UserSetting(productQueryService.getById(assistant.getProductId()));
        } else {
            userSetting = new UserSetting();
        }

        ChatRequest request = new ChatRequest(
            userSetting,
            memberMessage.getContent(),
            memberMessage.getPrompt(),
            sessionId
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
                    LogUtil.error(error.getMessage());
                    emitter.completeWithError(new BaseException(AssistantException.WEBCLIENT_REQUEST_ERROR));
                },
                () -> {
                    String answer = responseBuilder.toString().replace("[DONE]", "").trim();
                    AssistantMessage assistantMessage = AssistantMessage.builder()
                        .assistantId(assistantId)
                        .role(MessageSenderRole.ASSISTANT)
                        .content(answer)
                        .createdBy(memberMessage.getCreatedBy())
                        .build();
                    assistantService.createMessage(assistantMessage);

                    assistantService.modifyStatus(assistantId, AssistantStatus.IN_PROGRESS);
                    emitter.complete();
                }
            );

        return emitter;
    }

    @Transactional
    public AssistantResponse research(AssistantResearchRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UUID assistantId = assistantService.create(request.getProductId(), AssistantType.CHAT);

        AssistantMessage memberMessage = AssistantMessage.builder()
            .assistantId(assistantId)
            .role(MessageSenderRole.MEMBER)
            .content(request.getContent())
            .prompt(request.getPrompt())
            .createdBy(MemberUtil.getMemberId())
            .build();
        assistantService.createMessage(memberMessage);

        UserSetting userSetting;
        if (request.getShouldApplySetting()) {
            userSetting = new UserSetting(productQueryService.getById(request.getProductId()));
        } else {
            userSetting = new UserSetting();
        }

        ResearchRequest researchRequest = new ResearchRequest(userSetting, request.getContent(), request.getPrompt(), request.getSessionId());

        // 웹 검색 요청
        String answer = assistantApiClient.research(researchRequest).block();

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
}
