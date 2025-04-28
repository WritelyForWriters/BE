package writeon.api.assistant.repository;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import writeon.api.assistant.response.AssistantHistoryResponse;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.domain.assistant.enums.AssistantType;
import writeon.domain.assistant.enums.MessageSenderRole;
import writeon.tables.AssistantMessage;

import static writeon.Tables.ASSISTANT;
import static writeon.Tables.ASSISTANT_EVALUATION;
import static writeon.Tables.ASSISTANT_MESSAGE;
import static writeon.Tables.PRODUCT_FAVORITE_PROMPT;

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
            .select(ASSISTANT, memberMessage, PRODUCT_FAVORITE_PROMPT.MESSAGE_ID.isNotNull(), assistantMessage,
                DSL.when(ASSISTANT_EVALUATION.ASSISTANT_ID.isNull(), false).otherwise(ASSISTANT_EVALUATION.IS_GOOD))
            .from(ASSISTANT)
            .join(memberMessage)
            .on(ASSISTANT.ID.eq(memberMessage.ASSISTANT_ID)
                .and(memberMessage.ROLE.eq(MessageSenderRole.MEMBER.getCode())))
            .leftJoin(PRODUCT_FAVORITE_PROMPT)
            .on(memberMessage.ID.eq(PRODUCT_FAVORITE_PROMPT.MESSAGE_ID))
            .join(assistantMessage)
            .on(ASSISTANT.ID.eq(assistantMessage.ASSISTANT_ID)
                .and(assistantMessage.ROLE.eq(MessageSenderRole.ASSISTANT.getCode())))
            .leftJoin(ASSISTANT_EVALUATION)
            .on(ASSISTANT.ID.eq(ASSISTANT_EVALUATION.ASSISTANT_ID))
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
