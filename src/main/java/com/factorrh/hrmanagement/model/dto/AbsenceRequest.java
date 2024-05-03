package com.factorrh.hrmanagement.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AbsenceRequest(
        @NotNull(message = "Employee ID cannot be null")
        UUID employeeId,
        @NotBlank(message = "Type of leave cannot be blank")
        String type,
        @FutureOrPresent(message = "Start date must be in the present or future")
        LocalDate startDate,
        @NotNull(message = "End date cannot be null")
        LocalDate endDate,
        boolean approval
) {
}