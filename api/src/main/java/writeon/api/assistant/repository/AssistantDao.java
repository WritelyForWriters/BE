package writeon.api.assistant.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import writeon.api.assistant.response.AssistantHistoryResponse;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.domain.assistant.enums.AssistantType;
import writeon.domain.assistant.enums.MessageSenderRole;
import writeon.tables.AssistantMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static writeon.Tables.ASSISTANT;
import static writeon.Tables.ASSISTANT_MESSAGE;

@Repository
@RequiredArgsConstructor
public class AssistantDao {

    private final DSLContext dsl;

    public List<AssistantHistoryResponse> selectHistories(UUID productId, UUID assistantId, int size) {
        AssistantMessage memberMessage = ASSISTANT_MESSAGE.as("member_message");
        AssistantMessage assistantMessage = ASSISTANT_MESSAGE.as("assistant_message");

        List<Condition> conditions = getConditions(productId);
        if (assistantId != null) {
            conditions.add(ASSISTANT.ID.lt(assistantId));
        }

        return dsl
            .select(ASSISTANT.ID, ASSISTANT.TYPE, ASSISTANT.IS_APPLIED, ASSISTANT.CREATED_AT,
                memberMessage.CONTENT, memberMessage.PROMPT, assistantMessage.CONTENT)
            .from(ASSISTANT)
            .join(memberMessage)
            .on(ASSISTANT.ID.eq(memberMessage.ASSISTANT_ID)
                .and(memberMessage.ROLE.eq(MessageSenderRole.MEMBER.getCode())))
            .join(assistantMessage)
            .on(ASSISTANT.ID.eq(assistantMessage.ASSISTANT_ID)
                .and(assistantMessage.ROLE.eq(MessageSenderRole.ASSISTANT.getCode())))
            .where(conditions)
            .orderBy(ASSISTANT.ID.desc())
            .limit(size)
            .fetchInto(AssistantHistoryResponse.class);
    }

    public Long countHistories(UUID productId) {
        return dsl
            .selectCount()
            .from(ASSISTANT)
            .where(getConditions(productId))
            .fetchOneInto(Long.class);
    }

    private List<Condition> getConditions(UUID productId) {
        LocalDateTime now = LocalDateTime.now();

        return new ArrayList<>(List.of(
            ASSISTANT.PRODUCT_ID.eq(productId),
            ASSISTANT.STATUS.ne(AssistantStatus.DRAFT.getCode()),
            ASSISTANT.CREATED_AT.between(now.minusMonths(6), now),
            ASSISTANT.TYPE.in(
                List.of(
                    AssistantType.AUTO_MODIFY.getCode(),
                    AssistantType.USER_MODIFY.getCode(),
                    AssistantType.FEEDBACK.getCode(),
                    AssistantType.CHAT.getCode()
                )
            )
        ));
    }
}
