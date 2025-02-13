package com.writely.product.service;

import com.writely.common.exception.BaseException;
import com.writely.product.domain.*;
import com.writely.product.domain.enums.ProductException;
import com.writely.product.repository.*;
import com.writely.product.request.ProductMemoSaveRequest;
import com.writely.product.request.ProductModifyRequest;
import com.writely.product.request.ProductTemplateSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        modifyCustomFields(product, request.getCustomFields());
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

    private void modifyCharacters(Product product, List<ProductTemplateSaveRequest.Character> characters) {
        Map<UUID, ProductCharacter> savedCharacterMap = product.getCharacters().stream()
            .collect(Collectors.toMap(ProductCharacter::getId, Function.identity()));
        if (characters.isEmpty()) {
            productCharacterJpaRepository.deleteAll(savedCharacterMap.values());
            return;
        }

        Set<UUID> modifyIds = characters.stream()
            .map(ProductTemplateSaveRequest.Character::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        List<ProductCharacter> deleteCharacters = savedCharacterMap.values().stream()
            .filter(e -> !modifyIds.contains(e.getId()))
            .toList();

        productCharacterJpaRepository.deleteAll(deleteCharacters);

        List<ProductCharacter> addCharacters = new ArrayList<>();
        characters.forEach(e -> {
            if (e.getId() == null) {
                addCharacters.add(e.toEntity(product.getId()));
            } else {
                ProductCharacter savedCharacter = savedCharacterMap.get(e.getId());
                if (savedCharacter == null) {
                    throw new BaseException(ProductException.NOT_EXIST_CHARACTER);
                }

                savedCharacter.update(e.getIntro(), e.getName(), e.getAge(), e.getGender(), e.getOccupation(),
                    e.getAppearance(), e.getPersonality(), e.getCharacteristic(), e.getRelationship());
            }
        });

        productCharacterJpaRepository.saveAll(addCharacters);
    }

    private void modifyCustomFields(Product product, List<ProductTemplateSaveRequest.CustomField> customFields) {
        Map<UUID, ProductCustomField> savedCustomFieldMap = product.getCustomFields().stream()
            .collect(Collectors.toMap(ProductCustomField::getId, Function.identity()));
        if (customFields.isEmpty()) {
            productCustomFieldJpaRepository.deleteAll(savedCustomFieldMap.values());
            return;
        }

        Set<UUID> modifyIds = customFields.stream()
            .map(ProductTemplateSaveRequest.CustomField::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        List<ProductCustomField> deleteCharacters = savedCustomFieldMap.values().stream()
            .filter(e -> !modifyIds.contains(e.getId()))
            .toList();

        productCustomFieldJpaRepository.deleteAll(deleteCharacters);

        List<ProductCustomField> addCustomFields = new ArrayList<>();
        customFields.forEach(e -> {
            if (e.getId() == null) {
                addCustomFields.add(e.toEntity(product.getId()));
            } else {
                ProductCustomField savedCustomField = savedCustomFieldMap.get(e.getId());
                if (savedCustomField == null) {
                    throw new BaseException(ProductException.NOT_EXIST_CUSTOM_FIELD);
                }

                savedCustomField.update(e.getSectionType(), e.getName(), e.getContent(), e.getSeq());
            }
        });

        productCustomFieldJpaRepository.saveAll(addCustomFields);
    }

    private void modifyIdeaNote(Product product, ProductTemplateSaveRequest.IdeaNote ideaNote) {
        ProductIdeaNote savedIdeaNote = product.getIdeaNote();

        if (ideaNote == null && savedIdeaNote != null) {
            productIdeaNoteRepository.delete(savedIdeaNote);
            return;
        }

        if (ideaNote != null) {
            if (savedIdeaNote == null) {
                ProductIdeaNote newIdeaNote = new ProductIdeaNote(product.getId(), ideaNote.getTitle(), ideaNote.getContent());
                productIdeaNoteRepository.save(newIdeaNote);
            } else {
                savedIdeaNote.update(ideaNote.getTitle(), ideaNote.getContent());
            }
        }
    }

    private void modifyPlot(Product product, ProductTemplateSaveRequest.Plot plot) {
        ProductPlot savedPlot = product.getPlot();

        if (plot == null && savedPlot != null) {
            productPlotRepository.delete(savedPlot);
            return;
        }

        if (plot != null) {
            if (savedPlot == null) {
                productPlotRepository.save(plot.toEntity(product.getId()));
            } else {
                savedPlot.update(plot.getExposition(), plot.getComplication(), plot.getComplication(), plot.getComplication());
            }
        }
    }

    private void modifySynopsis(Product product, ProductTemplateSaveRequest.Synopsis synopsis) {
        ProductSynopsis savedSynopsis = product.getSynopsis();

        if (synopsis == null && savedSynopsis != null) {
            productSynopsisRepository.delete(savedSynopsis);
            return;
        }

        if (synopsis != null) {
            if (savedSynopsis == null) {
                productSynopsisRepository.save(synopsis.toEntity(product.getId()));
            } else {
                savedSynopsis.update(synopsis.getGenre(), synopsis.getLength(), synopsis.getPurpose(), synopsis.getLogline(), synopsis.getExample());
            }
        }
    }

    private void modifyWorldview(Product product, ProductTemplateSaveRequest.Worldview worldview) {
        ProductWorldview savedWorldview = product.getWorldview();

        if (worldview == null && savedWorldview != null) {
            productWorldviewRepository.delete(savedWorldview);
            return;
        }

        if (worldview != null) {
            if (savedWorldview == null) {
                productWorldviewRepository.save(worldview.toEntity(product.getId()));
            } else {
                savedWorldview.update(worldview.getGeography(), worldview.getHistory(), worldview.getPolitics(),
                    worldview.getSociety(), worldview.getReligion(), worldview.getEconomy(), worldview.getTechnology(), worldview.getLifestyle(),
                    worldview.getLanguage(), worldview.getCulture(), worldview.getSpecies(), worldview.getOccupation(), worldview.getConflict());
            }
        }
    }

    private void verifyExistProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new BaseException(ProductException.NOT_EXIST);
        }
    }
}
