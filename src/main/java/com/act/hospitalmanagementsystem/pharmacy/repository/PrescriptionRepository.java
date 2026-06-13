package com.act.hospitalmanagementsystem.pharmacy.repository;

import com.act.hospitalmanagementsystem.pharmacy.entity.Prescription;
import com.act.hospitalmanagementsystem.pharmacy.enums.PrescriptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

    Optional<Prescription> findByPrescriptionNumber(String prescriptionNumber);

    Page<Prescription> findByPatientId(UUID patientId, Pageable pageable);

    Page<Prescription> findByDoctorId(UUID doctorId, Pageable pageable);

    Page<Prescription> findByStatus(PrescriptionStatus status, Pageable pageable);

    @Query("SELECT p FROM Prescription p WHERE p.deleted = false AND p.status = :status ORDER BY p.prescriptionDate ASC")
    Page<Prescription> findPendingPrescriptions(@Param("status") PrescriptionStatus status, Pageable pageable);

    @Query("SELECT p FROM Prescription p WHERE p.deleted = false AND p.patientId = :patientId AND p.status = :status")
    Page<Prescription> findByPatientIdAndStatus(@Param("patientId") UUID patientId, @Param("status") PrescriptionStatus status, Pageable pageable);
}
