package com.progra3.cafeteria_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidEmployeeRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmployeeRequest {
    String message() default "EmployeeRequestDTO is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
