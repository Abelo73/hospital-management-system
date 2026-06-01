package com.act.hospitalmanagementsystem.medical.repository;

import com.act.hospitalmanagementsystem.medical.entity.Medication;
import com.act.hospitalmanagementsystem.medical.enums.MedicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, UUID> {

    Optional<Medication> findByIdAndDeletedFalse(UUID id);

    List<Medication> findByPatientIdAndDeletedFalseOrderByStartDateDesc(UUID patientId);

    List<Medication> findByPatientIdAndStatusAndDeletedFalse(UUID patientId, MedicationStatus status);

    Page<Medication> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<Medication> findByPatientIdAndStatusAndDeletedFalse(UUID patientId, MedicationStatus status, Pageable pageable);

    @Query("SELECT m FROM Medication m WHERE m.deleted = false AND m.patientId = :patientId AND " +
           "(LOWER(m.medicationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.genericName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Medication> searchPatientMedications(@Param("patientId") UUID patientId, @Param("searchTerm") String searchTerm, Pageable pageable);
}
