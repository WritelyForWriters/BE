package com.writely.product.repository;

import com.writely.product.domain.ProductWorldview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductWorldviewRepository extends JpaRepository<ProductWorldview, UUID> {
}
