package com.iykescode.blog.mikeybloggingwebapp.validation;

import com.iykescode.blog.mikeybloggingwebapp.annotation.UniqueUsernameValidator;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class UniqueUsernameValidation implements ConstraintValidator<UniqueUsernameValidator, String> {

    private final PersonRepository personRepository;

    @Override
    public void initialize(UniqueUsernameValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !personRepository.existsByUsername(username);
    }
}
