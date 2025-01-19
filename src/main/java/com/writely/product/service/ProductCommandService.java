package com.writely.product.service;

import com.writely.common.exception.BaseException;
import com.writely.product.domain.Product;
import com.writely.product.domain.ProductMemo;
import com.writely.product.domain.enums.ProductException;
import com.writely.product.repository.ProductMemoRepository;
import com.writely.product.repository.ProductRepository;
import com.writely.product.request.ProductCreateRequest;
import com.writely.product.request.ProductMemoCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

    private final ProductQueryService productQueryService;
    private final ProductRepository productRepository;
    private final ProductMemoRepository productMemoRepository;

    @Transactional
    public void create(ProductCreateRequest request) {
        Product product = new Product(request.getName());

        productRepository.save(product);
    }

    @Transactional
    public void createMemo(UUID productId, ProductMemoCreateRequest request) {
        verifyExistProduct(productId);

        productMemoRepository.save(new ProductMemo(productId, request.getName()));
    }

    private void verifyExistProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new BaseException(ProductException.NOT_EXIST);
        }
    }
}
