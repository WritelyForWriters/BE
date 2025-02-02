package com.writely.product.repository;

import com.writely.product.domain.ProductPlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductPlotJpaRepository extends JpaRepository<ProductPlot, UUID> {
}
