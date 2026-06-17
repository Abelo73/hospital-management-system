package com.act.hospitalmanagementsystem.billing.controller;

import com.act.hospitalmanagementsystem.billing.dto.*;
import com.act.hospitalmanagementsystem.billing.service.InsuranceClaimService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/billing/insurance")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('BILLING_READ')")
public class InsuranceController {

    private final InsuranceClaimService insuranceClaimService;

    @GetMapping("/providers")
    public ResponseEntity<BaseResponseDTO<List<InsuranceProviderDTO>>> getProviders() {
        return ResponseEntity.ok(BaseResponseDTO.success(insuranceClaimService.getActiveProviders()));
    }

    @PostMapping("/providers")
    @PreAuthorize("hasAuthority('BILLING_WRITE')")
    public ResponseEntity<BaseResponseDTO<InsuranceProviderDTO>> createProvider(@RequestBody InsuranceProviderDTO dto) {
        return ResponseEntity.ok(BaseResponseDTO.success("Provider created", insuranceClaimService.createProvider(dto)));
    }

    @PostMapping("/claims")
    @PreAuthorize("hasAuthority('BILLING_WRITE')")
    public ResponseEntity<BaseResponseDTO<InsuranceClaimDTO>> submitClaim(@Valid @RequestBody SubmitClaimRequest request) {
        return ResponseEntity.ok(BaseResponseDTO.success("Claim submitted", insuranceClaimService.submitClaim(request)));
    }

    @GetMapping("/claims")
    public ResponseEntity<BaseResponseDTO<Page<InsuranceClaimDTO>>> getAllClaims(Pageable pageable) {
        return ResponseEntity.ok(BaseResponseDTO.success(insuranceClaimService.getAllClaims(pageable)));
    }

    @GetMapping("/claims/{id}")
    public ResponseEntity<BaseResponseDTO<InsuranceClaimDTO>> getClaim(@PathVariable UUID id) {
        return ResponseEntity.ok(BaseResponseDTO.success(insuranceClaimService.getClaimById(id)));
    }

    @PutMapping("/claims/{id}/status")
    @PreAuthorize("hasAuthority('BILLING_WRITE')")
    public ResponseEntity<BaseResponseDTO<InsuranceClaimDTO>> updateClaimStatus(
            @PathVariable UUID id, @Valid @RequestBody UpdateClaimStatusRequest request) {
        return ResponseEntity.ok(BaseResponseDTO.success("Claim status updated", insuranceClaimService.updateClaimStatus(id, request)));
    }
}
