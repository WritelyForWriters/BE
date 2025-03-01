package com.writely.auth.domain.enums;

import com.writely.common.converter.AbstractEnumCodeConverter;
import com.writely.common.enums.Codable;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginAttemptResultType implements Codable {
    SUCCEED("succeed"),
    FAILED("failed"),
    BLOCKED("blocked");

    private final String code;

    @Converter
    public static class TypeCodeConverter extends AbstractEnumCodeConverter<LoginAttemptResultType> {
        @Override
        public LoginAttemptResultType convertToEntityAttribute(String dbData) {
            return this.toEntityAttribute(LoginAttemptResultType.class, dbData);
        }
    }
}
