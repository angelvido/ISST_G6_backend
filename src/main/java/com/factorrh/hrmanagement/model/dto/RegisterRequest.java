package com.factorrh.hrmanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message = "Lastname cannot be blank")
        String lastname,
        @NotBlank(message = "Job cannot be blank")
        String job,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, max = 20, message = "Size of password must be between 6 and 20 characters")
        String password
) {
}
