package writeon.domain.assistant.enums;

import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import writeon.domain.common.converter.AbstractEnumCodeConverter;
import writeon.domain.common.enums.Codable;

@Getter
@RequiredArgsConstructor
public enum AssistantStatus implements Codable {

    DRAFT("draft"),
    IN_PROGRESS("in progress"),
    COMPLETED("completed");

    private final String code;

    @Converter
    public static class TypeCodeConverter extends AbstractEnumCodeConverter<AssistantStatus> {
        @Override
        public AssistantStatus convertToEntityAttribute(String dbData) {
            return this.toEntityAttribute(AssistantStatus.class, dbData);
        }
    }
}
