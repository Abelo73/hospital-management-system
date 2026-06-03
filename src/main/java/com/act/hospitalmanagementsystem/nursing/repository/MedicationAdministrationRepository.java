package com.act.hospitalmanagementsystem.nursing.repository;

import com.act.hospitalmanagementsystem.nursing.entity.MedicationAdministration;
import com.act.hospitalmanagementsystem.nursing.enums.AdministrationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicationAdministrationRepository extends JpaRepository<MedicationAdministration, UUID> {

    Optional<MedicationAdministration> findByIdAndDeletedFalse(UUID id);

    List<MedicationAdministration> findByPatientIdAndDeletedFalseOrderByScheduledDateDescScheduledTimeDesc(UUID patientId);

    Page<MedicationAdministration> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<MedicationAdministration> findByAdministeredByAndDeletedFalse(UUID administeredBy, Pageable pageable);

    Page<MedicationAdministration> findByAdministrationStatusAndDeletedFalse(AdministrationStatus administrationStatus, Pageable pageable);

    @Query("SELECT m FROM MedicationAdministration m WHERE m.deleted = false AND " +
           "m.scheduledDate = :date AND m.administrationStatus = 'SCHEDULED'")
    List<MedicationAdministration> findScheduledMedications(@Param("date") LocalDate date);

    @Query("SELECT m FROM MedicationAdministration m WHERE m.deleted = false AND " +
           "m.scheduledDate = :date AND m.scheduledTime < :time AND m.administrationStatus = 'SCHEDULED'")
    List<MedicationAdministration> findOverdueMedications(@Param("date") LocalDate date, @Param("time") LocalTime time);

    @Query("SELECT m FROM MedicationAdministration m WHERE m.deleted = false AND m.patientId = :patientId " +
           "AND m.scheduledDate BETWEEN :startDate AND :endDate")
    Page<MedicationAdministration> findByPatientIdAndDateRange(@Param("patientId") UUID patientId,
            @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
}
