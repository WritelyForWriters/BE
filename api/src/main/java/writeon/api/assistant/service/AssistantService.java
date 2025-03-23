package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import writeon.api.assistant.request.AssistantCompletedRequest;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.MemberUtil;
import writeon.domain.assistant.Assistant;
import writeon.domain.assistant.AssistantJpaRepository;
import writeon.domain.assistant.enums.AssistantException;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.domain.assistant.enums.AssistantType;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssistantService {

    private final AssistantJpaRepository assistantRepository;

    @Transactional
    public UUID create(UUID productId, AssistantType type) {
        Assistant assistant = new Assistant(productId, type);
        return assistantRepository.save(assistant).getId();
    }

    @Transactional
    public void completed(UUID assistantId, AssistantCompletedRequest request) {
        Assistant assistant = getById(assistantId);

        if (assistant.getStatus() != AssistantStatus.IN_PROGRESS) {
            throw new BaseException(AssistantException.CANNOT_BE_COMPLETED);
        }

        assistant.updateStatus(AssistantStatus.COMPLETED);
    }

    @Transactional
    public void modifyStatus(UUID assistantId, AssistantStatus status) {
        Assistant assistant = getById(assistantId);

        assistant.updateStatus(status);
    }

    @Transactional(readOnly = true)
    public Assistant getById(UUID assistantId) {
        return assistantRepository.findByIdAndCreatedBy(assistantId, MemberUtil.getMemberId())
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST));
    }

    @Transactional(readOnly = true)
    public void verifyExist(UUID assistantId) {
        if (!assistantRepository.existsByIdAndCreatedBy(assistantId, MemberUtil.getMemberId())) {
            throw new BaseException(AssistantException.NOT_EXIST);
        }
    }
}
