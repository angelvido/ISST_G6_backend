package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.model.dto.AbsenceRequest;
import com.factorrh.hrmanagement.repository.AbsenceRepository;
import com.factorrh.hrmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository, EmployeeRepository employeeRepository) {
        this.absenceRepository = absenceRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }

    @Transactional
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

    public Absence getAbsenceById(UUID id) {
        return absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found with id: " + id));
    }

    @Transactional
    public Absence updateAbsence(UUID id, AbsenceRequest updatedAbsence) {
        Optional<Absence> absence = absenceRepository.findById(id);
        if (absence.isPresent() && absence.get().getEmployee().getEmployeeID().equals(updatedAbsence.employeeId())) {
            Absence existingAbsence = getAbsenceById(id);
            existingAbsence.setType(updatedAbsence.type());
            existingAbsence.setStartDate(updatedAbsence.startDate());
            existingAbsence.setEndDate(updatedAbsence.endDate());
            existingAbsence.setApproval(updatedAbsence.approval());
            absenceRepository.save(existingAbsence);
            return existingAbsence;
        } else {
            throw new IllegalArgumentException("Cannot update absence. Absence doesn't exists or employee ID does not match.");
        }
    }

    @Transactional
    public Absence changeApproval(UUID id) {
        Optional<Absence> absence = absenceRepository.findById(id);
        if (absence.isPresent()) {
            Absence existingAbsence = getAbsenceById(id);
            existingAbsence.setApproval(!existingAbsence.isApproval());
            absenceRepository.save(existingAbsence);
            return existingAbsence;
        } else {
            throw new IllegalArgumentException("Cannot update absence. Absence doesn't exists.");
        }
    }

    public void deleteAbsence(UUID id) {
        absenceRepository.deleteById(id);
    }

    public List<Absence> getAbsencesByEmployeeId(UUID employeeId) {
        return absenceRepository.findByEmployeeEmployeeID(employeeId);
    }
}