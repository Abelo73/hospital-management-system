package com.act.hospitalmanagementsystem.nursing.repository;

import com.act.hospitalmanagementsystem.nursing.entity.NursingCarePlan;
import com.act.hospitalmanagementsystem.nursing.enums.CarePlanStatus;
import com.act.hospitalmanagementsystem.nursing.enums.CarePlanType;
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
public interface NursingCarePlanRepository extends JpaRepository<NursingCarePlan, UUID> {

    Optional<NursingCarePlan> findByIdAndDeletedFalse(UUID id);

    List<NursingCarePlan> findByPatientIdAndDeletedFalseOrderByStartDateDesc(UUID patientId);

    Page<NursingCarePlan> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<NursingCarePlan> findByPlanTypeAndDeletedFalse(CarePlanType planType, Pageable pageable);

    Page<NursingCarePlan> findByPatientIdAndPlanTypeAndDeletedFalse(UUID patientId, CarePlanType planType, Pageable pageable);

    Page<NursingCarePlan> findByStatusAndDeletedFalse(CarePlanStatus status, Pageable pageable);

    Page<NursingCarePlan> findByPrimaryNurseIdAndDeletedFalse(UUID primaryNurseId, Pageable pageable);

    @Query("SELECT n FROM NursingCarePlan n WHERE n.deleted = false AND " +
           "(LOWER(n.planName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(n.nursingDiagnosis) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<NursingCarePlan> searchCarePlans(@Param("searchTerm") String searchTerm, Pageable pageable);
}
