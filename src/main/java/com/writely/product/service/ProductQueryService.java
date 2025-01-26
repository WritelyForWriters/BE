package com.writely.product.service;

import com.writely.common.exception.BaseException;
import com.writely.product.domain.Product;
import com.writely.product.domain.enums.ProductException;
import com.writely.product.repository.ProductDao;
import com.writely.product.repository.ProductJpaRepository;
import com.writely.product.response.ProductDetailResponse;
import com.writely.product.response.ProductMemoResponse;
import com.writely.product.response.ProductResponse;
import com.writely.product.response.ProductTemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductDao productDao;
    private final ProductJpaRepository productRepository;

    public ProductDetailResponse getDetail(UUID productId) {
        return new ProductDetailResponse(getById(productId));
    }

    public List<ProductResponse> getList() {
        return productDao.select();
    }

    public Product getById(UUID productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new BaseException(ProductException.NOT_EXIST));
    }

    public ProductTemplateResponse getTemplate(UUID productId) {
        return new ProductTemplateResponse(getById(productId));
    }

    public List<ProductMemoResponse> getMemos(UUID productId) {
        Product product = getById(productId);

        return product.getMemos().stream()
            .map(ProductMemoResponse::new)
            .toList();
    }
}
