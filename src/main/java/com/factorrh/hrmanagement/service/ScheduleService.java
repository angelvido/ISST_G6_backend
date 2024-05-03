package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Schedule;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.model.dto.ScheduleRequest;
import com.factorrh.hrmanagement.repository.ScheduleRepository;
import com.factorrh.hrmanagement.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
    }

    public void createSchedule(UUID employeeId, ScheduleRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee with ID '" + employeeId + "' does not exist."));

        Schedule schedule = Schedule.builder()
                .employee(employee)
                .date(request.date())
                .dayOfWeek(request.dayOfWeek())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .approval(false)
                .build();

        scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedulesByEmployeeId(UUID employeeId) {
        return scheduleRepository.findByEmployeeEmployeeID(employeeId);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public void updateSchedule(UUID scheduleId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setApproval(!schedule.isApproval());
            scheduleRepository.save(schedule);
        }
    }

    public BigDecimal getHoursWorkedForEmployee(Employee employee, LocalDate startDate, LocalDate endDate) {
        BigDecimal totalHoursWorked = BigDecimal.ZERO;

        List<Schedule> schedules = scheduleRepository.findByEmployeeAndDateBetweenAndApproval(employee, startDate, endDate, true);

        for (Schedule schedule : schedules) {
            BigDecimal hoursWorkedInSchedule = calculateHoursWorkedInSchedule(schedule);
            totalHoursWorked = totalHoursWorked.add(hoursWorkedInSchedule);
        }

        return totalHoursWorked;
    }

    private BigDecimal calculateHoursWorkedInSchedule(Schedule schedule) {
        LocalDateTime startDateTime = LocalDateTime.of(schedule.getDate(), schedule.getStartTime());
        LocalDateTime endDateTime = LocalDateTime.of(schedule.getDate(), schedule.getEndTime());

        long hours = java.time.Duration.between(startDateTime, endDateTime).toHours();

        return BigDecimal.valueOf(hours);
    }
}