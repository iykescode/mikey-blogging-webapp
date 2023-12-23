package com.iykescode.blog.mikeybloggingwebapp.annotation;

import com.iykescode.blog.mikeybloggingwebapp.validation.UniqueUsernameValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidation.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsernameValidator {

    String message() default "This username already exists in our database !!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
