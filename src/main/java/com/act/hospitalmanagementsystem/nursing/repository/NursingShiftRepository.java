package com.act.hospitalmanagementsystem.nursing.repository;

import com.act.hospitalmanagementsystem.nursing.entity.NursingShift;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftRecordStatus;
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
public interface NursingShiftRepository extends JpaRepository<NursingShift, UUID> {

    Optional<NursingShift> findByIdAndDeletedFalse(UUID id);

    List<NursingShift> findByShiftDateAndDeletedFalseOrderByShiftDateDesc(java.time.LocalDate shiftDate);

    Page<NursingShift> findByShiftDateAndDeletedFalse(java.time.LocalDate shiftDate, Pageable pageable);

    Page<NursingShift> findByNurseIdAndDeletedFalse(UUID nurseId, Pageable pageable);

    Page<NursingShift> findByShiftTypeAndDeletedFalse(ShiftType shiftType, Pageable pageable);

    Page<NursingShift> findByStatusAndDeletedFalse(ShiftRecordStatus status, Pageable pageable);

    Page<NursingShift> findByUnitAndDeletedFalse(String unit, Pageable pageable);

    @Query("SELECT n FROM NursingShift n WHERE n.deleted = false AND n.shiftDate BETWEEN :startDate AND :endDate")
    Page<NursingShift> findByDateRange(@Param("startDate") java.time.LocalDate startDate,
            @Param("endDate") java.time.LocalDate endDate, Pageable pageable);
}
