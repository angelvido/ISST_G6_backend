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
@Table(name = "recruiter", schema = "public")
public class Recruiter {
    @Id
    @UuidGenerator
    @Column(name = "recruiter_id", unique = true)
    private UUID recruiterId;
    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;
}
