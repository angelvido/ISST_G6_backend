package com.factorrh.hrmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "payroll", schema = "public")
public class Payroll {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @Column(name = "horas")
    private BigDecimal hours;

    @Column(name = "salario_bruto")
    private BigDecimal grossSalary;

    @Column(name = "porcentaje_retencion_irpf")
    private BigDecimal irpfRetentionPercentage;

    @Column(name = "retencion_irpf")
    private BigDecimal irpfRetention;

    @Column(name = "salario_neto")
    private BigDecimal netSalary;

    @Column(name = "fecha_inicio")
    private LocalDate startDate;

    @Column(name = "fecha_fin")
    private LocalDate endDate;
}