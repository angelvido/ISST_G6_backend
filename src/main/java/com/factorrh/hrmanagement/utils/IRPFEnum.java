package com.factorrh.hrmanagement.utils;

import java.math.BigDecimal;

public enum IRPFEnum {
    Tramo1(BigDecimal.valueOf(12450), BigDecimal.valueOf(0.19)),
    Tramo2(BigDecimal.valueOf(20199), BigDecimal.valueOf(0.24)),
    Tramo3(BigDecimal.valueOf(35199), BigDecimal.valueOf(0.3)),
    Tramo4(BigDecimal.valueOf(59999), BigDecimal.valueOf(0.37)),
    Tramo5(BigDecimal.valueOf(299999), BigDecimal.valueOf(0.45)),
    Tramo6(null, BigDecimal.valueOf(0.47));

    private final BigDecimal limite;
    private final BigDecimal porcentaje;

    IRPFEnum(BigDecimal limite, BigDecimal porcentaje) {
        this.limite = limite;
        this.porcentaje = porcentaje;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }
}
