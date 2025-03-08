package writeon.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.product.ProductMemo;

import java.util.UUID;

public interface ProductMemoJpaRepository extends JpaRepository<ProductMemo, UUID> {
}
