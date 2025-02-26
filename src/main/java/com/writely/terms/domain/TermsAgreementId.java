package com.writely.terms.domain;

import com.writely.terms.domain.enums.TermsCode;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsAgreementId implements Serializable {

    @Convert(converter = TermsCode.TypeCodeConverter.class)
    @Column(name = "terms_cd", nullable = false)
    private TermsCode termsCd;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

}
