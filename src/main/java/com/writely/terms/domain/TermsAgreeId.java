package com.writely.terms.domain;

import com.writely.terms.domain.enums.TermsCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TermsAgreeId implements Serializable {

    private TermsCode termsCd;
    private UUID memberId;

}
