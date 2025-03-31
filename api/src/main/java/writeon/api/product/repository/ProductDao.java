package writeon.api.product.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import writeon.api.common.util.MemberUtil;
import writeon.api.product.response.ProductResponse;

import static writeon.tables.Product.PRODUCT;
import static writeon.tables.ProductSynopsis.PRODUCT_SYNOPSIS;


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
}
