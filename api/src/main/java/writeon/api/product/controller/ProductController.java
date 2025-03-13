package writeon.api.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import writeon.api.product.request.ProductMemoSaveRequest;
import writeon.api.product.request.ProductSaveRequest;
import writeon.api.product.request.ProductTemplateSaveRequest;
import writeon.api.product.response.ProductDetailResponse;
import writeon.api.product.response.ProductMemoResponse;
import writeon.api.product.response.ProductResponse;
import writeon.api.product.response.ProductTemplateResponse;
import writeon.api.product.service.ProductCommandService;
import writeon.api.product.service.ProductQueryService;

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
        @RequestBody ProductSaveRequest request) {
        return productCommandService.save(productId, request);
    }

    @Operation(summary = "템플릿 저장")
    @PostMapping("/{productId}/templates")
    public void createTemplate(
        @PathVariable UUID productId,
        @RequestBody ProductTemplateSaveRequest request) {
        productCommandService.saveTemplate(productId, request);
    }

    @Operation(summary = "메모 생성")
    @PostMapping("/{productId}/memos")
    public void createMemo(
        @PathVariable UUID productId,
        @RequestBody ProductMemoSaveRequest request) {
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
        @RequestBody ProductMemoSaveRequest request) {
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
