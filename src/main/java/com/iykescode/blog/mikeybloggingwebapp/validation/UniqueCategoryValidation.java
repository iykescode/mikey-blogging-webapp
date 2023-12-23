package com.iykescode.blog.mikeybloggingwebapp.validation;

import com.iykescode.blog.mikeybloggingwebapp.annotation.UniqueCategoryValidator;
import com.iykescode.blog.mikeybloggingwebapp.repository.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UniqueCategoryValidation implements ConstraintValidator<UniqueCategoryValidator, String> {

    private final CategoryRepository categoryRepository;

    @Override
    public void initialize(UniqueCategoryValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !categoryRepository.existsByName(name);
    }
}
