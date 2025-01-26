package com.writely.product.repository;

import com.writely.product.domain.ProductSynopsis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductSynopsisJpaRepository extends JpaRepository<ProductSynopsis, UUID> {
}
