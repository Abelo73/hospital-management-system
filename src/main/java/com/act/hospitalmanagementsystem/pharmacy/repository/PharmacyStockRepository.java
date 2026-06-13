package com.act.hospitalmanagementsystem.pharmacy.repository;

import com.act.hospitalmanagementsystem.pharmacy.entity.PharmacyStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PharmacyStockRepository extends JpaRepository<PharmacyStock, UUID> {

    List<PharmacyStock> findByDrugId(UUID drugId);

    @Query("SELECT ps FROM PharmacyStock ps WHERE ps.deleted = false AND ps.quantity <= 0")
    List<PharmacyStock> findOutOfStock();

    @Query("SELECT ps FROM PharmacyStock ps WHERE ps.deleted = false AND ps.expiryDate <= :date")
    List<PharmacyStock> findExpiringStock(@Param("date") LocalDate date);

    @Query("SELECT ps FROM PharmacyStock ps WHERE ps.deleted = false AND ps.status = :status")
    List<PharmacyStock> findByStatus(@Param("status") String status);
}
