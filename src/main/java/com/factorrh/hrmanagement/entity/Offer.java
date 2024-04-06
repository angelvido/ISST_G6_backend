package com.factorrh.hrmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "offer", schema = "public")
public class Offer {
    @Id
    @UuidGenerator
    @Column(name = "offer_id", unique = true)
    private UUID offerID;
    @Column(name = "position")
    private String position;
    @Column(name = "requirements")
    private String requirements;
    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    @JsonIgnore
    private Recruiter recruiter;
    @OneToMany(mappedBy = "offer")
    @JsonBackReference
    private List<Candidate> candidates;
}