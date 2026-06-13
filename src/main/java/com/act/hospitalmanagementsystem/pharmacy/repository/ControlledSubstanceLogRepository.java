package com.act.hospitalmanagementsystem.pharmacy.repository;

import com.act.hospitalmanagementsystem.pharmacy.entity.ControlledSubstanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ControlledSubstanceLogRepository extends JpaRepository<ControlledSubstanceLog, UUID> {

    List<ControlledSubstanceLog> findByDrugId(UUID drugId);

    @Query("SELECT csl FROM ControlledSubstanceLog csl WHERE csl.deleted = false AND csl.performedAt BETWEEN :startDate AND :endDate")
    List<ControlledSubstanceLog> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT csl FROM ControlledSubstanceLog csl WHERE csl.deleted = false AND csl.transactionType = :type")
    List<ControlledSubstanceLog> findByTransactionType(@Param("type") String type);
}
