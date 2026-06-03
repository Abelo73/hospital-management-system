package com.act.hospitalmanagementsystem.nursing.repository;

import com.act.hospitalmanagementsystem.nursing.entity.IncidentReport;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentSeverity;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentStatus;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncidentReportRepository extends JpaRepository<IncidentReport, UUID> {

    Optional<IncidentReport> findByIdAndDeletedFalse(UUID id);

    Page<IncidentReport> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<IncidentReport> findByIncidentTypeAndDeletedFalse(IncidentType incidentType, Pageable pageable);

    Page<IncidentReport> findBySeverityAndDeletedFalse(IncidentSeverity severity, Pageable pageable);

    Page<IncidentReport> findByStatusAndDeletedFalse(IncidentStatus status, Pageable pageable);

    Page<IncidentReport> findByReportedByAndDeletedFalse(UUID reportedBy, Pageable pageable);

    @Query("SELECT i FROM IncidentReport i WHERE i.deleted = false AND i.incidentDate BETWEEN :startDate AND :endDate")
    Page<IncidentReport> findByDateRange(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate, Pageable pageable);

    @Query("SELECT i FROM IncidentReport i WHERE i.deleted = false AND " +
           "(LOWER(i.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(i.location) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<IncidentReport> searchIncidents(@Param("searchTerm") String searchTerm, Pageable pageable);
}
