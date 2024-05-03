package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Candidate;
import com.factorrh.hrmanagement.entity.Offer;
import com.factorrh.hrmanagement.model.dto.CandidateRequest;
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

    //Query de este metodo: http://localhost:8080/api/recruiter/candidates/{id de oferta}?recruiterId={id del reclutador}
    @GetMapping("/candidates/{offerId}")
    public ResponseEntity<Optional<List<Candidate>>> getCandidatesForOffer(@PathVariable UUID offerId, @RequestParam UUID recruiterId) {
        Optional<List<Candidate>> candidates = offerService.getCandidatesForOffer(offerId, recruiterId);
        return ResponseEntity.ok(candidates);
    }

    //Query de este metodo: http://localhost:8080/api/recruiter/offer
    //Body de este metodo: {
    //  "position": "Dentista",
    //  "requirements": "5+ años de experiencia en el campo",
    //  "recruiterId": "95fde76d-e7fa-472c-a5e5-bbfea7c58e43"
    //}
    @PostMapping("/offer")
    public ResponseEntity<Void> addOffer(@RequestBody OfferRequest request) {
        offerService.addOffer(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //Query de este metoodo: http://localhost:8080/api/recruiter/candidate
    //Body de este metodo: {
    //  "name": "Manolo",
    //  "lastname": "Garcia",
    //  "experience": "Dentista en Vitaldent 7 años",
    //  "offerId": "03c1496c-c94a-4f6f-8d3e-99ceb24bf293"
    //}
    @PostMapping("/candidate")
    public ResponseEntity<Void> addCandidate(@Valid @RequestBody CandidateRequest request) {
        offerService.addCandidate(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //Query de este metodo: http://localhost:8080/api/recruiter/offers
    @GetMapping("/offers")
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offerService.getAllOffers();
        return ResponseEntity.ok(offers);
    }
}