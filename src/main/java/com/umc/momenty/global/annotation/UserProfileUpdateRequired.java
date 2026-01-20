package com.umc.momenty.global.annotation;

import com.umc.momenty.global.validator.UserProfileUpdateRequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserProfileUpdateRequiredValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserProfileUpdateRequired {

    String message() default "적어도 하나 이상의 필드는 값이 있어야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
