package com.factorrh.hrmanagement.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IRPFCalculator {

    private IRPFCalculator() {
    }

    public static BigDecimal calcularRetencionMedia(JobTypeEnum jobTypeEnum) {
        BigDecimal salarioAnual = jobTypeEnum.getAnnualSalaryRate();
        BigDecimal retencionTotal = BigDecimal.ZERO;

        BigDecimal salarioRestante = salarioAnual;

        for (IRPFEnum tramo : IRPFEnum.values()) {
            BigDecimal limite = tramo.getLimite();
            BigDecimal porcentaje = tramo.getPorcentaje();

            if (limite == null || salarioRestante.compareTo(limite) <= 0) {
                BigDecimal retencionTramo = salarioRestante.multiply(porcentaje);
                retencionTotal = retencionTotal.add(retencionTramo);
                break;
            } else {
                BigDecimal retencionTramo = limite.multiply(porcentaje);
                retencionTotal = retencionTotal.add(retencionTramo);
                salarioRestante = salarioRestante.subtract(limite);
            }
        }

        return retencionTotal.divide(salarioAnual, 2, RoundingMode.HALF_UP);
    }
}