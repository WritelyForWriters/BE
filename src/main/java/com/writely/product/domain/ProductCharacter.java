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
@Table(name = "product_character")
public class ProductCharacter extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "intro")
    private String intro;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Short age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "appearance")
    private String appearance;

    @Column(name = "personality")
    private String personality;

    @Column(name = "characteristic")
    private String characteristic;

    @Column(name = "relationship")
    private String relationship;

    @Builder
    public ProductCharacter(UUID productId, String intro, String name, Short age, String gender, String occupation, String appearance, String personality, String characteristic, String relationship) {
        this.productId = productId;
        this.intro = intro;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.appearance = appearance;
        this.personality = personality;
        this.characteristic = characteristic;
        this.relationship = relationship;
    }

    public void update(String intro, String name, Short age, String gender, String occupation, String appearance, String personality, String characteristic, String relationship) {
        this.intro = intro;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.appearance = appearance;
        this.personality = personality;
        this.characteristic = characteristic;
        this.relationship = relationship;
    }
}
