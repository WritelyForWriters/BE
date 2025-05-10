package writeon.domain.assistant.enums;

import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import writeon.domain.common.converter.AbstractEnumCodeConverter;
import writeon.domain.common.enums.Codable;

@Getter
@RequiredArgsConstructor
public enum FeedbackType implements Codable {

    AWKWARD_SENTENCE("AWKWARD_SENTENCE"),
    INACCURATE_INFO("INACCURATE_INFO"),
    UNAPPLIED_SETTING("UNAPPLIED_SETTING"),
    ETC("ETC");

    private final String code;

    public static FeedbackType fromCode(final String code) {
        return Codable.fromCode(FeedbackType.class, code);
    }

    @Converter
    public static class TypeCodeConverter extends AbstractEnumCodeConverter<FeedbackType> {
        @Override
        public FeedbackType convertToEntityAttribute(String dbData) {
            return this.toEntityAttribute(FeedbackType.class, dbData);
        }
    }
}
