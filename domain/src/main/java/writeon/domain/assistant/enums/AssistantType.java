package writeon.domain.assistant.enums;

import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import writeon.domain.common.converter.AbstractEnumCodeConverter;
import writeon.domain.common.enums.Codable;

@Getter
@RequiredArgsConstructor
public enum AssistantType implements Codable {

    AUTO_MODIFY("auto modify"),
    FEEDBACK("feedback"),
    CHAT("chat"),
    USER_MODIFY("user modify"),
    PLANNER("planner");

    private final String code;

    @Converter
    public static class TypeCodeConverter extends AbstractEnumCodeConverter<AssistantType> {
        @Override
        public AssistantType convertToEntityAttribute(String dbData) {
            return this.toEntityAttribute(AssistantType.class, dbData);
        }
    }
}
