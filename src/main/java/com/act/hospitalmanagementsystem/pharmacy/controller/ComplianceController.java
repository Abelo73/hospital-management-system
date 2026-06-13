package com.act.hospitalmanagementsystem.pharmacy.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.pharmacy.service.ComplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pharmacy/controlled-substances")
@RequiredArgsConstructor
public class ComplianceController {

    private final ComplianceService complianceService;

    @GetMapping("/log")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<BaseResponseDTO<Map<String, Object>>> getControlledSubstanceLog(
            @RequestParam UUID drugId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        BaseResponseDTO<Map<String, Object>> response = complianceService.getControlledSubstanceLog(drugId, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transaction")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<BaseResponseDTO<Void>> recordControlledSubstanceTransaction(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID drugId = UUID.fromString((String) request.get("drugId"));
        String transactionType = (String) request.get("transactionType");
        Integer quantity = (Integer) request.get("quantity");
        String batchNumber = (String) request.get("batchNumber");
        String prescriptionNumber = (String) request.get("prescriptionNumber");
        String reason = (String) request.get("reason");
        String performedBy = authentication.getName();

        BaseResponseDTO<Void> response = complianceService.recordControlledSubstanceTransaction(
                drugId, transactionType, quantity, batchNumber, prescriptionNumber, reason, performedBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/destroy")
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<BaseResponseDTO<Void>> destroyControlledSubstance(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID drugId = UUID.fromString((String) request.get("drugId"));
        String batchNumber = (String) request.get("batchNumber");
        Integer quantity = (Integer) request.get("quantity");
        String reason = (String) request.get("reason");
        String witnessedBy = authentication.getName();

        BaseResponseDTO<Void> response = complianceService.destroyControlledSubstance(
                drugId, batchNumber, quantity, reason, witnessedBy);
        return ResponseEntity.ok(response);
    }
}
