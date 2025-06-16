package writeon.api.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.MemberUtil;
import writeon.api.product.repository.ProductDao;
import writeon.api.product.response.ProductDetailResponse;
import writeon.api.product.response.ProductFavoritePromptResponse;
import writeon.api.product.response.ProductFixedMessageResponse;
import writeon.api.product.response.ProductMemoResponse;
import writeon.api.product.response.ProductResponse;
import writeon.api.product.response.ProductTemplateResponse;
import writeon.domain.assistant.AssistantMessage;
import writeon.domain.product.Product;
import writeon.domain.product.ProductFixedMessage;
import writeon.domain.product.ProductMemo;
import writeon.domain.product.enums.ProductException;
import writeon.domain.product.repository.ProductJpaRepository;

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
        return productRepository.findByIdAndCreatedBy(productId, MemberUtil.getMemberId())
            .orElseThrow(() -> new BaseException(ProductException.NOT_EXIST));
    }

    public List<ProductFavoritePromptResponse> getFavoritePrompts(UUID productId) {
        verifyExist(productId);

        return productDao.selectFavoritePrompts(productId);
    }

    public ProductFixedMessageResponse getFixedMessage(UUID productId) {
        ProductFixedMessage fixedMessage = getById(productId).getFixedMessage();

        if (fixedMessage == null) {
            return null;
        }

        AssistantMessage message = fixedMessage.getMessage();
        return new ProductFixedMessageResponse(message.getId(), message.getContent());
    }

    public ProductTemplateResponse getTemplate(UUID productId) {
        return new ProductTemplateResponse(getById(productId));
    }

    public List<ProductMemoResponse> getMemos(UUID productId) {
        Product product = getById(productId);

        return product.getMemos()
            .stream()
            .sorted(Comparator.comparing(ProductMemo::getUpdatedAt).reversed())
            .map(ProductMemoResponse::new)
            .toList();
    }

    public void verifyExist(UUID productId) {
        if (!productRepository.existsByIdAndCreatedBy(productId, MemberUtil.getMemberId())) {
            throw new BaseException(ProductException.NOT_EXIST);
        }
    }
}
