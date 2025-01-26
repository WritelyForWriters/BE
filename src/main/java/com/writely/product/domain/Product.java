package com.writely.product.domain;

import com.writely.common.domain.BaseAuditTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<ProductCharacter> characters = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<ProductCustomField> customFields = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ProductIdeaNote ideaNote;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<ProductMemo> memos = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ProductPlot plot;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ProductSynopsis synopsis;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ProductWorldview worldview;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addCharacters(List<ProductCharacter> addCharacters) {
        if (addCharacters == null || addCharacters.isEmpty()) {
            return;
        }
        this.characters.addAll(addCharacters);
    }

    public void addCustomFields(List<ProductCustomField> addCustomFields) {
        if (addCustomFields == null || addCustomFields.isEmpty()) {
            return;
        }
        this.customFields.addAll(addCustomFields);
    }

    public void addIdeaNote(ProductIdeaNote addIdeaNote) {
        if (addIdeaNote == null) {
            return;
        }
        this.ideaNote = addIdeaNote;
    }

    public void addMemo(ProductMemo addMemo) {
        if (addMemo == null) {
            return;
        }
        this.memos.add(addMemo);
    }

    public void addPlot(ProductPlot addPlot) {
        if (addPlot == null) {
            return;
        }
        this.plot = addPlot;
    }

    public void addSynopsis(ProductSynopsis addSynopsis) {
        if (addSynopsis == null) {
            return;
        }
        this.synopsis = addSynopsis;
    }

    public void addWorldview(ProductWorldview addWorldview) {
        if (addWorldview == null) {
            return;
        }
        this.worldview = addWorldview;
    }
}
