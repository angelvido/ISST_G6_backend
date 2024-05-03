package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.entity.Payroll;
import com.factorrh.hrmanagement.entity.Schedule;
import com.factorrh.hrmanagement.model.dto.AbsenceRequest;
import com.factorrh.hrmanagement.model.dto.DataResponse;
import com.factorrh.hrmanagement.model.dto.IDRequest;
import com.factorrh.hrmanagement.model.dto.ScheduleRequest;
import com.factorrh.hrmanagement.repository.EmployeeRepository;
import com.factorrh.hrmanagement.service.AbsenceService;
import com.factorrh.hrmanagement.service.EmployeeService;
import com.factorrh.hrmanagement.service.PayrollService;
import com.factorrh.hrmanagement.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final AbsenceService absenceService;
    private final ScheduleService scheduleService;
    private final PayrollService payrollService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository, AbsenceService absenceService, ScheduleService scheduleService, PayrollService payrollService) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.absenceService = absenceService;
        this.scheduleService = scheduleService;
        this.payrollService = payrollService;
    }

    @GetMapping("/data")
    public ResponseEntity<DataResponse> getData(@Valid @RequestBody IDRequest requestDto) {
        DataResponse dataResponse = employeeService.data(requestDto);
        if (dataResponse != null) {
            return ResponseEntity.ok(dataResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getEmployees")
    public List<Employee> getEmployees(@Valid @RequestBody IDRequest requestDto) {
        return employeeService.getEmployees(requestDto);
    }

    @PostMapping("/absence")
    public ResponseEntity<Void> registerAbsence(@Valid @RequestBody AbsenceRequest requestDto) {
        absenceService.createAbsence(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/absences")
    public ResponseEntity<List<Absence>> getAllAbsences(@Valid @RequestBody IDRequest requestDto) {
        List<Absence> absences = absenceService.getAbsencesByEmployeeId(requestDto.Id());
        return ResponseEntity.ok(absences);
    }

    @PatchMapping("/absence/{id}")
    public ResponseEntity<Void> updateAbsence(@Valid @RequestBody AbsenceRequest requestDto, @PathVariable UUID id) {
        absenceService.updateAbsenceRequest(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/schedule/{employeeId}")
    public ResponseEntity<Void> registerSchedule(@PathVariable UUID employeeId, @Valid @RequestBody ScheduleRequest requestDto) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if (employeeOptional.isPresent()) {
            scheduleService.createSchedule(employeeId, requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<Schedule>> getSchedule(@Valid @RequestBody IDRequest requestDto) {
        List<Schedule> schedules = scheduleService.getSchedulesByEmployeeId(requestDto.Id());
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/payrolls/{id}")
    public ResponseEntity<List<Payroll>> getPayrollsByEmployeeId(@PathVariable UUID id) {
        List<Payroll> payrolls = payrollService.getPayrollsByEmployeeId(id);
        if (!payrolls.isEmpty()) {
            return ResponseEntity.ok(payrolls);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}