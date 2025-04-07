package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import writeon.api.assistant.request.AssistantPlannerMessageRequest;
import writeon.api.assistant.response.MessageCreateResponse;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.LogUtil;
import writeon.api.common.util.MemberUtil;
import writeon.api.product.service.ProductQueryService;
import writeon.assistantapiclient.AssistantApiClient;
import writeon.assistantapiclient.request.PlannerGenerateRequest;
import writeon.domain.assistant.Assistant;
import writeon.domain.assistant.AssistantMessage;
import writeon.domain.assistant.AssistantPlannerMessage;
import writeon.domain.assistant.AssistantPlannerMessageJpaRepository;
import writeon.domain.assistant.enums.AssistantException;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.domain.assistant.enums.AssistantType;
import writeon.domain.assistant.enums.MessageSenderRole;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final long TIMEOUT = 180_000L;

    private final AssistantApiClient assistantApiClient;
    private final AssistantService assistantService;
    private final ProductQueryService productQueryService;
    private final AssistantPlannerMessageJpaRepository assistantPlannerMessageRepository;

    @Transactional
    public MessageCreateResponse createMessage(AssistantPlannerMessageRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UUID assistantId = assistantService.create(request.getProductId(), AssistantType.PLANNER);

        AssistantMessage memberMessage = AssistantMessage.builder()
            .assistantId(assistantId)
            .prompt(request.getPrompt())
            .role(MessageSenderRole.MEMBER)
            .createdBy(MemberUtil.getMemberId())
            .build();
        assistantService.createMessage(memberMessage);

        AssistantPlannerMessage plannerMessage = AssistantPlannerMessage.builder()
            .assistantId(assistantId)
            .genre(request.getGenre())
            .logline(request.getLogline())
            .section(request.getSection())
            .build();
        assistantPlannerMessageRepository.save(plannerMessage);

        return new MessageCreateResponse(assistantId);
    }

    public SseEmitter streamPlanner(UUID assistantId) {
        Assistant assistant = assistantService.getById(assistantId, MemberUtil.getMemberId());

        if (assistant.getStatus() != AssistantStatus.DRAFT) {
            throw new BaseException(AssistantException.ALREADY_ANSWERED);
        }
        productQueryService.verifyExist(assistant.getProductId());

        AssistantMessage memberMessage = assistantService.getMessage(assistantId, MessageSenderRole.MEMBER);

        AssistantPlannerMessage plannerMessage = assistantPlannerMessageRepository.findById(assistantId)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST_MESSAGE));

        PlannerGenerateRequest request = PlannerGenerateRequest.builder()
            .tenantId(assistant.getProductId().toString())
            .genre(plannerMessage.getGenre())
            .logline(plannerMessage.getLogline())
            .section(plannerMessage.getSection())
            .prompt(memberMessage.getPrompt())
            .build();

        SseEmitter emitter = new SseEmitter(TIMEOUT);
        StringBuilder responseBuilder = new StringBuilder();

        assistantApiClient.streamPlannerGenerate(request)
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
                    String answer = responseBuilder.toString().replace("[DONE]", "").trim();
                    AssistantMessage assistantMessage = AssistantMessage.builder()
                        .assistantId(assistantId)
                        .role(MessageSenderRole.ASSISTANT)
                        .content(answer)
                        .build();
                    assistantService.createMessage(assistantMessage);

                    assistantService.modifyStatus(assistantId, AssistantStatus.IN_PROGRESS);
                    emitter.complete();
                }
            );

        return emitter;
    }
}
