package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import writeon.api.assistant.request.AssistantResearchRequest;
import writeon.api.assistant.response.AssistantResponse;
import writeon.api.product.service.ProductQueryService;
import writeon.assistantapiclient.AssistantApiClient;
import writeon.assistantapiclient.request.ResearchRequest;
import writeon.assistantapiclient.request.UserSetting;
import writeon.domain.assistant.enums.MessageSenderRole;
import writeon.domain.assistant.research.ResearchMessage;
import writeon.domain.assistant.research.ResearchMessageJpaRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResearchService {

    private final long TIMEOUT = 180_000L;

    private final ResearchMessageJpaRepository researchMessageRepository;
    private final ProductQueryService productQueryService;
    private final AssistantApiClient assistantApiClient;

    @Transactional
    public AssistantResponse research(AssistantResearchRequest request) {
        productQueryService.verifyExist(request.getProductId());

        UUID assistantId = UUID.randomUUID();
        ResearchMessage memberMessage =
            new ResearchMessage(request.getProductId(), assistantId, MessageSenderRole.MEMBER, request.getContent(), request.getPrompt());
        researchMessageRepository.save(memberMessage);

        UserSetting userSetting = new UserSetting(productQueryService.getById(request.getProductId()));
        ResearchRequest researchRequest = new ResearchRequest("1", userSetting, request.getContent());

        String answer = assistantApiClient.research(researchRequest).block();

        ResearchMessage assistantMessage =
            new ResearchMessage(request.getProductId(), assistantId, MessageSenderRole.ASSISTANT, answer, null);
        researchMessageRepository.save(assistantMessage);

        return new AssistantResponse(assistantId, answer);
    }
}
