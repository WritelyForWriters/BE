package writeon.api.file.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import writeon.domain.common.converter.AbstractEnumCodeConverter;
import writeon.domain.common.enums.Codable;

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
