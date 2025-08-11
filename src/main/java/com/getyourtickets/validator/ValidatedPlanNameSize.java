package com.getyourtickets.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidatedPlanNameSize implements ConstraintValidator<PlanNameSizeValidator, String> {
    private long min;
    private long max;
    @Override
    public void initialize(PlanNameSizeValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !(value.length() < min || value.length() > max);
    }
}
