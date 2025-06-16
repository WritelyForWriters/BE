package writeon.api.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsNicknameValidator implements ConstraintValidator<IsNickname, String> {

    final String digit = "0-9";
    final String english = "a-zA-Z";
    final String korean = "가-힣";
    final String specialCharacters = "_,.";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null)
            return true;

        final String otherCharacterMatcher = String.format(".*[^%s%s%s%s].*", digit, english, korean, specialCharacters);
        if (s.matches(otherCharacterMatcher))
            return false;

        final String digitOnlyMatcher = String.format("[%s]*", digit);
        if (s.matches(digitOnlyMatcher))
            return false;

        final String startsWithSpecialCharacterMatcher = String.format("^[%s].*", specialCharacters);
        if (s.matches(startsWithSpecialCharacterMatcher))
            return false;

        final String endsWithSpecialCharacterMatcher = String.format("[%s].*$", specialCharacters);
        if (s.matches(endsWithSpecialCharacterMatcher))
            return false;

        final String sequentialSpecialCharacterMatcher = String.format(".*[%s]{2,}.*", specialCharacters);
        if (s.matches(sequentialSpecialCharacterMatcher))
            return false;

        return true;
    }
}