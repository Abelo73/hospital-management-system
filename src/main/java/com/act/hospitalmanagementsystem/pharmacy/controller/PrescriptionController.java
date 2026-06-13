package com.act.hospitalmanagementsystem.pharmacy.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.pharmacy.dto.PrescriptionDTO;
import com.act.hospitalmanagementsystem.pharmacy.enums.PrescriptionStatus;
import com.act.hospitalmanagementsystem.pharmacy.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pharmacy/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping
    @PreAuthorize("hasAuthority('PHARMACY_READ')")
    public ResponseEntity<BaseResponseDTO<List<PrescriptionDTO>>> getPrescriptions(
            @RequestParam(required = false) UUID patientId,
            @RequestParam(required = false) UUID doctorId,
            @RequestParam(required = false) PrescriptionStatus status,
            Pageable pageable) {
        BaseResponseDTO<List<PrescriptionDTO>> response = prescriptionService.getPrescriptions(patientId, doctorId, status, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{prescriptionNumber}")
    @PreAuthorize("hasAuthority('PHARMACY_READ')")
    public ResponseEntity<BaseResponseDTO<PrescriptionDTO>> getPrescriptionByNumber(@PathVariable String prescriptionNumber) {
        BaseResponseDTO<PrescriptionDTO> response = prescriptionService.getPrescriptionByNumber(prescriptionNumber);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/validate")
    @PreAuthorize("hasAuthority('PHARMACY_WRITE')")
    public ResponseEntity<BaseResponseDTO<PrescriptionDTO>> validatePrescription(
            @PathVariable UUID id,
            @RequestBody java.util.Map<String, Object> request,
            Authentication authentication) {
        Boolean validated = (Boolean) request.get("validated");
        String notes = (String) request.get("notes");
        String validatedBy = authentication.getName();

        BaseResponseDTO<PrescriptionDTO> response = prescriptionService.validatePrescription(id, validated, notes, validatedBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/process")
    @PreAuthorize("hasAuthority('PHARMACY_WRITE')")
    public ResponseEntity<BaseResponseDTO<PrescriptionDTO>> processPrescription(
            @PathVariable UUID id,
            @RequestBody List<Object> items,
            Authentication authentication) {
        String updatedBy = authentication.getName();
        BaseResponseDTO<PrescriptionDTO> response = prescriptionService.processPrescription(id, items, updatedBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('PHARMACY_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> cancelPrescription(
            @PathVariable UUID id,
            @RequestBody java.util.Map<String, String> request,
            Authentication authentication) {
        String reason = request.get("reason");
        String updatedBy = authentication.getName();

        BaseResponseDTO<Void> response = prescriptionService.cancelPrescription(id, reason, updatedBy);
        return ResponseEntity.ok(response);
    }
}
