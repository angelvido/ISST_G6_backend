package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.model.dto.RegisterRequest;
import com.factorrh.hrmanagement.repository.EmployeeRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository repository;
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void register(RegisterRequest request) {
        String username = request.username();
        String name = request.name();
        String lastname = request.lastname();
        LocalDate admission = LocalDate.now();
        String job = request.job();
        String password = request.password();       //TODO password debería de encriptarse
        Optional<Employee> existingEmployee = repository.findByUsername(username);
        if (existingEmployee.isPresent()) {
            throw new DuplicateKeyException(String.format("Employee with username '%s' already exists.", username));
        }
        Employee employee = Employee.builder()
                .username(username)
                .name(name)
                .lastname(lastname)
                .admission(admission)
                .job(job)
                .password(password)
                .build();
        repository.save(employee);
    }
}
