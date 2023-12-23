package com.iykescode.blog.mikeybloggingwebapp.annotation;

import com.iykescode.blog.mikeybloggingwebapp.validation.UniqueCategoryValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueCategoryValidation.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCategoryValidator {

    String message() default "This category already exists in our database !!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
