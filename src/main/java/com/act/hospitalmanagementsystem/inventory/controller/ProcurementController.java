package com.act.hospitalmanagementsystem.inventory.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.dto.PurchaseOrderDTO;
import com.act.hospitalmanagementsystem.inventory.enums.ProcurementStatus;
import com.act.hospitalmanagementsystem.inventory.service.ProcurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/procurement")
@RequiredArgsConstructor
public class ProcurementController {

    private final ProcurementService procurementService;

    @PostMapping("/purchase-orders")
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ResponseEntity<BaseResponseDTO<PurchaseOrderDTO>> createPurchaseOrder(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID supplierId = UUID.fromString((String) request.get("supplierId"));
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
        LocalDate expectedDeliveryDate = request.get("expectedDeliveryDate") != null ? 
                LocalDate.parse((String) request.get("expectedDeliveryDate")) : null;
        String paymentMethod = (String) request.get("paymentMethod");
        String deliveryMethod = (String) request.get("deliveryMethod");
        String createdBy = authentication.getName();

        BaseResponseDTO<PurchaseOrderDTO> response = procurementService.createPurchaseOrder(
                supplierId, items, expectedDeliveryDate, paymentMethod, deliveryMethod, createdBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/goods-receipt")
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> receiveGoods(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID purchaseOrderId = UUID.fromString((String) request.get("purchaseOrderId"));
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
        UUID receivedBy = UUID.fromString(authentication.getName());
        String notes = (String) request.get("notes");

        BaseResponseDTO<Void> response = procurementService.receiveGoods(purchaseOrderId, items, receivedBy, notes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/purchase-orders")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<List<PurchaseOrderDTO>>> getPurchaseOrders(
            @RequestParam(required = false) ProcurementStatus status,
            Pageable pageable) {
        BaseResponseDTO<List<PurchaseOrderDTO>> response = procurementService.getPurchaseOrders(status, pageable);
        return ResponseEntity.ok(response);
    }
}
