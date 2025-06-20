package writeon.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import writeon.domain.product.ProductCustomField;

import java.util.UUID;

public interface ProductCustomFieldJpaRepository extends JpaRepository<ProductCustomField, UUID> {

    @Modifying
    @Query(value = "DELETE FROM product_custom_field WHERE product_id = :productId AND section_type = :sectionType", nativeQuery = true)
    void deleteAllByProductIdAndSectionType(@Param("productId") UUID productId, @Param("sectionType") String sectionType);
}
