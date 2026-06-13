package com.act.hospitalmanagementsystem.pharmacy.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.pharmacy.dto.DispensingDTO;
import com.act.hospitalmanagementsystem.pharmacy.service.DispensingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pharmacy/dispensing")
@RequiredArgsConstructor
public class DispensingController {

    private final DispensingService dispensingService;

    @PostMapping
    @PreAuthorize("hasAuthority('PHARMACY_WRITE')")
    public ResponseEntity<BaseResponseDTO<DispensingDTO>> createDispensing(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID prescriptionId = UUID.fromString((String) request.get("prescriptionId"));
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
        String createdBy = authentication.getName();

        BaseResponseDTO<DispensingDTO> response = dispensingService.createDispensing(prescriptionId, items, createdBy);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{dispensingNumber}")
    @PreAuthorize("hasAuthority('PHARMACY_READ')")
    public ResponseEntity<BaseResponseDTO<DispensingDTO>> getDispensingByNumber(@PathVariable String dispensingNumber) {
        BaseResponseDTO<DispensingDTO> response = dispensingService.getDispensingByNumber(dispensingNumber);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/complete")
    @PreAuthorize("hasAuthority('PHARMACY_WRITE')")
    public ResponseEntity<BaseResponseDTO<DispensingDTO>> completeDispensing(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        Boolean counselingProvided = (Boolean) request.get("counselingProvided");
        String counselingNotes = (String) request.get("counselingNotes");
        UUID receivedBy = UUID.fromString(authentication.getName());

        BaseResponseDTO<DispensingDTO> response = dispensingService.completeDispensing(id, counselingProvided, counselingNotes, receivedBy);
        return ResponseEntity.ok(response);
    }
}
