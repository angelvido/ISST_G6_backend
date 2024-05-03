package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.model.dto.DataResponse;
import com.factorrh.hrmanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public DataResponse data(Employee employee) {
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
    }

    @Transactional
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
}
