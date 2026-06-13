package com.act.hospitalmanagementsystem.pharmacy.repository;

import com.act.hospitalmanagementsystem.pharmacy.entity.Dispensing;
import com.act.hospitalmanagementsystem.pharmacy.enums.DispensingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DispensingRepository extends JpaRepository<Dispensing, UUID> {

    Optional<Dispensing> findByDispensingNumber(String dispensingNumber);

    Page<Dispensing> findByPatientId(UUID patientId, Pageable pageable);

    Page<Dispensing> findByStatus(DispensingStatus status, Pageable pageable);

    Page<Dispensing> findByDispensedBy(UUID dispensedBy, Pageable pageable);

    Page<Dispensing> findByDispensingDateBetween(java.time.LocalDate startDate, java.time.LocalDate endDate, Pageable pageable);
}
