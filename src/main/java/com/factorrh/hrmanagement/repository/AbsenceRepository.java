package com.factorrh.hrmanagement.repository;

import com.factorrh.hrmanagement.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, UUID> {
    List<Absence> findByEmployeeEmployeeID(UUID employeeId);
}