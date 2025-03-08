package com.writely.file.enums;

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
public enum FileUploadType implements Codable {
    IDEA_NOTE("idea-note", "idea-note");

    private final String code;
    private final String path;

    @JsonCreator
    public static FileUploadType fromCode(final String code) {
        return Codable.fromCode(FileUploadType.class, code);
    }

    @Converter
    public static class TypeCodeConverter extends AbstractEnumCodeConverter<FileUploadType> {
        @Override
        public FileUploadType convertToEntityAttribute(String dbData) {
            return this.toEntityAttribute(FileUploadType.class, dbData);
        }
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
