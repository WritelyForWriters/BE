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
    private String exposition;
    @Column
    private String complication;
    @Column
    private String climax;
    @Column
    private String resolution;

    @Builder
    public ProductPlot(UUID id, String exposition, String complication, String climax, String resolution) {
        this.id = id;
        this.exposition = exposition;
        this.complication = complication;
        this.climax = climax;
        this.resolution = resolution;
    }

    public void update(String exposition, String complication, String climax, String resolution) {
        this.exposition = exposition;
        this.complication = complication;
        this.climax = climax;
        this.resolution = resolution;
    }
}
