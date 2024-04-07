package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.model.dto.AbsenceRequest;
import com.factorrh.hrmanagement.model.dto.DataResponse;
import com.factorrh.hrmanagement.model.dto.IDRequest;
import com.factorrh.hrmanagement.service.AbsenceService;
import com.factorrh.hrmanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/absences")
    public ResponseEntity<List<Absence>> getAllAbsences(@Valid @RequestBody IDRequest requestDto) {
        List<Absence> absences = absenceService.getAbsencesByEmployeeId(requestDto.Id());
        return ResponseEntity.ok(absences);
    }

    @PatchMapping("/absence/{id}")
    public ResponseEntity<Void> updateAbsence(@Valid @RequestBody AbsenceRequest requestDto, @PathVariable UUID id) {
        absenceService.updateAbsenceRequest(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}