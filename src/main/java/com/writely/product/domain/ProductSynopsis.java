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
@Table(name = "product_synopsis")
public class ProductSynopsis extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "length")
    private String length;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "logline")
    private String logline;

    @Column(name = "example")
    private String example;

    @Builder
    public ProductSynopsis(UUID id, String genre, String length, String purpose, String logline, String example) {
        this.id = id;
        this.genre = genre;
        this.length = length;
        this.purpose = purpose;
        this.logline = logline;
        this.example = example;
    }

    public void update(String genre, String length, String purpose, String logline, String example) {
        this.genre = genre;
        this.length = length;
        this.purpose = purpose;
        this.logline = logline;
        this.example = example;
    }
}
