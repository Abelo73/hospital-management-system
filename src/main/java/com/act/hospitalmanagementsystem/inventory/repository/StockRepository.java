package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.Stock;
import com.act.hospitalmanagementsystem.inventory.enums.StockStatus;
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
public interface StockRepository extends JpaRepository<Stock, UUID> {

    Optional<Stock> findByItemIdAndLocationId(UUID itemId, UUID locationId);

    Page<Stock> findByLocationId(UUID locationId, Pageable pageable);

    Page<Stock> findByItemId(UUID itemId, Pageable pageable);

    @Query("SELECT s FROM Stock s WHERE s.deleted = false AND s.item.id = :itemId AND s.availableQuantity <= s.item.reorderLevel")
    List<Stock> findLowStockForItem(@Param("itemId") UUID itemId);

    @Query("SELECT s FROM Stock s WHERE s.deleted = false AND s.availableQuantity <= s.item.reorderLevel")
    Page<Stock> findAllLowStock(Pageable pageable);

    @Query("SELECT s FROM Stock s WHERE s.deleted = false AND s.expiryDate <= :date")
    Page<Stock> findExpiringStock(@Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT s FROM Stock s WHERE s.deleted = false AND s.status = :status")
    Page<Stock> findByStatus(@Param("status") StockStatus status, Pageable pageable);
}
