package com.writely.product.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.writely.common.converter.AbstractEnumCodeConverter;
import com.writely.common.enums.Codable;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;

@Getter
@RequiredArgsConstructor
public enum ProductSectionType implements Codable {

    CHARACTER("character"),
    WORLDVIEW("worldview");

    private final String code;

    public static boolean containCode(String code) {
        return EnumSet.allOf(ProductSectionType.class).stream().anyMatch(e -> e.getCode().equals(code));
    }

    @JsonCreator
    public static ProductSectionType fromCode(final String code) {
        return Codable.fromCode(ProductSectionType.class, code);
    }

    @Converter
    public static class TypeCodeConverter extends AbstractEnumCodeConverter<ProductSectionType> {
        @Override
        public ProductSectionType convertToEntityAttribute(String dbData) {
            return this.toEntityAttribute(ProductSectionType.class, dbData);
        }
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
