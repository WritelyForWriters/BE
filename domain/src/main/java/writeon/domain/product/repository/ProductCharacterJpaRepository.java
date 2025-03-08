package writeon.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.product.ProductCharacter;

import java.util.UUID;

public interface ProductCharacterJpaRepository extends JpaRepository<ProductCharacter, UUID> {
}
