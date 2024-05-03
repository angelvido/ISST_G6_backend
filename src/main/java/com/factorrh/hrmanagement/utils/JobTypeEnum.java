package com.factorrh.hrmanagement.utils;

import java.math.BigDecimal;

public enum JobTypeEnum {
    HRManager(BigDecimal.valueOf(30), BigDecimal.valueOf(64800)),
    Controller(BigDecimal.valueOf(20), BigDecimal.valueOf(43200)),
    Recruiter(BigDecimal.valueOf(15), BigDecimal.valueOf(32400)),
    Default(BigDecimal.valueOf(10), BigDecimal.valueOf(21600))
    ;

    private final BigDecimal hourlyRate;
    private final BigDecimal annualSalaryRate;

    JobTypeEnum(BigDecimal hourlyRate, BigDecimal annualSalaryRate) {
        this.hourlyRate = hourlyRate;
        this.annualSalaryRate = annualSalaryRate;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public BigDecimal getAnnualSalaryRate() {return annualSalaryRate;}

}