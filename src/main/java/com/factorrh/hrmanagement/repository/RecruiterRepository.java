package com.factorrh.hrmanagement.repository;

import com.factorrh.hrmanagement.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, UUID> {
    Optional<Recruiter> findByRecruiterId(UUID recruiterId);
    Optional<Recruiter> findByEmployeeId(UUID employeeId);
}
