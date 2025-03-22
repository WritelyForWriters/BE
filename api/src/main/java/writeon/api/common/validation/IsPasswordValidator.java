package writeon.api.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.stream.Stream;

public class IsPasswordValidator implements ConstraintValidator<IsPassword, String> {

    final String digit = "0-9";
    final String uppercase = "A-Z";
    final String lowercase = "a-z";
    final String specialCharacters = "@$!%*?&";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null)
            return true;

        final String otherCharacterMatcher = String.format(".*[^%s%s%s%s].*", digit, uppercase, lowercase, specialCharacters);
        if (s.matches(otherCharacterMatcher))
            return false;

        long matchCount = Stream.of(digit, uppercase, lowercase, specialCharacters)
                .filter(regex -> s.matches(String.format(".*[%s].*", regex)))
                .count();

        return matchCount >= 3;
    }
}