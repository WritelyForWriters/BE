package com.writely.product.repository;

import com.writely.product.domain.ProductIdeaNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductIdeaNoteJpaRepository extends JpaRepository<ProductIdeaNote, UUID> {
}
