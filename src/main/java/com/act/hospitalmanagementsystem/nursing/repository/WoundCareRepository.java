package com.act.hospitalmanagementsystem.nursing.repository;

import com.act.hospitalmanagementsystem.nursing.entity.WoundCare;
import com.act.hospitalmanagementsystem.nursing.enums.WoundType;
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
public interface WoundCareRepository extends JpaRepository<WoundCare, UUID> {

    Optional<WoundCare> findByIdAndDeletedFalse(UUID id);

    List<WoundCare> findByPatientIdAndDeletedFalseOrderByAssessmentDateDesc(UUID patientId);

    Page<WoundCare> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<WoundCare> findByWoundTypeAndDeletedFalse(WoundType woundType, Pageable pageable);

    Page<WoundCare> findByPatientIdAndWoundTypeAndDeletedFalse(UUID patientId, WoundType woundType, Pageable pageable);

    Page<WoundCare> findByAssessedByAndDeletedFalse(UUID assessedBy, Pageable pageable);

    @Query("SELECT w FROM WoundCare w WHERE w.deleted = false AND w.assessmentDate = :date")
    List<WoundCare> findByAssessmentDate(@Param("date") java.time.LocalDate date);

    @Query("SELECT w FROM WoundCare w WHERE w.deleted = false AND " +
           "LOWER(w.woundLocation) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<WoundCare> searchWoundCare(@Param("searchTerm") String searchTerm, Pageable pageable);
}
