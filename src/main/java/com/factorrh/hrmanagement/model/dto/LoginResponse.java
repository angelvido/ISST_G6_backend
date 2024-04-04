package com.factorrh.hrmanagement.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record LoginResponse(
        @NotBlank(message = "EmployeeId cannot be blank")
        UUID employeeId,
        @Nullable
        UUID recruiterId,
        @Nullable
        UUID hrManagerId,
        @Nullable
        UUID controllerId
) {
}
