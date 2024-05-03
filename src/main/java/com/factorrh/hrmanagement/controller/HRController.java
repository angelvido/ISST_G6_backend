package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.entity.HRManager;
import com.factorrh.hrmanagement.model.dto.AbsenceRequest;
import com.factorrh.hrmanagement.model.dto.IDRequest;
import com.factorrh.hrmanagement.model.dto.PayrollRequest;
import com.factorrh.hrmanagement.repository.HRManagerRepository;
import com.factorrh.hrmanagement.service.AbsenceService;
import com.factorrh.hrmanagement.service.EmployeeService;
import com.factorrh.hrmanagement.service.PayrollService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/manager")
public class HRController {

    private final AbsenceService absenceService;
    private final EmployeeService employeeService;
    private final PayrollService payrollService;
    private final HRManagerRepository hrManagerRepository;

    @Autowired
    public HRController(AbsenceService absenceService, EmployeeService employeeService, PayrollService payrollService, HRManagerRepository hrManagerRepository) {
        this.absenceService = absenceService;
        this.employeeService = employeeService;
        this.payrollService = payrollService;
        this.hrManagerRepository = hrManagerRepository;
    }

    @GetMapping("/absences")
    public List<Absence> getAllAbsences(@Valid @RequestBody IDRequest request) {
        return absenceService.getAllAbsences(request);
    }

    //TODO Configurar los tres siguientes metodos del controlador para que se compruebe el inicio de sesion del HRManager
    @GetMapping("/absence/{id}")
    public Absence getAbsenceById(@PathVariable UUID id) {
        return absenceService.getAbsenceById(id);
    }


    @PatchMapping("/absence/{id}")
    public void updateAbsence(@PathVariable UUID id, @RequestBody AbsenceRequest absence) {
        absenceService.updateAbsence(id, absence);
    }

    @DeleteMapping("/absence/{id}")
    public void deleteAbsence(@PathVariable UUID id) {
        absenceService.deleteAbsence(id);
    }

    @PostMapping("/payrolls/{id}")
    public ResponseEntity<Void> generatePayrolls(@PathVariable String id, @RequestBody @Valid PayrollRequest request) {
        UUID hrManagerId = request.hrId();
        Optional<HRManager> hrManagerOptional = hrManagerRepository.findById(hrManagerId);
        if (hrManagerOptional.isPresent()) {
            List<Employee> employees = employeeService.getAllEmployees();
            payrollService.generatePayrolls(employees, request.startDate(), request.endDate());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}