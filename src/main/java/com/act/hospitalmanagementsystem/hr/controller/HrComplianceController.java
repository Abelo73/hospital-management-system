package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.ComplianceDTO;
import com.act.hospitalmanagementsystem.hr.entity.Compliance;
import com.act.hospitalmanagementsystem.hr.service.HrComplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hr/hr-compliance")
@RequiredArgsConstructor
public class HrComplianceController {

    private final HrComplianceService complianceService;

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<ComplianceDTO>>> getCompliance(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) String complianceType,
            @RequestParam(required = false) String status,
            Pageable pageable) {
        BaseResponseDTO<List<ComplianceDTO>> response = complianceService.getCompliance(employeeId, complianceType, status, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<ComplianceDTO>> getComplianceById(@PathVariable UUID id) {
        BaseResponseDTO<ComplianceDTO> response = complianceService.getComplianceById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<ComplianceDTO>> createCompliance(
            @RequestBody Compliance compliance,
            Authentication authentication) {
        String createdBy = authentication.getName();
        BaseResponseDTO<ComplianceDTO> response = complianceService.createCompliance(compliance, createdBy);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<ComplianceDTO>> updateCompliance(
            @PathVariable UUID id,
            @RequestBody Compliance compliance,
            Authentication authentication) {
        String updatedBy = authentication.getName();
        BaseResponseDTO<ComplianceDTO> response = complianceService.updateCompliance(id, compliance, updatedBy);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_ADMIN')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteCompliance(@PathVariable UUID id) {
        BaseResponseDTO<Void> response = complianceService.deleteCompliance(id);
        return ResponseEntity.ok(response);
    }
}
