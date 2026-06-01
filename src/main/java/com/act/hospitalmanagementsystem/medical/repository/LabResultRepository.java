package com.act.hospitalmanagementsystem.medical.repository;

import com.act.hospitalmanagementsystem.medical.entity.LabResult;
import com.act.hospitalmanagementsystem.medical.enums.LabResultStatus;
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
public interface LabResultRepository extends JpaRepository<LabResult, UUID> {

    Optional<LabResult> findByIdAndDeletedFalse(UUID id);

    List<LabResult> findByPatientIdAndDeletedFalseOrderByTestDateDesc(UUID patientId);

    Page<LabResult> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<LabResult> findByPatientIdAndStatusAndDeletedFalse(UUID patientId, LabResultStatus status, Pageable pageable);

    @Query("SELECT l FROM LabResult l WHERE l.deleted = false AND l.patientId = :patientId AND " +
           "(LOWER(l.testName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(l.testType) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<LabResult> searchPatientLabResults(@Param("patientId") UUID patientId, @Param("searchTerm") String searchTerm, Pageable pageable);
}
