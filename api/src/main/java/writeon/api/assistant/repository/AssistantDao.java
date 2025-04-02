package writeon.api.assistant.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import writeon.api.assistant.response.AssistantHistoryResponse;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.domain.assistant.enums.MessageSenderRole;
import writeon.tables.AssistantMessage;

import static writeon.Tables.ASSISTANT;
import static writeon.Tables.ASSISTANT_MESSAGE;

@Repository
@RequiredArgsConstructor
public class AssistantDao {

    private final DSLContext dsl;

    public List<AssistantHistoryResponse> selectHistories(UUID productId, int page, int size) {
        AssistantMessage memberMessage = ASSISTANT_MESSAGE.as("member_message");
        AssistantMessage assistantMessage = ASSISTANT_MESSAGE.as("assistant_message");

        return dsl.select(ASSISTANT.ID, ASSISTANT.TYPE, ASSISTANT.IS_APPLIED, ASSISTANT.CREATED_AT,
                  memberMessage.CONTENT, memberMessage.PROMPT, assistantMessage.CONTENT)
            .from(ASSISTANT)
            .join(memberMessage)
            .on(ASSISTANT.ID.eq(memberMessage.ASSISTANT_ID)
                .and(memberMessage.ROLE.eq(MessageSenderRole.MEMBER.getCode())))
            .join(assistantMessage)
            .on(ASSISTANT.ID.eq(assistantMessage.ASSISTANT_ID)
                .and(assistantMessage.ROLE.eq(MessageSenderRole.ASSISTANT.getCode())))
            .where(ASSISTANT.PRODUCT_ID.eq(productId))
            .and(ASSISTANT.STATUS.ne(AssistantStatus.DRAFT.getCode()))
            .and(ASSISTANT.CREATED_AT.between(LocalDateTime.now().minusMonths(6), LocalDateTime.now()))
            .orderBy(ASSISTANT.CREATED_AT.desc())
            .offset((long) (page - 1) * size)
            .limit(size)
            .fetchInto(AssistantHistoryResponse.class);
    }

    public Long countHistories(UUID productId) {
        return dsl.selectCount()
            .from(ASSISTANT)
            .where(ASSISTANT.PRODUCT_ID.eq(productId))
            .and(ASSISTANT.STATUS.ne(AssistantStatus.DRAFT.getCode()))
            .and(ASSISTANT.CREATED_AT.between(LocalDateTime.now().minusMonths(6), LocalDateTime.now()))
            .fetchOneInto(Long.class);
    }
}
