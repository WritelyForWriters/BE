package writeon.domain.assistant.enums;

import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import writeon.domain.common.converter.AbstractEnumCodeConverter;
import writeon.domain.common.enums.Codable;

@Getter
@RequiredArgsConstructor
public enum FeedbackType implements Codable {

    AWKWARD_SENTENCE("Awkward sentence"),
    INACCURATE_INFO("Inaccurate information"),
    UNAPPLIED_SETTING("Unapplied settings"),
    ETC("ETC");

    private final String code;

    @Converter
    public static class TypeCodeConverter extends AbstractEnumCodeConverter<FeedbackType> {
        @Override
        public FeedbackType convertToEntityAttribute(String dbData) {
            return this.toEntityAttribute(FeedbackType.class, dbData);
        }
    }
}
