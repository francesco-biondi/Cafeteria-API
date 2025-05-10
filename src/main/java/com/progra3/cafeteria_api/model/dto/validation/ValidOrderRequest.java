package com.progra3.cafeteria_api.model.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidOrderRequestValidator.class)
@Documented
public @interface ValidOrderRequest {
    String message() default "Invalid order request based on order type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}