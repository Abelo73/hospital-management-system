package com.act.hospitalmanagementsystem.inventory.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.inventory.dto.DepartmentRequestDTO;
import com.act.hospitalmanagementsystem.inventory.dto.StockIssueDTO;
import com.act.hospitalmanagementsystem.inventory.service.DistributionService;
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
@RequestMapping("/api/inventory/distribution")
@RequiredArgsConstructor
public class DistributionController {

    private final DistributionService distributionService;

    @PostMapping("/requests")
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ResponseEntity<BaseResponseDTO<DepartmentRequestDTO>> createDepartmentRequest(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        String department = (String) request.get("department");
        LocalDate requiredDate = request.get("requiredDate") != null ? 
                LocalDate.parse((String) request.get("requiredDate")) : null;
        String priority = (String) request.get("priority");
        String purpose = (String) request.get("purpose");
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
        String requestedBy = authentication.getName();

        BaseResponseDTO<DepartmentRequestDTO> response = distributionService.createDepartmentRequest(
                department, requiredDate, priority, purpose, items, requestedBy);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/requests")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ResponseEntity<BaseResponseDTO<List<DepartmentRequestDTO>>> getPendingRequests(
            @RequestParam(required = false) String department,
            Pageable pageable) {
        BaseResponseDTO<List<DepartmentRequestDTO>> response = distributionService.getPendingRequests(department, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/requests/{id}/approve")
    @PreAuthorize("hasAuthority('INVENTORY_ADMIN')")
    public ResponseEntity<BaseResponseDTO<Void>> approveRequest(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        Boolean approved = (Boolean) request.get("approved");
        String notes = (String) request.get("notes");
        String approvedBy = authentication.getName();

        BaseResponseDTO<Void> response = distributionService.approveRequest(id, approved, notes, approvedBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/issues")
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ResponseEntity<BaseResponseDTO<StockIssueDTO>> issueStock(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID requestId = UUID.fromString((String) request.get("requestId"));
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
        UUID receivedBy = UUID.fromString(authentication.getName());

        BaseResponseDTO<StockIssueDTO> response = distributionService.issueStock(requestId, items, receivedBy);
        return ResponseEntity.ok(response);
    }
}
