package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Candidate;
import com.factorrh.hrmanagement.entity.Offer;
import com.factorrh.hrmanagement.entity.Recruiter;
import com.factorrh.hrmanagement.model.dto.CandidateRequest;
import com.factorrh.hrmanagement.model.dto.OfferRequest;
import com.factorrh.hrmanagement.repository.CandidateRepository;
import com.factorrh.hrmanagement.repository.OfferRepository;
import com.factorrh.hrmanagement.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferService {
    private final CandidateRepository candidateRepository;
    private final OfferRepository offerRepository;
    private final RecruiterRepository recruiterRepository;

    @Autowired
    public OfferService(CandidateRepository candidateRepository, OfferRepository offerRepository, RecruiterRepository recruiterRepository) {
        this.candidateRepository = candidateRepository;
        this.offerRepository = offerRepository;
        this.recruiterRepository = recruiterRepository;
    }

    public Optional<List<Candidate>> getCandidatesForOffer(UUID offerId, UUID recruiterId) {
        Optional<Recruiter> recruiter = recruiterRepository.findById(recruiterId);
        if (recruiter.isPresent()) {
            Optional<Offer> offer = offerRepository.findById(offerId);
            if (offer.isPresent()) {
                return candidateRepository.findByOffer(offer.get());
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public void addOffer(OfferRequest request) {
        Recruiter recruiter = recruiterRepository.findById(request.recruiterId())
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        Offer offer = Offer.builder()
                .position(request.position())
                .requirements(request.requirements())
                .recruiter(recruiter)
                .build();
        offerRepository.save(offer);
    }

    public void addCandidate(CandidateRequest request) {
        Optional<Offer> optionalOffer = offerRepository.findById(request.offerId());
        if (optionalOffer.isPresent()) {
            Candidate candidateBuilder = Candidate.builder()
                    .name(request.name())
                    .lastname(request.lastname())
                    .experience(request.experience())
                    .offer(optionalOffer.get())
                    .build();
            candidateRepository.save(candidateBuilder);
        } else {
            throw  new RuntimeException("Offer with the offerId requested doesn't exists.");
        }
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }
}