package writeon.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.product.ProductSynopsis;

import java.util.UUID;

public interface ProductSynopsisJpaRepository extends JpaRepository<ProductSynopsis, UUID> {
}
