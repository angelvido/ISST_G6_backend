package com.factorrh.hrmanagement.model.dto;

import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleRequest(
        @NotNull(message = "Local Date cannot be null")
        LocalDate date,

        @NotNull(message = "Day of week cannot be null")
        DayOfWeek dayOfWeek,

        @NotNull(message = "Start time cannot be null")
        LocalTime startTime,

        @NotNull(message = "End time cannot be null")
        LocalTime endTime
) {
}