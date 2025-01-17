package com.factorrh.hrmanagement.model.dto;

import com.factorrh.hrmanagement.entity.Controller;
import com.factorrh.hrmanagement.entity.HRManager;
import com.factorrh.hrmanagement.entity.Recruiter;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public record DataResponse(
        UUID employeeId,
        @NotBlank(message = "Username cannot be blank")
        String username,
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message = "Lastname cannot be blank")
        String lastname,
        @NotBlank(message = "Admission cannot be blank")
        LocalDate admission,
        @NotBlank(message = "Job cannot be blank")
        String job,
        HRManager hrManager,
        Controller controller,
        Recruiter recruiter

) {
}
