package writeon.api.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsPasswordValidator implements ConstraintValidator<IsPassword, String> {

    final String regexp
            = "(?=(.*[A-Z]))(?=(.*[a-z]))(?=(.*\\d))(?=(.*[\\W_])).{8,}$|^(?=(.*[A-Z]))(?=(.*[a-z]))(?=(.*\\d)).{8,}$|^(?=(.*[A-Z]))(?=(.*[a-z]))(?=(.*[\\W_])).{8,}$|^(?=(.*[A-Z]))(?=(.*\\d))(?=(.*[\\W_])).{8,}$|^(?=(.*[a-z]))(?=(.*\\d))(?=(.*[\\W_])).{8,}";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null)
            return true;

        return s.matches(regexp);
    }
}