package com.getyourtickets.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValidatedPermissionName.class })
public @interface PermissionNameValidator {
    String message() default "Invalid permission name";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
