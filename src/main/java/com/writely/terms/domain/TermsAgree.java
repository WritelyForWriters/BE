package com.writely.terms.domain;

import com.writely.common.domain.BaseTimeEntity;
import com.writely.terms.domain.enums.TermsCode;
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
@Table(name = "terms_agree")
public class TermsAgree extends BaseTimeEntity {

    @Id
    @Column(nullable = false)
    private TermsCode termsCd;

    @Id
    @Column(nullable = false)
    private UUID memberId;

}
