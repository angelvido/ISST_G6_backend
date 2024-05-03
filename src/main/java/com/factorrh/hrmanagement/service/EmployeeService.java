package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.model.dto.DataResponse;
import com.factorrh.hrmanagement.model.dto.IDRequest;
import com.factorrh.hrmanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public DataResponse data(IDRequest request) {
        UUID employeeId = request.Id();
        Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);
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
    public List<Employee> getEmployees(IDRequest request) {
        UUID employeeId = request.Id();
        Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);
        if (existingEmployee.isPresent()) {
            return employeeRepository.findAll();
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
