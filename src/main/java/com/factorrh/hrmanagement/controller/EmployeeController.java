package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.model.dto.EmployeeRequest;
import com.factorrh.hrmanagement.model.dto.DataResponse;
import com.factorrh.hrmanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
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
}
