package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import writeon.api.common.exception.BaseException;
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
    public void reflect(UUID assistantId) {
        Assistant assistant = getById(assistantId);
        assistant.updateStatus(AssistantStatus.COMPLETED);
    }

    @Transactional(readOnly = true)
    public Assistant getById(UUID assistantId) {
        return assistantRepository.findById(assistantId)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST));
    }

    @Transactional(readOnly = true)
    public void verifyExist(UUID assistantId) {
        if (!assistantRepository.existsById(assistantId)) {
            throw new BaseException(AssistantException.NOT_EXIST);
        }
    }
}
