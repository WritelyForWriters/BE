package com.writely.common.validation;

import jakarta.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = IsEmailValidator.class)
public @interface IsEmail {
    String message() default "이메일 형식이어야 합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}