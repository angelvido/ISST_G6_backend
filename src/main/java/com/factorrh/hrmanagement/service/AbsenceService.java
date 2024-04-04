package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Absence;
import com.factorrh.hrmanagement.entity.HRManager;
import com.factorrh.hrmanagement.model.dto.HRRequest;
import com.factorrh.hrmanagement.model.dto.LoginResponse;
import com.factorrh.hrmanagement.repository.AbsenceRepository;
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

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository, HRManagerRepository hrManagerRepository) {
        this.absenceRepository = absenceRepository;
        this.hrManagerRepository = hrManagerRepository;
    }

    public List<Absence> getAllAbsences(HRRequest request) {
        UUID managerId = request.managerId();
        Optional<HRManager> existingHRManager = hrManagerRepository.findById(managerId);
        if (existingHRManager.isPresent()) {
            return absenceRepository.findAll();
        }  else {
            return new ArrayList<>();
        }
    }

    public Absence getAbsenceById(UUID id) {
        return absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found with id: " + id));
    }

    public Absence createAbsence(Absence absence) {
        return absenceRepository.save(absence);
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