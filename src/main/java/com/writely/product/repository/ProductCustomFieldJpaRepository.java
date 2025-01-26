package com.writely.product.repository;

import com.writely.product.domain.ProductCustomField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductCustomFieldJpaRepository extends JpaRepository<ProductCustomField, UUID> {
}
