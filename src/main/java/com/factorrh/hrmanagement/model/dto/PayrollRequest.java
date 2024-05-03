package com.factorrh.hrmanagement.model.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record PayrollRequest(
        @NotNull(message = "HrManagerId cannot be null")
        UUID hrId,
        @NotNull(message = "Start date cannot be blank")
        LocalDate startDate,
        @NotNull(message = "End date cannot be blank")
        LocalDate endDate
) {
}