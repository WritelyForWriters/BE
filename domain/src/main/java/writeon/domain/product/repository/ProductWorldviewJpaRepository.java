package writeon.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.product.ProductWorldview;

import java.util.UUID;

public interface ProductWorldviewJpaRepository extends JpaRepository<ProductWorldview, UUID> {
}
