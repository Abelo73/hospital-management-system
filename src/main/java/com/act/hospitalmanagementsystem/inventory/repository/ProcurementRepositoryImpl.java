package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.PurchaseOrder;
import com.act.hospitalmanagementsystem.inventory.entity.PurchaseRequisition;
import com.act.hospitalmanagementsystem.inventory.enums.ProcurementStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProcurementRepositoryImpl implements ProcurementRepository {

    private final PurchaseOrderJpaRepository purchaseOrderRepo;
    private final PurchaseRequisitionJpaRepository purchaseRequisitionRepo;

    // Purchase Order operations
    @Override
    public PurchaseOrder savePurchaseOrder(PurchaseOrder order) {
        return purchaseOrderRepo.save(order);
    }

    @Override
    public Optional<PurchaseOrder> findPurchaseOrderById(UUID id) {
        return purchaseOrderRepo.findById(id);
    }

    @Override
    public Optional<PurchaseOrder> findPurchaseOrderByOrderNumber(String orderNumber) {
        return purchaseOrderRepo.findByOrderNumber(orderNumber);
    }

    @Override
    public Page<PurchaseOrder> findAllPurchaseOrders(Pageable pageable) {
        return purchaseOrderRepo.findAll(pageable);
    }

    @Override
    public Page<PurchaseOrder> findPurchaseOrdersByStatus(ProcurementStatus status, Pageable pageable) {
        return purchaseOrderRepo.findByStatus(status, pageable);
    }

    @Override
    public Page<PurchaseOrder> findPurchaseOrdersBySupplierId(UUID supplierId, Pageable pageable) {
        return purchaseOrderRepo.findBySupplierId(supplierId, pageable);
    }

    // Purchase Requisition operations
    @Override
    public PurchaseRequisition savePurchaseRequisition(PurchaseRequisition requisition) {
        return purchaseRequisitionRepo.save(requisition);
    }

    @Override
    public Optional<PurchaseRequisition> findPurchaseRequisitionById(UUID id) {
        return purchaseRequisitionRepo.findById(id);
    }

    @Override
    public Optional<PurchaseRequisition> findPurchaseRequisitionByRequisitionNumber(String requisitionNumber) {
        return purchaseRequisitionRepo.findByRequisitionNumber(requisitionNumber);
    }

    @Override
    public Page<PurchaseRequisition> findAllPurchaseRequisitions(Pageable pageable) {
        return purchaseRequisitionRepo.findAll(pageable);
    }

    @Override
    public Page<PurchaseRequisition> findPurchaseRequisitionsByStatus(ProcurementStatus status, Pageable pageable) {
        return purchaseRequisitionRepo.findByStatus(status, pageable);
    }
}
