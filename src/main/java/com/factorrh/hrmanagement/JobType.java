package com.factorrh.hrmanagement;

import java.math.BigDecimal;

public enum JobType {
    HRManager(BigDecimal.valueOf(30)),
    Controller(BigDecimal.valueOf(20)),
    Recruiter(BigDecimal.valueOf(15)),
    Default(BigDecimal.valueOf(10))
    ;

    private final BigDecimal hourlyRate;

    JobType(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }
}