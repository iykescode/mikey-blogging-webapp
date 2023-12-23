package com.iykescode.blog.mikeybloggingwebapp.validation;

import com.iykescode.blog.mikeybloggingwebapp.annotation.UniqueEmailValidator;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class UniqueEmailValidation implements ConstraintValidator<UniqueEmailValidator, String> {

    private final PersonRepository personRepository;

    @Override
    public void initialize(UniqueEmailValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !personRepository.existsByEmail(email);
    }
}
