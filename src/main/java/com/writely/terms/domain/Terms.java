package com.writely.terms.domain;

import com.writely.common.domain.BaseAuditTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "terms")
public class Terms extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String cd;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

}
