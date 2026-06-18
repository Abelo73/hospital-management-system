package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.BenefitsDTO;
import com.act.hospitalmanagementsystem.hr.entity.Benefits;
import com.act.hospitalmanagementsystem.hr.service.BenefitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hr/benefits")
@RequiredArgsConstructor
public class BenefitsController {

    private final BenefitsService benefitsService;

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<BenefitsDTO>>> getBenefits(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) String benefitType,
            @RequestParam(required = false) String status,
            Pageable pageable) {
        BaseResponseDTO<List<BenefitsDTO>> response = benefitsService.getBenefits(employeeId, benefitType, status, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<BenefitsDTO>> getBenefitById(@PathVariable UUID id) {
        BaseResponseDTO<BenefitsDTO> response = benefitsService.getBenefitById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<BenefitsDTO>> createBenefit(
            @RequestBody Benefits benefit,
            Authentication authentication) {
        String createdBy = authentication.getName();
        BaseResponseDTO<BenefitsDTO> response = benefitsService.createBenefit(benefit, createdBy);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<BenefitsDTO>> updateBenefit(
            @PathVariable UUID id,
            @RequestBody Benefits benefit,
            Authentication authentication) {
        String updatedBy = authentication.getName();
        BaseResponseDTO<BenefitsDTO> response = benefitsService.updateBenefit(id, benefit, updatedBy);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_ADMIN')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteBenefit(@PathVariable UUID id) {
        BaseResponseDTO<Void> response = benefitsService.deleteBenefit(id);
        return ResponseEntity.ok(response);
    }
}
