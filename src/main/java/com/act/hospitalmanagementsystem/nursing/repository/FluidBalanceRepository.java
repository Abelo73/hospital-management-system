package com.act.hospitalmanagementsystem.nursing.repository;

import com.act.hospitalmanagementsystem.nursing.entity.FluidBalance;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftType;
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
public interface FluidBalanceRepository extends JpaRepository<FluidBalance, UUID> {

    Optional<FluidBalance> findByIdAndDeletedFalse(UUID id);

    List<FluidBalance> findByPatientIdAndDeletedFalseOrderByRecordDateDesc(UUID patientId);

    Page<FluidBalance> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<FluidBalance> findByShiftAndDeletedFalse(ShiftType shift, Pageable pageable);

    Page<FluidBalance> findByPatientIdAndShiftAndDeletedFalse(UUID patientId, ShiftType shift, Pageable pageable);

    @Query("SELECT f FROM FluidBalance f WHERE f.deleted = false AND f.recordDate = :date")
    List<FluidBalance> findByRecordDate(@Param("date") java.time.LocalDate date);

    @Query("SELECT f FROM FluidBalance f WHERE f.deleted = false AND f.patientId = :patientId " +
           "AND f.recordDate BETWEEN :startDate AND :endDate")
    Page<FluidBalance> findByPatientIdAndDateRange(@Param("patientId") UUID patientId,
            @Param("startDate") java.time.LocalDate startDate, @Param("endDate") java.time.LocalDate endDate, Pageable pageable);
}
