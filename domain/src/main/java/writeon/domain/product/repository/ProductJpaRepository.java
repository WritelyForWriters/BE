package writeon.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.product.Product;

import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {
}
