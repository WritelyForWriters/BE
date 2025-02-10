package com.writely.terms.domain;

import com.writely.common.domain.BaseTimeEntity;
import com.writely.terms.domain.enums.TermsCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "terms")
public class Terms extends BaseTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Convert(converter = TermsCode.TypeCodeConverter.class)
    @Column(nullable = false)
    private String cd;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

}
