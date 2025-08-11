package com.getyourtickets.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValidatedEmail.class })
public @interface EmailValidator {
    String message() default "Invalid email format";

//    long value();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
