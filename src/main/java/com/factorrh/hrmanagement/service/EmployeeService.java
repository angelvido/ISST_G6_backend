package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.model.dto.EmployeeRequest;
import com.factorrh.hrmanagement.model.dto.DataResponse;
import com.factorrh.hrmanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository repository;
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public DataResponse data(EmployeeRequest request) {
        UUID employeeId = request.employeeId();
        Optional<Employee> existingEmployee = repository.findById(employeeId);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            return new DataResponse(
                    employee.getEmployeeID(),
                    employee.getUsername(),
                    employee.getName(),
                    employee.getLastname(),
                    employee.getAdmission(),
                    employee.getJob(),
                    employee.getHrManager(),
                    employee.getController(),
                    employee.getRecruiter()
            );

        } else {
            return null;
        }
    }

    @Transactional
    public List<Employee> getEmployees(EmployeeRequest request) {
        UUID employeeId = request.employeeId();
        Optional<Employee> existingEmployee = repository.findById(employeeId);
        if (existingEmployee.isPresent()) {
            return repository.findAll();
        } else {
            return Collections.emptyList();
        }
    }
}
