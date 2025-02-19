package com.writely.terms.request;

import com.writely.terms.domain.enums.TermsCode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TermsAgreeRequest {
    @NotNull
    private TermsCode termsCd;

    @NotNull
    private Boolean isAgreed;
}
