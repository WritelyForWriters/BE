package com.writely.product.service;

import com.writely.common.exception.BaseException;
import com.writely.product.domain.Product;
import com.writely.product.domain.enums.ProductException;
import com.writely.product.repository.ProductRepository;
import com.writely.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream().map(ProductResponse::new).toList();
    }

    public Product getById(UUID id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new BaseException(ProductException.NOT_EXIST));
    }
}
