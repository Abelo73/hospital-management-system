package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.PurchaseOrder;
import com.act.hospitalmanagementsystem.inventory.enums.ProcurementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseOrderJpaRepository extends JpaRepository<PurchaseOrder, UUID> {

    Optional<PurchaseOrder> findByOrderNumber(String orderNumber);

    Page<PurchaseOrder> findByStatus(ProcurementStatus status, Pageable pageable);

    Page<PurchaseOrder> findBySupplierId(UUID supplierId, Pageable pageable);
}
