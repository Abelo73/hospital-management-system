package com.act.hospitalmanagementsystem.billing.repository;

import com.act.hospitalmanagementsystem.billing.entity.InsuranceClaim;
import com.act.hospitalmanagementsystem.billing.enums.ClaimStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaim, UUID> {
    Page<InsuranceClaim> findByDeletedFalse(Pageable pageable);
    Page<InsuranceClaim> findByStatusAndDeletedFalse(ClaimStatus status, Pageable pageable);
    List<InsuranceClaim> findByPatientIdAndDeletedFalse(UUID patientId);
}
