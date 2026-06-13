package com.act.hospitalmanagementsystem.inventory.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.dto.PurchaseOrderDTO;
import com.act.hospitalmanagementsystem.inventory.entity.PurchaseOrder;
import com.act.hospitalmanagementsystem.inventory.entity.PurchaseRequisition;
import com.act.hospitalmanagementsystem.inventory.entity.Supplier;
import com.act.hospitalmanagementsystem.inventory.enums.ProcurementStatus;
import com.act.hospitalmanagementsystem.inventory.mapper.ProcurementMapper;
import com.act.hospitalmanagementsystem.inventory.repository.ProcurementRepository;
import com.act.hospitalmanagementsystem.inventory.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcurementService {

    private final ProcurementRepository procurementRepository;
    private final SupplierRepository supplierRepository;
    private final ProcurementMapper procurementMapper;

    @Transactional
    public BaseResponseDTO<PurchaseOrderDTO> createPurchaseOrder(UUID supplierId, List<Map<String, Object>> items,
            LocalDate expectedDeliveryDate, String paymentMethod, String deliveryMethod, String createdBy) {
        try {
            Supplier supplier = supplierRepository.findById(supplierId)
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));

            PurchaseOrder order = new PurchaseOrder();
            order.setOrderNumber(generateOrderNumber());
            order.setSupplier(supplier);
            order.setOrderDate(LocalDate.now());
            order.setExpectedDeliveryDate(expectedDeliveryDate);
            order.setStatus(ProcurementStatus.DRAFT);
            order.setItems(items.toString());
            order.setPaymentMethod(paymentMethod);
            order.setDeliveryMethod(deliveryMethod);
            order.setCreatedBy(createdBy);

            // Calculate totals
            // TODO: Implement total calculation based on items

            PurchaseOrder saved = procurementRepository.savePurchaseOrder(order);
            return BaseResponseDTO.success(procurementMapper.toDTO(saved), "Purchase order created successfully");
        } catch (Exception e) {
            log.error("Error creating purchase order", e);
            return BaseResponseDTO.error("Failed to create purchase order: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> receiveGoods(UUID purchaseOrderId, List<Map<String, Object>> items, UUID receivedBy, String notes) {
        try {
            PurchaseOrder order = procurementRepository.findPurchaseOrderById(purchaseOrderId)
                    .orElseThrow(() -> new RuntimeException("Purchase order not found"));

            // Create goods receipt
            // TODO: Implement goods receipt creation

            // Update stock
            // TODO: Implement stock update based on received items

            // Update purchase order status
            order.setActualDeliveryDate(LocalDate.now());
            order.setStatus(ProcurementStatus.RECEIVED);
            procurementRepository.savePurchaseOrder(order);

            return BaseResponseDTO.success(null, "Goods received successfully");
        } catch (Exception e) {
            log.error("Error receiving goods", e);
            return BaseResponseDTO.error("Failed to receive goods: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<PurchaseOrderDTO>> getPurchaseOrders(ProcurementStatus status, Pageable pageable) {
        try {
            Page<PurchaseOrder> orders;
            if (status != null) {
                orders = procurementRepository.findPurchaseOrdersByStatus(status, pageable);
            } else {
                orders = procurementRepository.findAllPurchaseOrders(pageable);
            }
            return BaseResponseDTO.success(procurementMapper.toDTOList(orders.getContent()), "Purchase orders retrieved");
        } catch (Exception e) {
            log.error("Error getting purchase orders", e);
            return BaseResponseDTO.error("Failed to get purchase orders: " + e.getMessage());
        }
    }

    private String generateOrderNumber() {
        // TODO: Implement proper order number generation
        return "PO-" + System.currentTimeMillis();
    }
}
