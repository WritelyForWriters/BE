package com.writely.product.controller;

import com.writely.common.response.BaseResponse;
import com.writely.product.request.ProductCreateRequest;
import com.writely.product.request.ProductMemoCreateRequest;
import com.writely.product.response.ProductResponse;
import com.writely.product.service.ProductCommandService;
import com.writely.product.service.ProductQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "작품")
public class ProductController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    @Operation(summary = "작품 생성")
    @PostMapping
    public void create(@RequestBody ProductCreateRequest request) {
        productCommandService.create(request);
    }

    @Operation(summary = "메모 생성")
    @PostMapping("/{productId}/memos")
    public void createMemo(@PathVariable UUID productId, @RequestBody ProductMemoCreateRequest request) {
        productCommandService.createMemo(productId, request);
    }

    @Operation(summary = "작품 목록 조회")
    @GetMapping
    public List<ProductResponse> getProducts() {
        return productQueryService.getAll();
    }
}
