package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.model.dto.HRRequest;
import com.factorrh.hrmanagement.service.AbsenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hrmanager")
public class HRController {

    private final AbsenceService absenceService;

    @Autowired
    public HRController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @GetMapping("/absences")
    public List<Absence> getAllAbsences(HRRequest request) {
        return absenceService.getAllAbsences(request);
    }

    @GetMapping("/absence/{id}")
    public Absence getAbsenceById(@PathVariable UUID id) {
        return absenceService.getAbsenceById(id);
    }

    @PostMapping("/absence")
    public ResponseEntity<Void> createAbsence(@Valid @RequestBody Absence absence) {
        absenceService.createAbsence(absence);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/absence/{id}")
    public void updateAbsence(@PathVariable UUID id, @RequestBody Absence absence) {
        absenceService.updateAbsence(id, absence);
    }

    @DeleteMapping("/absence/{id}")
    public void deleteAbsence(@PathVariable UUID id) {
        absenceService.deleteAbsence(id);
    }
}