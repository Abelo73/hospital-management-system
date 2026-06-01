package com.act.hospitalmanagementsystem.medical.repository;

import com.act.hospitalmanagementsystem.medical.entity.Diagnosis;
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
public interface DiagnosisRepository extends JpaRepository<Diagnosis, UUID> {

    Optional<Diagnosis> findByIdAndDeletedFalse(UUID id);

    List<Diagnosis> findByMedicalRecordIdAndDeletedFalseOrderByDiagnosisDateDesc(UUID medicalRecordId);

    Page<Diagnosis> findByMedicalRecordIdAndDeletedFalse(UUID medicalRecordId, Pageable pageable);

    @Query("SELECT d FROM Diagnosis d WHERE d.deleted = false AND d.medicalRecordId = :medicalRecordId AND " +
           "LOWER(d.diagnosisName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Diagnosis> searchMedicalRecordDiagnoses(@Param("medicalRecordId") UUID medicalRecordId, @Param("searchTerm") String searchTerm, Pageable pageable);
}
