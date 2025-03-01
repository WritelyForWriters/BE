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
@Table(name = "product_plot")
public class ProductPlot extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column
    private String content;

    @Builder
    public ProductPlot(UUID id, String content) {
        this.id = id;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
