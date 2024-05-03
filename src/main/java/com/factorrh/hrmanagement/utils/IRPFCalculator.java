package com.factorrh.hrmanagement.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IRPFCalculator {

    private IRPFCalculator() {
    }

    public static BigDecimal calcularRetencionMedia(JobTypeEnum jobTypeEnum) {
        BigDecimal salarioAnual = jobTypeEnum.getAnnualSalaryRate();
        BigDecimal retencionTotal = BigDecimal.ZERO;

        for (IRPFEnum tramo : IRPFEnum.values()) {
            BigDecimal limite = tramo.getLimite();
            BigDecimal porcentaje = tramo.getPorcentaje();

            if (limite == null || salarioAnual.compareTo(limite) <= 0) {
                BigDecimal retencionTramo = salarioAnual.subtract(limite == null ? BigDecimal.ZERO : limite).multiply(porcentaje);
                retencionTotal = retencionTotal.add(retencionTramo);
                break;
            } else {
                BigDecimal salarioEnTramo = salarioAnual.subtract(limite);
                BigDecimal retencionTramo = salarioEnTramo.multiply(porcentaje);
                retencionTotal = retencionTotal.add(retencionTramo);
            }
        }

        return retencionTotal.divide(salarioAnual, 2, RoundingMode.HALF_UP);
    }
}