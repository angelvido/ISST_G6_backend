package com.factorrh.hrmanagement.repository;

import com.factorrh.hrmanagement.entity.HRManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HRManagerRepository extends JpaRepository<HRManager, UUID> {
    Optional<HRManager> findByEmployee_EmployeeID(UUID employeeId);
}
