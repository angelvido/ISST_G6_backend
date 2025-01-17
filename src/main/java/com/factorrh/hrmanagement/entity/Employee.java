package com.factorrh.hrmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "employee", schema = "public")
public class Employee {
    @Id
    @UuidGenerator
    @Column(name = "employee_id", unique = true)
    private UUID employeeID;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "admission")
    private LocalDate admission;
    @Column(name = "job")
    private String job;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @OneToOne(mappedBy = "employee")
    @JsonBackReference
    private HRManager hrManager;
    @OneToOne(mappedBy = "employee")
    @JsonBackReference
    private Controller controller;
    @OneToOne(mappedBy = "employee")
    @JsonBackReference
    private Recruiter recruiter;
}
