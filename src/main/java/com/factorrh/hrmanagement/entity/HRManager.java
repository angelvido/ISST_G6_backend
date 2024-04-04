package com.factorrh.hrmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "manager", schema = "public")
public class HRManager {
    @Id
    @UuidGenerator
    @Column(name = "manager_id", unique = true)
    private UUID managerId;
    @Column(name = "employee_id", unique = true)
    private UUID employeeId;
}