package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.entity.Payroll;
import com.factorrh.hrmanagement.entity.Schedule;
import com.factorrh.hrmanagement.model.dto.AbsenceRequest;
import com.factorrh.hrmanagement.model.dto.DataResponse;
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

    //Query de este metodo: http://localhost:8080/api/employees/data?employeeId={id del empleado}
    @GetMapping("/data")
    public ResponseEntity<DataResponse> getData(@RequestParam UUID employeeId) {
        try {
            if (employeeId == null) {
                return ResponseEntity.badRequest().build();
            }
            Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);
            if (existingEmployee.isPresent()) {
                Employee employee = existingEmployee.get();
                DataResponse dataResponse = employeeService.data(employee);
                return ResponseEntity.ok(dataResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Query de este método: http://localhost:8080/api/employees/getEmployees?employeeId={id del empleado}
    @GetMapping("/getEmployees")
    public ResponseEntity<List<Employee>> getEmployees(@RequestParam UUID employeeId) {
        try {
            if (employeeId == null) {
                return ResponseEntity.badRequest().build();
            }
            Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);
            if (existingEmployee.isPresent()) {
                List<Employee> response = employeeService.getEmployees();
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Query de este metodo: http://localhost:8080/api/employees/absence
    //Body de este metodo: {
    //    "employeeId" : "id del empleado",
    //    "type" : "Vacaciones",
    //    "startDate" : "2024-06-15",
    //    "endDate" : "2024-06-30"
    //}
    @PostMapping("/absence")
    public ResponseEntity<Void> registerAbsence(@Valid @RequestBody AbsenceRequest requestDto) {
        absenceService.createAbsence(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //Query de este metodo: http://localhost:8080/api/employees/absences?employeeId={id del empleado}
    @GetMapping("/absences")
    public ResponseEntity<List<Absence>> getAllAbsences(@RequestParam UUID employeeId) {
        List<Absence> absences = absenceService.getAbsencesByEmployeeId(employeeId);
        return ResponseEntity.ok(absences);
    }

    //Query de este metodo: http://localhost:8080/api/employees/absence/{id de la ausencia}
    //Body de este metodo: {
    //    "employeeId" : "id del empleado",
    //    "type" : "Vacaciones",
    //    "startDate" : "2024-06-17",
    //    "endDate" : "2024-06-30"
    //}
    @PatchMapping("/absence/{id}")
    public ResponseEntity<Absence> updateAbsence(@Valid @RequestBody AbsenceRequest requestDto, @PathVariable UUID id) {
        Absence response = absenceService.updateAbsence(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //Query de este metodo: http://localhost:8080/api/employees/schedule/{id del empleado}
    //Body de este metodo: {
    //  "date": "2024-05-03",
    //  "dayOfWeek": "MONDAY",
    //  "startTime": "08:00",
    //  "endTime": "17:00"
    //}
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

    //Query de este metodo: http://localhost:8080/api/employees/schedules?employeeId={id del empleado}
    @GetMapping("/schedules")
    public ResponseEntity<List<Schedule>> getSchedule(@RequestParam UUID employeeId) {
        List<Schedule> schedules = scheduleService.getSchedulesByEmployeeId(employeeId);
        return ResponseEntity.ok(schedules);
    }

    //Query de este metodo: http://localhost:8080/api/employees/payrolls?employeeId={id del empleado}
    @GetMapping("/payrolls")
    public ResponseEntity<List<Payroll>> getPayrollsByEmployeeId(@RequestParam UUID employeeId) {
        List<Payroll> payrolls = payrollService.getPayrollsByEmployeeId(employeeId);
        if (!payrolls.isEmpty()) {
            return ResponseEntity.ok(payrolls);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}