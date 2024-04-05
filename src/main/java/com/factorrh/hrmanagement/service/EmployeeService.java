package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.model.dto.EmployeeRequest;
import com.factorrh.hrmanagement.model.dto.DataResponse;
import com.factorrh.hrmanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

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
                    employee.getJob()
            );
        } else {
            return null;
        }
    }
}
