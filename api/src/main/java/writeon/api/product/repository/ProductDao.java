package writeon.api.product.repository;

import writeon.api.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static writely.tables.Product.PRODUCT;

@Repository
@RequiredArgsConstructor
public class ProductDao {

    private final DSLContext dsl;

    public List<ProductResponse> select() {
        return dsl
            .selectFrom(PRODUCT)
            .orderBy(PRODUCT.UPDATED_AT.desc())
            .fetch()
            .map(ProductResponse::new);
    }
}
