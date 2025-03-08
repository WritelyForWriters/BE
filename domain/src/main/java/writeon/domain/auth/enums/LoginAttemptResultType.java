package writeon.domain.auth.enums;

import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import writeon.domain.common.converter.AbstractEnumCodeConverter;
import writeon.domain.common.enums.Codable;

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
