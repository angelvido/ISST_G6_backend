package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Candidate;
import com.factorrh.hrmanagement.entity.Offer;
import com.factorrh.hrmanagement.model.dto.CandidateRequest;
import com.factorrh.hrmanagement.model.dto.IDRequest;
import com.factorrh.hrmanagement.model.dto.OfferRequest;
import com.factorrh.hrmanagement.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterController {
    private final OfferService offerService;

    @Autowired
    public RecruiterController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/candidates/{offerId}")
    public ResponseEntity<Optional<List<Candidate>>> getCandidatesForOffer(@PathVariable UUID offerId, @RequestBody @Valid IDRequest request) {
        Optional<List<Candidate>> candidates = offerService.getCandidatesForOffer(offerId, request.Id());
        return ResponseEntity.ok(candidates);
    }

    @PostMapping("/offer")
    public ResponseEntity<Void> addOffer(@RequestBody OfferRequest request) {
        offerService.addOffer(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/candidate")
    public ResponseEntity<Void> addCandidate(@Valid @RequestBody CandidateRequest request) {
        offerService.addCandidate(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/offers")
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offerService.getAllOffers();
        return ResponseEntity.ok(offers);
    }
}