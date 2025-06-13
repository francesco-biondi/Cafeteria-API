package com.progra3.cafeteria_api.validation;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.enums.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEmployeeRequestValidator implements ConstraintValidator<ValidEmployeeRequest, EmployeeRequestDTO> {

    @Override
    public boolean isValid(EmployeeRequestDTO dto, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (dto.role() != null && dto.role().equals(Role.OWNER)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("OWNER role can only be assigned when creating a business")
                    .addPropertyNode("role")
                    .addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
