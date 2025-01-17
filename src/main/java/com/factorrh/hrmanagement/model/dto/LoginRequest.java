package com.factorrh.hrmanagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, max = 20, message = "Size of password must be between 6 and 20 characters")
        String password
) {
}
