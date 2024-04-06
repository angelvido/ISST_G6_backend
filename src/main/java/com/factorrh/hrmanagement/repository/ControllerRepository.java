package com.factorrh.hrmanagement.repository;

import com.factorrh.hrmanagement.entity.Controller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ControllerRepository extends JpaRepository<Controller, UUID> {
    Optional<Controller> findByEmployee_EmployeeID(UUID employeeId);
}
