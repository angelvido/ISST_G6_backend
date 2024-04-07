package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.model.dto.AbsenceRequest;
import com.factorrh.hrmanagement.model.dto.IDRequest;
import com.factorrh.hrmanagement.service.AbsenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/manager")
public class HRController {

    private final AbsenceService absenceService;

    @Autowired
    public HRController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @PostMapping("/absences")
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
}