package com.writely.product.repository;

import com.writely.product.domain.ProductCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductCharacterJpaRepository extends JpaRepository<ProductCharacter, UUID> {
}
