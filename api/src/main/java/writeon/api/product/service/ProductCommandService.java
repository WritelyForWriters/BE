package writeon.api.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import writeon.api.common.exception.BaseException;
import writeon.api.product.request.ProductMemoSaveRequest;
import writeon.api.product.request.ProductModifyRequest;
import writeon.api.product.request.ProductTemplateSaveRequest;
import writeon.domain.product.*;
import writeon.domain.product.enums.ProductException;
import writeon.domain.product.enums.ProductSectionType;
import writeon.domain.product.repository.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Transactional
    public UUID create() {
        return productRepository.save(new Product()).getId();
    }

    @Transactional
    public void createMemo(UUID productId, ProductMemoSaveRequest request) {
        verifyExistProduct(productId);

        productMemoRepository.save(new ProductMemo(productId, request.getContent(),
            request.getSelectedText(), request.getStartIndex(), request.getEndIndex()));
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
    public UUID modify(UUID productId, ProductModifyRequest request) {
        Product product = productQueryService.getById(productId);

        product.update(request.getTitle(), request.getContent());
        return product.getId();
    }

    @Transactional
    public void modifyMemo(UUID productId, UUID memoId, ProductMemoSaveRequest request) {
        verifyExistProduct(productId);

        ProductMemo memo = getMemoById(memoId);

        memo.update(request.getContent(), request.getSelectedText(), request.getStartIndex()
            , request.getEndIndex(), request.getIsCompleted());
    }

    @Transactional
    public void deleteMemo(UUID productId, UUID memoId) {
        verifyExistProduct(productId);

        ProductMemo memo = getMemoById(memoId);

        productMemoRepository.delete(memo);
    }

    private ProductMemo getMemoById(UUID memoId) {
        return productMemoRepository.findById(memoId)
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

    private void verifyExistProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new BaseException(ProductException.NOT_EXIST);
        }
    }
}
