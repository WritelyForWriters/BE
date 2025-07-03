package writeon.api.product.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import writeon.api.common.util.MemberUtil;
import writeon.api.product.response.ProductFavoritePromptResponse;
import writeon.api.product.response.ProductResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static writeon.tables.AssistantMessage.ASSISTANT_MESSAGE;
import static writeon.tables.Product.PRODUCT;
import static writeon.tables.ProductSynopsis.PRODUCT_SYNOPSIS;
import static writeon.tables.ProductFavoritePrompt.PRODUCT_FAVORITE_PROMPT;


@Repository
@RequiredArgsConstructor
public class ProductDao {

    private final DSLContext dsl;

    public List<ProductResponse> select() {
        return dsl
            .select(PRODUCT, PRODUCT_SYNOPSIS.GENRE)
            .from(PRODUCT)
            .leftJoin(PRODUCT_SYNOPSIS)
            .on(PRODUCT.ID.eq(PRODUCT_SYNOPSIS.ID))
            .where(PRODUCT.CREATED_BY.eq(MemberUtil.getMemberId()))
            .orderBy(PRODUCT.UPDATED_AT.desc())
            .fetchInto(ProductResponse.class);
    }

    public List<ProductFavoritePromptResponse> selectFavoritePrompts(UUID productId) {
        return dsl
            .select(PRODUCT_FAVORITE_PROMPT.MESSAGE_ID, ASSISTANT_MESSAGE.PROMPT, PRODUCT_FAVORITE_PROMPT.CREATED_AT)
            .from(PRODUCT)
            .join(PRODUCT_FAVORITE_PROMPT)
            .on(PRODUCT.ID.eq(PRODUCT_FAVORITE_PROMPT.PRODUCT_ID))
            .join(ASSISTANT_MESSAGE)
            .on(PRODUCT_FAVORITE_PROMPT.MESSAGE_ID.eq(ASSISTANT_MESSAGE.ID))
            .where(PRODUCT.ID.eq(productId))
            .orderBy(PRODUCT_FAVORITE_PROMPT.CREATED_AT.desc())
            .fetchInto(ProductFavoritePromptResponse.class);
    }

    public boolean hasUpdatedToday(UUID memberId) {
        LocalDate today = LocalDate.now();
        return dsl.selectOne()
                .from(PRODUCT)
                .where(PRODUCT.UPDATED_BY.eq(memberId))
                .and(PRODUCT.UPDATED_AT.ge(today.atStartOfDay()))
                .and(PRODUCT.UPDATED_AT.lt(today.plusDays(1).atStartOfDay()))
                .fetchOptional()
                .isPresent();
    }
}
