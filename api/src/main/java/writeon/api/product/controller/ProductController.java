package writeon.api.product.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import writeon.api.product.request.ProductMemoSaveRequest;
import writeon.api.product.request.ProductSaveRequest;
import writeon.api.product.request.ProductTemplateSaveRequest;
import writeon.api.product.response.ProductDetailResponse;
import writeon.api.product.response.ProductFavoritePromptResponse;
import writeon.api.product.response.ProductFixedMessageResponse;
import writeon.api.product.response.ProductMemoResponse;
import writeon.api.product.response.ProductResponse;
import writeon.api.product.response.ProductTemplateResponse;
import writeon.api.product.service.ProductCommandService;
import writeon.api.product.service.ProductQueryService;

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

    @Operation(summary = "프롬프트 즐겨찾기 설정")
    @PostMapping("/{productId}/favorite-prompts/{assistantId}")
    public void createFavoritePrompt(
        @PathVariable UUID productId,
        @PathVariable UUID assistantId) {
        productCommandService.createFavoritePrompt(productId, assistantId);
    }

    @Operation(summary = "고정 메세지 설정")
    @PostMapping("/{productId}/fixed-messages/{assistantId}")
    public void createFixedMessage(
        @PathVariable UUID productId,
        @PathVariable UUID assistantId) {
        productCommandService.createFixedMessage(productId, assistantId);
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

    @Operation(summary = "즐겨찾는 프롬프트 목록 조회")
    @GetMapping("/{productId}/favorite-prompts")
    public List<ProductFavoritePromptResponse> getFavoritePrompts(@PathVariable UUID productId) {
        return productQueryService.getFavoritePrompts(productId);
    }

    @Operation(summary = "고정 메세지 조회")
    @GetMapping("/{productId}/fixed-messages")
    public ProductFixedMessageResponse getFixedMessage(@PathVariable UUID productId) {
        return productQueryService.getFixedMessage(productId);
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

    @Operation(summary = "프롬프트 즐겨찾기 해제")
    @DeleteMapping("/{productId}/favorite-prompts/{messageId}")
    public void deleteFavoritePrompt(
        @PathVariable UUID productId,
        @PathVariable UUID messageId) {
        productCommandService.deleteFavoritePrompt(productId, messageId);
    }

    @Operation(summary = "고정 메세지 해제")
    @DeleteMapping("/{productId}/fixed-messages")
    public void deleteFixedMessage(@PathVariable UUID productId) {
        productCommandService.deleteFixedMessage(productId);
    }
}
