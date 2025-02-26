package com.writely.terms.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.writely.common.converter.AbstractEnumCodeConverter;
import com.writely.common.enums.Codable;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@RequiredArgsConstructor
@Slf4j
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

    public static class SpringConverter implements org.springframework.core.convert.converter.Converter<String, TermsCode> {
        @Override
        public TermsCode convert(String code) {
            return TermsCode.fromCode(code);
        }
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
