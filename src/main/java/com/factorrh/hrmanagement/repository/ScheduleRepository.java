package com.factorrh.hrmanagement.repository;

import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    List<Schedule> findByEmployeeEmployeeID(UUID employeeId);
    List<Schedule> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
    // Otra manera de hacerlo seria esta
//    @Query("SELECT s FROM Schedule s WHERE s.employee = :employee AND s.date BETWEEN :startDate AND :endDate")
//    List<Schedule> findByEmployeeAndDateBetween(@Param("employee") Employee employee, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}