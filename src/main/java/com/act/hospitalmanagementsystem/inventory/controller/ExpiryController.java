package com.act.hospitalmanagementsystem.inventory.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.service.ExpiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/expiry")
@RequiredArgsConstructor
public class ExpiryController {

    private final ExpiryService expiryService;

    @GetMapping("/alerts")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<List<Object>>> getExpiryAlerts(
            @RequestParam Integer days,
            @RequestParam(required = false) String location) {
        BaseResponseDTO<List<Object>> response = expiryService.getExpiryAlerts(days, location);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/alerts/{id}/acknowledge")
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> acknowledgeAlert(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        String actionTaken = (String) request.get("actionTaken");
        String acknowledgedBy = authentication.getName();

        BaseResponseDTO<Void> response = expiryService.acknowledgeAlert(id, actionTaken, acknowledgedBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dispose")
    @PreAuthorize("hasAuthority('INVENTORY_ADMIN')")
    public ResponseEntity<BaseResponseDTO<Void>> disposeExpiredItems(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
        UUID disposedBy = UUID.fromString(authentication.getName());

        BaseResponseDTO<Void> response = expiryService.disposeExpiredItems(items, disposedBy);
        return ResponseEntity.ok(response);
    }
}
