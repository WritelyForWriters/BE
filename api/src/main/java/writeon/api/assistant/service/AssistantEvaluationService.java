package writeon.api.assistant.service;

import com.amplitude.Amplitude;
import com.amplitude.Event;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import writeon.api.assistant.request.AssistantEvaluateRequest;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.MemberUtil;
import writeon.domain.assistant.AssistantEvaluation;
import writeon.domain.assistant.AssistantEvaluationJpaRepository;
import writeon.domain.assistant.enums.AssistantException;
import writeon.domain.assistant.enums.FeedbackType;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssistantEvaluationService {

    private final AssistantEvaluationJpaRepository assistantEvaluationRepository;
    private final AssistantService assistantService;
    private final Amplitude amplitude = Amplitude.getInstance();

    @Transactional
    public void evaluate(UUID assistantId, AssistantEvaluateRequest request) {
        assistantService.verifyExist(assistantId);
        verifyEvaluated(assistantId);

        String feedback = null;

        if (!request.getIsGood()) {
            feedback = request.getFeedbackType() == FeedbackType.ETC
                ? request.getFeedback() : null;
        }

        AssistantEvaluation assistantEvaluation = AssistantEvaluation.builder()
            .assistantId(assistantId)
            .isGood(request.getIsGood())
            .feedbackType(request.getFeedbackType())
            .feedback(feedback)
            .build();

        assistantEvaluationRepository.save(assistantEvaluation);

        final UUID memberId = MemberUtil.getMemberId();
        final List<AssistantEvaluation> evaluations = assistantEvaluationRepository.getByCreatedBy(memberId);
        final float totalCount = evaluations.size();
        final float goodCount = evaluations.stream().filter(AssistantEvaluation::getIsGood).toList().size();
        final float percentage = Math.round(goodCount / totalCount * 100 * 10) / 10f;
        // amplitude 이벤트 송신
        Event event = new Event("$identify", memberId.toString());
        event.userProperties = new JSONObject().put("ai_feedback_satisfaction", percentage);
        amplitude.logEvent(event);
    }

    private void verifyEvaluated(UUID assistantId) {
        if (assistantEvaluationRepository.existsById(assistantId)) {
            throw new BaseException(AssistantException.ALREADY_EVALUATED);
        }
    }
}
