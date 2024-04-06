package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.entity.HRManager;
import com.factorrh.hrmanagement.model.dto.AbsenceRequest;
import com.factorrh.hrmanagement.model.dto.HRRequest;
import com.factorrh.hrmanagement.repository.AbsenceRepository;
import com.factorrh.hrmanagement.repository.EmployeeRepository;
import com.factorrh.hrmanagement.repository.HRManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final HRManagerRepository hrManagerRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository, HRManagerRepository hrManagerRepository, EmployeeRepository employeeRepository) {
        this.absenceRepository = absenceRepository;
        this.hrManagerRepository = hrManagerRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Absence> getAllAbsences(HRRequest request) {
        Optional<HRManager> existingHRManager = hrManagerRepository.findById(request.HRId());
        if (existingHRManager.isPresent()) {
            return absenceRepository.findAll();
        } else {
            return null;
        }
    }

    public void createAbsence(AbsenceRequest request) {
        UUID employeeId = request.employeeId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee with ID '" + employeeId + "' does not exist."));

        LocalDate startDate = request.startDate();
        LocalDate endDate = request.endDate();
        String type = request.type();

        Absence absence = Absence.builder()
                .employee(employee) // Use the found Employee entity
                .type(type)
                .startDate(startDate)
                .endDate(endDate)
                .approval(false)
                .build();
        absenceRepository.save(absence);
    }

    //TODO Hay que cambiar ligeramente estos 3 metodos siguientes para que solo el HRManager pueda modificarlos
    public Absence getAbsenceById(UUID id) {
        return absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found with id: " + id));
    }

    public void updateAbsence(UUID id, AbsenceRequest updatedAbsence) {
        Absence existingAbsence = getAbsenceById(id);
        existingAbsence.setType(updatedAbsence.type());
        existingAbsence.setStartDate(updatedAbsence.startDate());
        existingAbsence.setEndDate(updatedAbsence.endDate());
        existingAbsence.setApproval(updatedAbsence.approval());
        absenceRepository.save(existingAbsence);
    }

    public void deleteAbsence(UUID id) {
        absenceRepository.deleteById(id);
    }

    public List<Absence> getAbsencesByEmployeeId(UUID employeeId) {
        return absenceRepository.findByEmployeeEmployeeID(employeeId);
    }

    public void updateAbsenceRequest(UUID absenceId, AbsenceRequest updatedAbsence) {
        Optional<Absence> absence = absenceRepository.findById(absenceId);
        if (absence.isPresent() && absence.get().getEmployee().getEmployeeID().equals(updatedAbsence.employeeId())) {
            Absence existingAbsence = absence.get();

            existingAbsence.setStartDate(updatedAbsence.startDate());
            existingAbsence.setEndDate(updatedAbsence.endDate());
            existingAbsence.setType(updatedAbsence.type());
            existingAbsence.setApproval(false);
            absenceRepository.save(existingAbsence);
        } else {
            throw new IllegalArgumentException("Cannot update absence. Absence doesn't exists or employee ID does not match.");
        }
    }
}