package writeon.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.product.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByIdAndCreatedBy(UUID productId, UUID createdBy);

    boolean existsByIdAndCreatedBy(UUID productId, UUID createdBy);
}
