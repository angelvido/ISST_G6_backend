package com.factorrh.hrmanagement.model.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CandidateRequest(
        @NotNull(message = "Name cannot be null")
        String name,
        @NotNull(message = "Lastname cannot be null")
        String lastname,
        @NotNull(message = "Offer ID cannot be null")
        UUID offerId,
        @NotNull(message = "Experience cannot be null")
        String experience
) {}