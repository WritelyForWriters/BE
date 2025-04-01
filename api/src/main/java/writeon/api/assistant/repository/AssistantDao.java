package writeon.api.assistant.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;
import writeon.api.assistant.response.AssistantHistoryResponse;
import writeon.domain.assistant.enums.AssistantStatus;
import writeon.tables.records.AssistantMessageRecord;
import writeon.tables.records.AssistantRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static writeon.tables.Assistant.ASSISTANT;
import static writeon.tables.AssistantMessage.ASSISTANT_MESSAGE;

@Repository
@RequiredArgsConstructor
public class AssistantDao {

    private final DSLContext dsl;

    public List<AssistantHistoryResponse> selectHistories(UUID productId) {
        Map<UUID, Result<Record>> groupedResults = dsl
            .select(ASSISTANT.asterisk(), ASSISTANT_MESSAGE.asterisk())
            .from(ASSISTANT)
            .join(ASSISTANT_MESSAGE)
            .on(ASSISTANT.ID.eq(ASSISTANT_MESSAGE.ASSISTANT_ID))
            .where(ASSISTANT.PRODUCT_ID.eq(productId))
            .and(ASSISTANT.STATUS.ne(AssistantStatus.DRAFT.getCode()))
            .and(ASSISTANT.CREATED_AT.between(LocalDateTime.now().minusMonths(6), LocalDateTime.now()))
            .orderBy(ASSISTANT.CREATED_AT.desc())
            .fetchGroups(ASSISTANT.ID);

    return groupedResults.values().stream()
        .map(records -> {
            AssistantRecord assistant = records.getFirst().into(AssistantRecord.class);
            AssistantMessageRecord memberMessage = records.get(0).into(AssistantMessageRecord.class);
            AssistantMessageRecord assistantMessage = records.get(1).into(AssistantMessageRecord.class);
             return new AssistantHistoryResponse(assistant, memberMessage, assistantMessage);
        })
        .toList();
    }
}
