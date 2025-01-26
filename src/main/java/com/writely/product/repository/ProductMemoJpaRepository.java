package com.writely.product.repository;

import com.writely.product.domain.ProductMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductMemoJpaRepository extends JpaRepository<ProductMemo, UUID> {
}
