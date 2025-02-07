package com.writely.terms.domain.enums;

import com.writely.common.enums.Codable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TermsCode implements Codable {
    TERMS_PRIVACY_POLICY("1001"),
    TERMS_MARKETING_AGREEMENT("1002");

    private final String code;
}
