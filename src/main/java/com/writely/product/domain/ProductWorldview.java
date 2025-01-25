package com.writely.product.domain;

import com.writely.common.domain.BaseAuditTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_worldview")
public class ProductWorldview extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "geography")
    private String geography;

    @Column(name = "history")
    private String history;

    @Column(name = "politics")
    private String politics;

    @Column(name = "society")
    private String society;

    @Column(name = "religion")
    private String religion;

    @Column(name = "economy")
    private String economy;

    @Column(name = "technology")
    private String technology;

    @Column(name = "lifestyle")
    private String lifestyle;

    @Column(name = "language")
    private String language;

    @Column(name = "culture")
    private String culture;

    @Column(name = "species")
    private String species;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "conflict")
    private String conflict;

    @Builder
    public ProductWorldview(UUID id, String geography, String history, String politics, String society, String religion, String economy, String technology, String lifestyle, String language, String culture, String species, String occupation, String conflict) {
        this.id = id;
        this.geography = geography;
        this.history = history;
        this.politics = politics;
        this.society = society;
        this.religion = religion;
        this.economy = economy;
        this.technology = technology;
        this.lifestyle = lifestyle;
        this.language = language;
        this.culture = culture;
        this.species = species;
        this.occupation = occupation;
        this.conflict = conflict;
    }

    public void update(String geography, String history, String politics, String society, String religion, String economy, String technology, String lifestyle, String language, String culture, String species, String occupation, String conflict) {
        this.geography = geography;
        this.history = history;
        this.politics = politics;
        this.society = society;
        this.religion = religion;
        this.economy = economy;
        this.technology = technology;
        this.lifestyle = lifestyle;
        this.language = language;
        this.culture = culture;
        this.species = species;
        this.occupation = occupation;
        this.conflict = conflict;
    }
}
