package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.PurchaseOrder;
import com.act.hospitalmanagementsystem.inventory.entity.PurchaseRequisition;
import com.act.hospitalmanagementsystem.inventory.enums.ProcurementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcurementRepository {

    // Purchase Order operations
    PurchaseOrder savePurchaseOrder(PurchaseOrder order);
    Optional<PurchaseOrder> findPurchaseOrderById(UUID id);
    Optional<PurchaseOrder> findPurchaseOrderByOrderNumber(String orderNumber);
    Page<PurchaseOrder> findAllPurchaseOrders(Pageable pageable);
    Page<PurchaseOrder> findPurchaseOrdersByStatus(ProcurementStatus status, Pageable pageable);
    Page<PurchaseOrder> findPurchaseOrdersBySupplierId(UUID supplierId, Pageable pageable);

    // Purchase Requisition operations
    PurchaseRequisition savePurchaseRequisition(PurchaseRequisition requisition);
    Optional<PurchaseRequisition> findPurchaseRequisitionById(UUID id);
    Optional<PurchaseRequisition> findPurchaseRequisitionByRequisitionNumber(String requisitionNumber);
    Page<PurchaseRequisition> findAllPurchaseRequisitions(Pageable pageable);
    Page<PurchaseRequisition> findPurchaseRequisitionsByStatus(ProcurementStatus status, Pageable pageable);
}
