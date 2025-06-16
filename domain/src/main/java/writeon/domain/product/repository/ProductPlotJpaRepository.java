package writeon.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import writeon.domain.product.ProductPlot;

import java.util.UUID;

public interface ProductPlotJpaRepository extends JpaRepository<ProductPlot, UUID> {
}
