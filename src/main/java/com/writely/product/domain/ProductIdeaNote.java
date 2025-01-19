package com.writely.product.domain;

import com.writely.common.domain.BaseAuditTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_ideanote")
public class ProductIdeaNote extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public ProductIdeaNote(UUID id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
