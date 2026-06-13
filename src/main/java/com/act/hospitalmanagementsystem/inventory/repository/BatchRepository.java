package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BatchRepository extends JpaRepository<Batch, UUID> {

    Optional<Batch> findByBatchNumber(String batchNumber);

    List<Batch> findByItemId(UUID itemId);

    @Query("SELECT b FROM Batch b WHERE b.deleted = false AND b.expiryDate <= :date")
    List<Batch> findExpiringBatches(@Param("date") LocalDate date);

    @Query("SELECT b FROM Batch b WHERE b.deleted = false AND b.expiryDate BETWEEN :startDate AND :endDate")
    List<Batch> findBatchesExpiringBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
