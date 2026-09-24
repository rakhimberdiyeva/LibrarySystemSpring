package com.library.auth.auth.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        if (value.length() < 8) {
            return false;
        }
        return  value.chars().anyMatch(Character::isUpperCase) &&
                value.chars().anyMatch(Character::isLowerCase) &&
                value.chars().anyMatch(Character::isDigit);
    }
}
