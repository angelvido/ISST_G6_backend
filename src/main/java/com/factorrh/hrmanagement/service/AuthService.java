package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.exception.AuthenticationException;
import com.factorrh.hrmanagement.model.dto.LoginRequest;
import com.factorrh.hrmanagement.model.dto.RegisterRequest;
import com.factorrh.hrmanagement.repository.EmployeeRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private final EmployeeRepository repository;
    public AuthService(EmployeeRepository repository) {
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

    @Transactional
    public UUID login(LoginRequest request) {
        String username = request.username();
        String password = request.password();
        Optional<Employee> existingEmployee = repository.findByUsername(username);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            String storedPassword = employee.getPassword();

            if (Objects.equals(storedPassword, password)) {     //TODO Hay que cambiar para que la contraseña se decodifique y demas
                return employee.getEmployeeID();
            } else {
                throw new AuthenticationException("Username or password incorrect.");
            }
        } else {
            throw new DuplicateKeyException(String.format("Employee with username '%s' already exists.", username));
        }
    }
}
