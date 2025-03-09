package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import writeon.api.assistant.request.AssistantEvaluationRequest;
import writeon.api.common.exception.BaseException;
import writeon.domain.assistant.AssistantEvaluation;
import writeon.domain.assistant.AssistantEvaluationJpaRepository;
import writeon.domain.assistant.enums.AssistantException;
import writeon.domain.assistant.enums.FeedbackType;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssistantEvaluationService {

    private final AssistantEvaluationJpaRepository assistantEvaluationRepository;
    private final AssistantService assistantService;

    @Transactional
    public void evaluate(AssistantEvaluationRequest request) {
        assistantService.verifyExist(request.getAssistantId());
        verifyEvaluated(request.getAssistantId());

        String feedback = null;

        if (!request.getIsGood()) {
            feedback = request.getFeedbackType() == FeedbackType.ETC
                ? request.getFeedback() : request.getFeedbackType().getCode();
        }

        AssistantEvaluation assistantEvaluation = AssistantEvaluation.builder()
            .assistantId(request.getAssistantId())
            .isGood(request.getIsGood())
            .feedback(feedback)
            .build();

        assistantEvaluationRepository.save(assistantEvaluation);
    }

    private void verifyEvaluated(UUID assistantId) {
        if (assistantEvaluationRepository.existsById(assistantId)) {
            throw new BaseException(AssistantException.ALREADY_EVALUATED);
        }
    }
}
