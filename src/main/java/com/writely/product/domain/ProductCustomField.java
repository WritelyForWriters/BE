package com.writely.product.domain;

import com.writely.common.domain.BaseAuditTimeEntity;
import com.writely.product.domain.enums.ProductSectionType;
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
@Table(name = "product_custom_field")
public class ProductCustomField extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "section_id", nullable = false)
    private UUID sectionId;

    @Convert(converter = ProductSectionType.TypeCodeConverter.class)
    @Column(name = "section_type", nullable = false)
    private ProductSectionType sectionType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "seq", nullable = false)
    private Short seq;

    @Builder
    public ProductCustomField(UUID productId, UUID sectionId, ProductSectionType sectionType, String name, String content, Short seq) {
        this.productId = productId;
        this.sectionId = sectionId;
        this.sectionType = sectionType;
        this.name = name;
        this.content = content;
        this.seq = seq;
    }

    public void update(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
