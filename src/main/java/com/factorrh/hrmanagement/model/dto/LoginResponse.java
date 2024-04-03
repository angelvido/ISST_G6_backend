package com.factorrh.hrmanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record LoginResponse(
        @NotBlank(message = "EmployeeId cannot be blank")
        UUID employeeId
) {
}
