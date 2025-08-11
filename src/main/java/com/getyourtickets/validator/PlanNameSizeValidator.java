package com.getyourtickets.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValidatedPlanNameSize.class })
public @interface PlanNameSizeValidator {
    String message() default "Invalid plan name size";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    long min();
    long max();
}
