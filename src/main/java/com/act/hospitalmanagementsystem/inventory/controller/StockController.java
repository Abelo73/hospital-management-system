package com.act.hospitalmanagementsystem.inventory.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.dto.StockDTO;
import com.act.hospitalmanagementsystem.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<List<StockDTO>>> getStockStatus(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            Pageable pageable) {
        BaseResponseDTO<List<StockDTO>> response = stockService.getStockStatus(location, category, status, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/item/{itemId}")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<List<StockDTO>>> getStockByItem(@PathVariable UUID itemId) {
        BaseResponseDTO<List<StockDTO>> response = stockService.getStockByItem(itemId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/low-stock")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<List<StockDTO>>> getLowStockItems(@RequestParam(required = false) String location) {
        BaseResponseDTO<List<StockDTO>> response = stockService.getLowStockItems(location);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/expiring")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<List<StockDTO>>> getExpiringItems(
            @RequestParam Integer days,
            @RequestParam(required = false) String location) {
        BaseResponseDTO<List<StockDTO>> response = stockService.getExpiringItems(days, location);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/adjust")
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> adjustStock(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID itemId = UUID.fromString((String) request.get("itemId"));
        UUID locationId = UUID.fromString((String) request.get("locationId"));
        UUID batchId = request.get("batchId") != null ? UUID.fromString((String) request.get("batchId")) : null;
        Integer adjustmentQuantity = (Integer) request.get("adjustmentQuantity");
        String reason = (String) request.get("reason");
        String adjustmentType = (String) request.get("adjustmentType");
        String updatedBy = authentication.getName();

        BaseResponseDTO<Void> response = stockService.adjustStock(itemId, locationId, batchId, adjustmentQuantity, reason, adjustmentType, updatedBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> transferStock(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID itemId = UUID.fromString((String) request.get("itemId"));
        UUID fromLocationId = UUID.fromString((String) request.get("fromLocationId"));
        UUID toLocationId = UUID.fromString((String) request.get("toLocationId"));
        Integer quantity = (Integer) request.get("quantity");
        String reason = (String) request.get("reason");
        String updatedBy = authentication.getName();

        BaseResponseDTO<Void> response = stockService.transferStock(itemId, fromLocationId, toLocationId, quantity, reason, updatedBy);
        return ResponseEntity.ok(response);
    }
}
