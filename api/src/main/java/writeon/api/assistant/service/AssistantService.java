package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import writeon.api.assistant.repository.AssistantDao;
import writeon.api.assistant.request.AssistantHistoryListRequest;
import writeon.api.assistant.response.AssistantHistoryResponse;
import writeon.api.common.exception.BaseException;
import writeon.api.common.response.CursorResponse;
import writeon.api.common.util.MemberUtil;
import writeon.api.product.service.ProductQueryService;
import writeon.domain.assistant.Assistant;
import writeon.domain.assistant.AssistantJpaRepository;
import writeon.domain.assistant.AssistantMessage;
import writeon.domain.assistant.AssistantMessageJpaRepository;
import writeon.domain.assistant.enums.AssistantException;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.domain.assistant.enums.AssistantType;
import writeon.domain.assistant.enums.MessageSenderRole;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssistantService {

    private final AssistantJpaRepository assistantRepository;
    private final AssistantMessageJpaRepository assistantMessageRepository;
    private final AssistantDao assistantDao;

    private final ProductQueryService productQueryService;

    @Transactional
    public void apply(UUID assistantId) {
        Assistant assistant = getById(assistantId, MemberUtil.getMemberId());
        if (assistant.getStatus() == AssistantStatus.DRAFT || assistant.getIsApplied()) {
            throw new BaseException(AssistantException.CANNOT_BE_APPLIED);
        }

        assistant.apply();
    }

    @Transactional
    public UUID create(UUID productId, AssistantType type) {
        Assistant assistant = new Assistant(productId, type, MemberUtil.getMemberId());
        return assistantRepository.save(assistant).getId();
    }

    @Transactional
    public UUID createMessage(AssistantMessage message) {
        return assistantMessageRepository.save(message).getId();
    }

    @Transactional
    public void completed(UUID assistantId) {
        Assistant assistant = getById(assistantId, MemberUtil.getMemberId());
        if (assistant.getStatus() != AssistantStatus.IN_PROGRESS) {
            throw new BaseException(AssistantException.CANNOT_BE_COMPLETED);
        }

        assistant.updateStatus(AssistantStatus.COMPLETED);
    }

    @Transactional
    public void modifyStatus(UUID assistantId, AssistantStatus status) {
        assistantRepository.updateStatus(assistantId, status);
    }

    @Transactional(readOnly = true)
    public Assistant getById(UUID assistantId) {
        return assistantRepository.findById(assistantId)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST));
    }

    @Transactional(readOnly = true)
    public Assistant getById(UUID assistantId, UUID memberId) {
        return assistantRepository.findByIdAndCreatedBy(assistantId, memberId)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST));
    }

    @Transactional(readOnly = true)
    public CursorResponse<AssistantHistoryResponse> getHistories(AssistantHistoryListRequest request) {
        productQueryService.verifyExist(request.getProductId());

        long count = assistantDao.countHistories(request.getProductId());
        return CursorResponse.of(
            assistantDao.selectHistories(request.getProductId(), request.getAssistantId(), request.getSize()),
            count
        );
    }

    @Transactional(readOnly = true)
    public AssistantMessage getMessageByAssistantId(UUID assistantId, MessageSenderRole role) {
        return assistantMessageRepository.findByAssistantIdAndMessageContent_Role(assistantId, role)
            .orElseThrow(() -> new BaseException(AssistantException.NOT_EXIST_MESSAGE));
    }

    @Transactional(readOnly = true)
    public void verifyAnswered(UUID assistantId) {
        if (assistantMessageRepository.existsByAssistantIdAndMessageContent_Role(assistantId, MessageSenderRole.ASSISTANT)) {
            throw new BaseException(AssistantException.ALREADY_ANSWERED);
        }
    }

    @Transactional(readOnly = true)
    public void verifyExist(UUID assistantId) {
        if (!assistantRepository.existsByIdAndCreatedBy(assistantId, MemberUtil.getMemberId())) {
            throw new BaseException(AssistantException.NOT_EXIST);
        }
    }
}
