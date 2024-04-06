package com.factorrh.hrmanagement.repository;

import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
}