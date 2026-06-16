package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.PurchaseRequisition;
import com.act.hospitalmanagementsystem.inventory.enums.ProcurementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseRequisitionJpaRepository extends JpaRepository<PurchaseRequisition, UUID> {

    Optional<PurchaseRequisition> findByRequisitionNumber(String requisitionNumber);

    Page<PurchaseRequisition> findByStatus(ProcurementStatus status, Pageable pageable);
}
