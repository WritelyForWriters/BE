package com.writely.assistant.domain.enums;

import com.writely.common.converter.AbstractEnumCodeConverter;
import com.writely.common.enums.Codable;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
