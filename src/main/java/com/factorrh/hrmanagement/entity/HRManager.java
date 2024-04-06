package com.factorrh.hrmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;
}
