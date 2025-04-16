package writeon.api.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import writeon.api.assistant.service.AssistantService;
import writeon.api.assistant.service.DocumentUploadService;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.MemberUtil;
import writeon.api.product.request.ProductMemoSaveRequest;
import writeon.api.product.request.ProductSaveRequest;
import writeon.api.product.request.ProductTemplateSaveRequest;
import writeon.domain.assistant.Assistant;
import writeon.domain.assistant.AssistantMessage;
import writeon.domain.assistant.enums.AssistantException;
import writeon.domain.assistant.enums.AssistantType;
import writeon.domain.assistant.enums.MessageSenderRole;
import writeon.domain.product.Product;
import writeon.domain.product.ProductCharacter;
import writeon.domain.product.ProductCustomField;
import writeon.domain.product.ProductFavoritePrompt;
import writeon.domain.product.ProductFixedMessage;
import writeon.domain.product.ProductIdeaNote;
import writeon.domain.product.ProductMemo;
import writeon.domain.product.ProductPlot;
import writeon.domain.product.ProductSynopsis;
import writeon.domain.product.ProductWorldview;
import writeon.domain.product.enums.ProductException;
import writeon.domain.product.enums.ProductSectionType;
import writeon.domain.product.repository.ProductCharacterJpaRepository;
import writeon.domain.product.repository.ProductCustomFieldJpaRepository;
import writeon.domain.product.repository.ProductFavoritePromptJpaRepository;
import writeon.domain.product.repository.ProductIdeaNoteJpaRepository;
import writeon.domain.product.repository.ProductJpaRepository;
import writeon.domain.product.repository.ProductMemoJpaRepository;
import writeon.domain.product.repository.ProductPlotJpaRepository;
import writeon.domain.product.repository.ProductSynopsisJpaRepository;
import writeon.domain.product.repository.ProductWorldviewJpaRepository;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

    private final ProductQueryService productQueryService;
    private final ProductJpaRepository productRepository;
    private final ProductCharacterJpaRepository productCharacterJpaRepository;
    private final ProductCustomFieldJpaRepository productCustomFieldJpaRepository;
    private final ProductIdeaNoteJpaRepository productIdeaNoteRepository;
    private final ProductMemoJpaRepository productMemoRepository;
    private final ProductPlotJpaRepository productPlotRepository;
    private final ProductSynopsisJpaRepository productSynopsisRepository;
    private final ProductWorldviewJpaRepository productWorldviewRepository;
    private final ProductFavoritePromptJpaRepository productFavoritePromptRepository;

    private final AssistantService assistantService;
    private final DocumentUploadService documentUploadService;

    @Transactional
    public UUID create() {
        return productRepository.save(new Product()).getId();
    }

    @Transactional
    public void createMemo(UUID productId, ProductMemoSaveRequest request) {
        productQueryService.verifyExist(productId);

        productMemoRepository.save(new ProductMemo(productId, request.getTitle(), request.getContent(),
            request.getSelectedText(), request.getStartIndex(), request.getEndIndex()));
    }

    @Transactional
    public void createFavoritePrompt(UUID productId, UUID assistantId) {
        Product product = productQueryService.getById(productId);
        Assistant assistant = assistantService.getById(assistantId);

        if (!assistant.getProductId().equals(productId)) {
            throw new BaseException(AssistantException.NOT_EXIST);
        }

        // 자유 대화 기능에서만 프롬프트 즐겨찾기 등록 가능
        if (!assistant.getType().equals(AssistantType.CHAT)) {
            throw new BaseException(ProductException.INVALID_FAVORITE_PROMPT_TYPE);
        }
        AssistantMessage message = assistantService.getMessageByAssistantId(assistantId, MessageSenderRole.MEMBER);

        // 이미 즐겨찾기 등록된 프롬프트인지 검증
        if (productFavoritePromptRepository.existsByProduct_IdAndMessageId(productId, message.getId())) {
            throw new BaseException(ProductException.ALREADY_EXIST_FAVORITE_PROMPT);
        }

        product.getFavoritePrompts().add(new ProductFavoritePrompt(product, message.getId()));
    }

    @Transactional
    public void createFixedMessage(UUID productId, UUID assistantId) {
        Product product = productQueryService.getById(productId);
        Assistant assistant = assistantService.getById(assistantId);

        if (!assistant.getProductId().equals(productId)) {
            throw new BaseException(AssistantException.NOT_EXIST);
        }

        AssistantMessage message = assistantService.getMessageByAssistantId(assistantId, MessageSenderRole.ASSISTANT);

        product.setFixedMessage(new ProductFixedMessage(message));
    }

    @Transactional
    public void saveTemplate(UUID productId, ProductTemplateSaveRequest request) {
        Product product = productQueryService.getById(productId);

        modifyCharacters(product, request.getCharacters());
        modifyIdeaNote(product, request.getIdeaNote());
        modifyPlot(product, request.getPlot());
        modifySynopsis(product, request.getSynopsis());
        modifyWorldview(product, request.getWorldview());
    }

    @Transactional
    public UUID save(UUID productId, ProductSaveRequest request) {
        Product product = productQueryService.getById(productId);

        product.update(request.getTitle(), request.getContent());

        // 수동 저장 시 작품 내용 임베딩
        if (!request.getIsAutoSave()) {
            documentUploadService.documentUpload(productId, product.getContent());
        }

        return product.getId();
    }

    @Transactional
    public void modifyMemo(UUID productId, UUID memoId, ProductMemoSaveRequest request) {
        productQueryService.verifyExist(productId);

        ProductMemo memo = getMemoById(memoId);

        memo.update(request.getTitle(), request.getContent(), request.getSelectedText(),
            request.getStartIndex(), request.getEndIndex(), request.getIsCompleted());
    }

    @Transactional
    public void deleteMemo(UUID productId, UUID memoId) {
        productQueryService.verifyExist(productId);

        ProductMemo memo = getMemoById(memoId);

        productMemoRepository.delete(memo);
    }

    @Transactional
    public void deleteFavoritePrompt(UUID productId, UUID messageId) {
        Product product = productQueryService.getById(productId);

        ProductFavoritePrompt favoritePrompt = product.getFavoritePrompts().stream()
            .filter(prompt -> prompt.getMessageId().equals(messageId))
            .findFirst()
            .orElseThrow(() -> new BaseException(ProductException.NOT_EXIST_FAVORITE_PROMPT));

        product.deleteFavoritePrompt(favoritePrompt);
        productFavoritePromptRepository.delete(favoritePrompt);
    }

    @Transactional
    public void deleteFixedMessage(UUID productId) {
        Product product = productQueryService.getById(productId);
        product.deleteFixedMessage();
    }

    private ProductMemo getMemoById(UUID memoId) {
        return productMemoRepository.findByIdAndCreatedBy(memoId, MemberUtil.getMemberId())
            .orElseThrow(() -> new BaseException(ProductException.NOT_EXIST_MEMO));
    }

    private void modifyCharacters(Product product, List<ProductTemplateSaveRequest.Character> requestInfos) {
        Map<UUID, ProductCharacter> savedCharacterMap = product.getCharacters().stream()
            .collect(Collectors.toMap(ProductCharacter::getId, Function.identity()));
        if (requestInfos == null || requestInfos.isEmpty()) {
            productCustomFieldJpaRepository.deleteAllByProductIdAndSectionType(product.getId(), ProductSectionType.CHARACTER.getCode());
            productCharacterJpaRepository.deleteAll(savedCharacterMap.values());
            return;
        }

        Set<UUID> modifyIds = requestInfos.stream()
            .map(ProductTemplateSaveRequest.Character::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        List<ProductCharacter> deleteCharacters = savedCharacterMap.values().stream()
            .filter(e -> !modifyIds.contains(e.getId()))
            .toList();

        productCharacterJpaRepository.deleteAll(deleteCharacters);

        List<ProductCharacter> addCharacters = new ArrayList<>();
        requestInfos.forEach(request -> {
            if (request.getId() == null) {
                ProductCharacter newCharacter = request.toEntity(product.getId());
                addCharacters.add(newCharacter);

                modifyCustomFields(product.getId(), newCharacter.getId(), ProductSectionType.CHARACTER,
                    request.getCustomFields(), List.of());
            } else {
                ProductCharacter savedCharacter = savedCharacterMap.get(request.getId());
                if (savedCharacter == null) {
                    throw new BaseException(ProductException.NOT_EXIST_CHARACTER);
                }

                savedCharacter.update(request.getIntro(), request.getName(), request.getAge(), request.getGender(), request.getOccupation(),
                    request.getAppearance(), request.getPersonality(), request.getCharacteristic(), request.getRelationship());

                modifyCustomFields(product.getId(), savedCharacter.getId(), ProductSectionType.CHARACTER,
                    request.getCustomFields(), savedCharacter.getCustomFields());
            }
        });

        productCharacterJpaRepository.saveAll(addCharacters);
    }

    private void modifyCustomFields(UUID productId, UUID sectionId, ProductSectionType sectionType,
                                    List<ProductTemplateSaveRequest.CustomField> requestInfos,
                                    List<ProductCustomField> savedCustomFields) {
        Map<UUID, ProductCustomField> savedCustomFieldMap = savedCustomFields.stream()
            .collect(Collectors.toMap(ProductCustomField::getId, Function.identity()));

        if (requestInfos == null || requestInfos.isEmpty()) {
            productCustomFieldJpaRepository.deleteAll(savedCustomFields);
            return;
        }

        Set<UUID> modifyIds = requestInfos.stream()
            .map(ProductTemplateSaveRequest.CustomField::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        List<ProductCustomField> deleteCustomFields = savedCustomFields.stream()
            .filter(cf -> !modifyIds.contains(cf.getId()))
            .toList();

        productCustomFieldJpaRepository.deleteAll(deleteCustomFields);

        List<ProductCustomField> addCustomFields = new ArrayList<>();

        for (ProductTemplateSaveRequest.CustomField request : requestInfos) {
            if (request.getId() == null) {
                addCustomFields.add(request.toEntity(productId, sectionId, sectionType));
            } else {
                ProductCustomField savedField = savedCustomFieldMap.get(request.getId());
                if (savedField == null) {
                    throw new BaseException(ProductException.NOT_EXIST_CUSTOM_FIELD);
                }
                savedField.update(request.getName(), request.getContent());
            }
        }

        productCustomFieldJpaRepository.saveAll(addCustomFields);
    }

    private void modifyIdeaNote(Product product, ProductTemplateSaveRequest.IdeaNote requestInfo) {
        ProductIdeaNote savedIdeaNote = product.getIdeaNote();

        if (requestInfo == null) {
            if (savedIdeaNote != null) {
                productIdeaNoteRepository.delete(savedIdeaNote);
            }
            return;
        }

        if (savedIdeaNote == null) {
            ProductIdeaNote newIdeaNote = new ProductIdeaNote(product.getId(), requestInfo.getTitle(), requestInfo.getContent());
            productIdeaNoteRepository.save(newIdeaNote);
        } else {
            savedIdeaNote.update(requestInfo.getTitle(), requestInfo.getContent());
        }
    }

    private void modifyPlot(Product product, ProductTemplateSaveRequest.Plot requestInfo) {
        ProductPlot savedPlot = product.getPlot();

        if (requestInfo == null) {
            if (savedPlot != null) {
                productPlotRepository.delete(savedPlot);
            }
            return;
        }

        if (savedPlot == null) {
            productPlotRepository.save(requestInfo.toEntity(product.getId()));
        } else {
            savedPlot.update(requestInfo.getContent());
        }
    }

    private void modifySynopsis(Product product, ProductTemplateSaveRequest.Synopsis requestInfo) {
        ProductSynopsis savedSynopsis = product.getSynopsis();

        if (requestInfo == null) {
            if (savedSynopsis != null) {
                productSynopsisRepository.delete(savedSynopsis);
            }
            return;
        }

        if (savedSynopsis == null) {
            productSynopsisRepository.save(requestInfo.toEntity(product.getId()));
        } else {
            savedSynopsis.update(requestInfo.getGenre(), requestInfo.getLength(), requestInfo.getPurpose(), requestInfo.getLogline(), requestInfo.getExample());
        }
    }

    private void modifyWorldview(Product product, ProductTemplateSaveRequest.Worldview requestInfo) {
        ProductWorldview savedWorldview = product.getWorldview();

        if (requestInfo == null) {
            if (savedWorldview != null) {
                productCustomFieldJpaRepository.deleteAllByProductIdAndSectionType(product.getId(), ProductSectionType.WORLDVIEW.getCode());
                productWorldviewRepository.delete(savedWorldview);
            }
            return;
        }

        if (savedWorldview == null) {
            ProductWorldview newWorldview = productWorldviewRepository.save(requestInfo.toEntity(product.getId()));

            modifyCustomFields(product.getId(), newWorldview.getId(), ProductSectionType.WORLDVIEW,
                requestInfo.getCustomFields(), List.of());
        } else {
            savedWorldview.update(requestInfo.getGeography(), requestInfo.getHistory(), requestInfo.getPolitics(),
                requestInfo.getSociety(), requestInfo.getReligion(), requestInfo.getEconomy(), requestInfo.getTechnology(), requestInfo.getLifestyle(),
                requestInfo.getLanguage(), requestInfo.getCulture(), requestInfo.getSpecies(), requestInfo.getOccupation(), requestInfo.getConflict());

            modifyCustomFields(product.getId(), savedWorldview.getId(), ProductSectionType.WORLDVIEW,
                requestInfo.getCustomFields(), savedWorldview.getCustomFields());
        }
    }
}
