package com.writely.product.controller;

import com.writely.product.request.ProductMemoCreateRequest;
import com.writely.product.request.ProductModifyRequest;
import com.writely.product.request.ProductTemplateCreateRequest;
import com.writely.product.response.ProductDetailResponse;
import com.writely.product.response.ProductMemoResponse;
import com.writely.product.response.ProductResponse;
import com.writely.product.response.ProductTemplateResponse;
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
    public UUID create() {
        return productCommandService.create();
    }

    @Operation(summary = "작품 저장")
    @PostMapping("/{productId}")
    public UUID modify(
        @PathVariable UUID productId,
        @RequestBody ProductModifyRequest request) {
        return productCommandService.modify(productId, request);
    }

    @Operation(summary = "템플릿 저장")
    @PostMapping("/{productId}/templates")
    public void createTemplate(
        @PathVariable UUID productId,
        @RequestBody ProductTemplateCreateRequest request) {
        productCommandService.saveTemplate(productId, request);
    }

    @Operation(summary = "메모 생성")
    @PostMapping("/{productId}/memos")
    public void createMemo(
        @PathVariable UUID productId,
        @RequestBody ProductMemoCreateRequest request) {
        productCommandService.createMemo(productId, request);
    }

    @Operation(summary = "작품 목록 조회")
    @GetMapping
    public List<ProductResponse> getList() {
        return productQueryService.getList();
    }

    @Operation(summary = "작품 상세 조회")
    @GetMapping("/{productId}")
    public ProductDetailResponse getDetail(@PathVariable UUID productId) {
        return productQueryService.getDetail(productId);
    }

    @Operation(summary = "템플릿 조회")
    @GetMapping("/{productId}/templates")
    public ProductTemplateResponse getTemplate(@PathVariable UUID productId) {
        return productQueryService.getTemplate(productId);
    }

    @Operation(summary = "메모 목록 조회")
    @GetMapping("/{productId}/memos")
    public List<ProductMemoResponse> getMemos(@PathVariable UUID productId) {
        return productQueryService.getMemos(productId);
    }

    @Operation(summary = "메모 수정")
    @PutMapping("/{productId}/memos/{memoId}")
    public void modifyMemo(
        @PathVariable UUID productId,
        @PathVariable UUID memoId,
        @RequestBody ProductMemoCreateRequest request) {
        productCommandService.modifyMemo(productId, memoId, request);
    }

    @Operation(summary = "메모 삭제")
    @DeleteMapping("/{productId}/memos/{memoId}")
    public void deleteMemo(
        @PathVariable UUID productId,
        @PathVariable UUID memoId) {
        productCommandService.deleteMemo(productId, memoId);
    }
}
