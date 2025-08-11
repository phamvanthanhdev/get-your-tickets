package com.getyourtickets.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatedPermissionName implements ConstraintValidator<PermissionNameValidator, String> {

    @Override
    public void initialize(PermissionNameValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return !value.contains(" ");
    }
}
