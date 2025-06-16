package writeon.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.product.ProductIdeaNote;

import java.util.UUID;

public interface ProductIdeaNoteJpaRepository extends JpaRepository<ProductIdeaNote, UUID> {
}
