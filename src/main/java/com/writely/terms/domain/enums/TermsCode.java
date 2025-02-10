package com.writely.terms.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.writely.common.converter.AbstractEnumCodeConverter;
import com.writely.common.enums.Codable;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TermsCode implements Codable {
    PRIVACY_POLICY("1001"),
    MARKETING_AGREEMENT("1002");

    private final String code;

    @JsonCreator
    public static TermsCode fromCode(final String code) {
        return Codable.fromCode(TermsCode.class, code);
    }

    @Converter
    public static class TypeCodeConverter extends AbstractEnumCodeConverter<TermsCode> {
        @Override
        public TermsCode convertToEntityAttribute(String dbData) {
            return this.toEntityAttribute(TermsCode.class, dbData);
        }
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
