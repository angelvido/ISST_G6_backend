package com.factorrh.hrmanagement.repository;

import com.factorrh.hrmanagement.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, UUID> {
    List<Payroll> findByEmployee_EmployeeID(UUID employeeId);
}