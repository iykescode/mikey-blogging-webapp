package com.iykescode.blog.mikeybloggingwebapp.validation;

import com.iykescode.blog.mikeybloggingwebapp.annotation.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class PasswordValidation implements ConstraintValidator<PasswordValidator, String> {

    List<String> weakPasswords;

    @Override
    public void initialize(PasswordValidator constraintAnnotation) {
        weakPasswords = Arrays.asList(
                "12345678", "password", "qwerty",
                "11111111", "22222222", "33333333",
                "44444444", "55555555", "66666666",
                "77777777", "88888888", "99999999", "00000000");
    }

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext cxt) {
        return passwordField != null && (!weakPasswords.contains(passwordField));
    }
}
