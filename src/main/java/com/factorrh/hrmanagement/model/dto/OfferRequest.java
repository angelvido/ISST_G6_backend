package com.factorrh.hrmanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record OfferRequest(
        @NotBlank(message = "Position cannot be blank")
        String position,
        @NotBlank(message = "Requirements cannot be blank")
        String requirements,
        @NotNull(message = "Recruiter ID cannot be null")
        UUID recruiterId
) {
}