package com.factorrh.hrmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "candidate", schema = "public")
public class Candidate {
    @Id
    @UuidGenerator
    @Column(name = "candidate_id", unique = true)
    private UUID candidateID;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
    @Column(name = "experience")
    private String experience;
}