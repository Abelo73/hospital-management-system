package com.act.hospitalmanagementsystem.nursing.repository;

import com.act.hospitalmanagementsystem.nursing.entity.VitalSign;
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
public interface VitalSignRepository extends JpaRepository<VitalSign, UUID> {

    Optional<VitalSign> findByIdAndDeletedFalse(UUID id);

    List<VitalSign> findByPatientIdAndDeletedFalseOrderByRecordedDateDescRecordedTimeDesc(UUID patientId);

    Page<VitalSign> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<VitalSign> findByRecordedByAndDeletedFalse(UUID recordedBy, Pageable pageable);

    @Query("SELECT v FROM VitalSign v WHERE v.deleted = false AND v.patientId = :patientId AND v.recordedDate = :date")
    List<VitalSign> findByPatientIdAndRecordedDate(@Param("patientId") UUID patientId, @Param("date") java.time.LocalDate date);

    @Query("SELECT v FROM VitalSign v WHERE v.deleted = false AND v.isAbnormal = true")
    Page<VitalSign> findAbnormalVitalSigns(Pageable pageable);

    @Query("SELECT v FROM VitalSign v WHERE v.deleted = false AND v.patientId = :patientId AND v.isAbnormal = true")
    Page<VitalSign> findAbnormalVitalSignsByPatient(@Param("patientId") UUID patientId, Pageable pageable);

    @Query("SELECT v FROM VitalSign v WHERE v.deleted = false AND v.patientId = :patientId " +
           "ORDER BY v.recordedDate DESC, v.recordedTime DESC")
    List<VitalSign> findLatestVitalSigns(@Param("patientId") UUID patientId, Pageable pageable);
}
