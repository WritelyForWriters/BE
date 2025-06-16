package writeon.domain.assistant.enums;

import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import writeon.domain.common.converter.AbstractEnumCodeConverter;
import writeon.domain.common.enums.Codable;

@Getter
@RequiredArgsConstructor
public enum MessageSenderRole implements Codable {

    MEMBER("member"),
    ASSISTANT("assistant");

    private final String code;

    @Converter
    public static class TypeCodeConverter extends AbstractEnumCodeConverter<MessageSenderRole> {
        @Override
        public MessageSenderRole convertToEntityAttribute(String dbData) {
            return this.toEntityAttribute(MessageSenderRole.class, dbData);
        }
    }
}
