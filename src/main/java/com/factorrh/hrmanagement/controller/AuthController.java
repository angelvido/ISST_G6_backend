package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.exception.AuthenticationException;
import com.factorrh.hrmanagement.model.dto.LoginRequest;
import com.factorrh.hrmanagement.model.dto.LoginResponse;
import com.factorrh.hrmanagement.model.dto.RegisterRequest;
import com.factorrh.hrmanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    AuthService authService;

    @Autowired
    public AuthController(AuthService employeeService) {
        this.authService = employeeService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest requestDto) {
        authService.register(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest requestDto) {
        try {
            UUID employeeId = authService.login(requestDto);
            LoginResponse response = new LoginResponse(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
