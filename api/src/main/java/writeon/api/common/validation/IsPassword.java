package writeon.api.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = IsPasswordValidator.class)
@Size(min = 8, max = 255)
public @interface IsPassword {
    String message() default "최소 8자 이상이며, 대문자, 소문자, 숫자, 특수문자 중 3가지를 포함해야 합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}