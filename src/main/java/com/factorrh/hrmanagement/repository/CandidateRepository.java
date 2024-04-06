package com.factorrh.hrmanagement.repository;

import com.factorrh.hrmanagement.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.factorrh.hrmanagement.entity.Candidate;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
    Optional<List<Candidate>> findByOffer(Offer offer);
}