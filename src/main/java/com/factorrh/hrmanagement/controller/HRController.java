package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.entity.HRManager;
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

    //Query de este metodo: http://localhost:8080/api/manager/absences?managerId={id del hrmanager}
    @GetMapping("/absences")
    public ResponseEntity<List<Absence>> getAllAbsences(@RequestParam UUID managerId) {
        try {
            if (managerId == null) {
                return ResponseEntity.badRequest().build();
            }
            Optional<HRManager> existingHRManager = hrManagerRepository.findById(managerId);
            if (existingHRManager.isPresent()) {
                List<Absence> response = absenceService.getAllAbsences();
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Query de este metodo: http://localhost:8080/api/manager/absence/{id de la ausencia}?managerId={id del hrmanager}
    @GetMapping("/absence/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable UUID id, @RequestParam UUID managerId) {
        try {
            if (managerId == null) {
                return ResponseEntity.badRequest().build();
            }
            Optional<HRManager> existingHRManager = hrManagerRepository.findById(managerId);
            if (existingHRManager.isPresent()) {
                Absence response = absenceService.getAbsenceById(id);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Query de este metodo: http://localhost:8080/api/manager/absence/{id de la ausencia}?managerId={id del hrmanager}
    @PutMapping("/absence/{id}")
    public ResponseEntity<Absence> changeApproval(@PathVariable UUID id, @RequestParam UUID managerId) {
        try {
            if (managerId == null) {
                return ResponseEntity.badRequest().build();
            }
            Optional<HRManager> existingHRManager = hrManagerRepository.findById(managerId);
            if (existingHRManager.isPresent()) {
                Absence response = absenceService.changeApproval(id);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Query de este metodo: http://localhost:8080/api/manager/absence/{id de la ausencia}?managerId={id del hrmanager}
    @DeleteMapping("/absence/{id}")
    public ResponseEntity<Object> deleteAbsence(@PathVariable UUID id, @RequestParam UUID managerId) {
        try {
            if (managerId == null) {
                return ResponseEntity.badRequest().build();
            }
            Optional<HRManager> existingHRManager = hrManagerRepository.findById(managerId);
            if (existingHRManager.isPresent()) {
                absenceService.deleteAbsence(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Query de este metodo: http://localhost:8080/api/manager/payrolls/HRManager
    //Body de este metodo: {
    //  "hrId": "id del manager",
    //  "startDate": "2024-05-01",
    //  "endDate": "2024-05-30"
    //}
    @PostMapping("/payrolls/{id}")
    public ResponseEntity<Void> generatePayrolls(@PathVariable String id, @RequestBody @Valid PayrollRequest request) {
        UUID hrManagerId = request.hrId();
        Optional<HRManager> hrManagerOptional = hrManagerRepository.findById(hrManagerId);
        if (hrManagerOptional.isPresent()) {
            List<Employee> employees = employeeService.getEmployees();
            payrollService.generatePayrolls(employees, request.startDate(), request.endDate());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}