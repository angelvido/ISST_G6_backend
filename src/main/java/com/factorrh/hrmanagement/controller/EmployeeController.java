package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.model.dto.AbsenceRequest;
import com.factorrh.hrmanagement.model.dto.EmployeeRequest;
import com.factorrh.hrmanagement.model.dto.DataResponse;
import com.factorrh.hrmanagement.service.AbsenceService;
import com.factorrh.hrmanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    EmployeeService employeeService;
    AbsenceService absenceService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, AbsenceService absenceService) {
        this.employeeService = employeeService;
        this.absenceService = absenceService;
    }

    @GetMapping("/data")
    public ResponseEntity<DataResponse> getData(@Valid @RequestBody EmployeeRequest requestDto) {
        DataResponse dataResponse = employeeService.data(requestDto);
        if (dataResponse != null) {
            return ResponseEntity.ok(dataResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getEmployees")
    public List<Employee> getEmployees(@Valid @RequestBody EmployeeRequest requestDto) {
        return employeeService.getEmployees(requestDto);
    }

    @PostMapping("/absence")
    public ResponseEntity<Void> registerAbsence(@Valid @RequestBody AbsenceRequest requestDto) {
        absenceService.createAbsence(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/absences")
    public ResponseEntity<List<Absence>> getAllAbsences(@Valid @RequestBody EmployeeRequest requestDto) {
        List<Absence> absences = absenceService.getAbsencesByEmployeeId(requestDto.employeeId());
        return ResponseEntity.ok(absences);
    }

    @PatchMapping("/absence/{id}")
    public ResponseEntity<Void> updateAbsence(@Valid @RequestBody AbsenceRequest requestDto, @PathVariable UUID id) {
        absenceService.updateAbsenceRequest(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
