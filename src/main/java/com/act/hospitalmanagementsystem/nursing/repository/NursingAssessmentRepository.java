package com.act.hospitalmanagementsystem.nursing.repository;

import com.act.hospitalmanagementsystem.nursing.entity.NursingAssessment;
import com.act.hospitalmanagementsystem.nursing.enums.AssessmentType;
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
public interface NursingAssessmentRepository extends JpaRepository<NursingAssessment, UUID> {

    Optional<NursingAssessment> findByIdAndDeletedFalse(UUID id);

    List<NursingAssessment> findByPatientIdAndDeletedFalseOrderByAssessmentDateDesc(UUID patientId);

    Page<NursingAssessment> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<NursingAssessment> findByAssessmentTypeAndDeletedFalse(AssessmentType assessmentType, Pageable pageable);

    Page<NursingAssessment> findByPatientIdAndAssessmentTypeAndDeletedFalse(UUID patientId, AssessmentType assessmentType, Pageable pageable);

    Page<NursingAssessment> findByNurseIdAndDeletedFalse(UUID nurseId, Pageable pageable);

    @Query("SELECT n FROM NursingAssessment n WHERE n.deleted = false AND n.assessmentDate = :date")
    List<NursingAssessment> findByAssessmentDate(@Param("date") java.time.LocalDate date);

    @Query("SELECT n FROM NursingAssessment n WHERE n.deleted = false AND " +
           "(LOWER(n.generalAppearance) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(n.mentalStatus) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(n.notes) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<NursingAssessment> searchAssessments(@Param("searchTerm") String searchTerm, Pageable pageable);
}
