package com.writely.product.repository;

import com.writely.product.domain.ProductIdeaNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductIdeaNoteRepository extends JpaRepository<ProductIdeaNote, UUID> {
}
