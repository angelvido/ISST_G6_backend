package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.entity.HRManager;
import com.factorrh.hrmanagement.model.dto.HRRequest;
import com.factorrh.hrmanagement.repository.AbsenceRepository;
import com.factorrh.hrmanagement.repository.EmployeeRepository;
import com.factorrh.hrmanagement.repository.HRManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Boolean createAbsence(Absence absence) {
        Optional<Employee> existingEmployee = employeeRepository.findById(absence.getEmployeeId());
        if (existingEmployee.isPresent()) {
            absenceRepository.save(absence);
            return true;
        } else {
            return false;
        }
    }

    //TODO Hay que cambiar ligeramente estos 3 metodos para que solo el HRManager pueda modificarlos
    public Absence getAbsenceById(UUID id) {
        return absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found with id: " + id));
    }

    public void updateAbsence(UUID id, Absence updatedAbsence) {
        Absence existingAbsence = getAbsenceById(id);
        existingAbsence.setType(updatedAbsence.getType());
        existingAbsence.setStartDate(updatedAbsence.getStartDate());
        existingAbsence.setEndDate(updatedAbsence.getEndDate());
        existingAbsence.setApproval(updatedAbsence.isApproval());
        absenceRepository.save(existingAbsence);
    }

    public void deleteAbsence(UUID id) {
        absenceRepository.deleteById(id);
    }
}