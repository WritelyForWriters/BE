package writeon.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.product.ProductFavoritePrompt;

import java.util.UUID;

public interface ProductFavoritePromptJpaRepository extends JpaRepository<ProductFavoritePrompt, UUID> {

    boolean existsByProduct_IdAndMessageId(UUID productId, UUID messageId);
}
