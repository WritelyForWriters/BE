package writeon.api.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = IsNicknameValidator.class)
@Size(min = 2, max = 20)
public @interface IsNickname {
    String message() default "한글, 영문(대소문자), 숫자, 일부 특수문자(_ , .)로 이루어진 2자 이상 20자 이하의 문자여야 합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}