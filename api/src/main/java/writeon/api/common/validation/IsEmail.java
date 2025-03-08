package writeon.api.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = IsEmailValidator.class)
@Size(max = 255)
public @interface IsEmail {
    String message() default "이메일 형식이어야 합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}