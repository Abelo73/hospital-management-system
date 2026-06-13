package com.act.hospitalmanagementsystem.pharmacy.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.pharmacy.service.PatientMedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pharmacy/patients")
@RequiredArgsConstructor
public class PatientMedicationController {

    private final PatientMedicationService patientMedicationService;

    @GetMapping("/{patientId}/medications")
    @PreAuthorize("hasAuthority('PHARMACY_READ')")
    public ResponseEntity<BaseResponseDTO<List<Object>>> getMedicationHistory(@PathVariable UUID patientId) {
        BaseResponseDTO<List<Object>> response = patientMedicationService.getMedicationHistory(patientId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{patientId}/medications/current")
    @PreAuthorize("hasAuthority('PHARMACY_READ')")
    public ResponseEntity<BaseResponseDTO<List<Object>>> getCurrentMedications(@PathVariable UUID patientId) {
        BaseResponseDTO<List<Object>> response = patientMedicationService.getCurrentMedications(patientId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{patientId}/allergies")
    @PreAuthorize("hasAuthority('PHARMACY_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> addDrugAllergy(
            @PathVariable UUID patientId,
            @RequestBody Map<String, Object> request,
            org.springframework.security.core.Authentication authentication) {
        UUID drugId = UUID.fromString((String) request.get("drugId"));
        String allergen = (String) request.get("allergen");
        String severity = (String) request.get("severity");
        String reaction = (String) request.get("reaction");
        String reportedBy = authentication.getName();

        BaseResponseDTO<Void> response = patientMedicationService.addDrugAllergy(patientId, drugId, allergen, severity, reaction, reportedBy);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{patientId}/adverse-reactions")
    @PreAuthorize("hasAuthority('PHARMACY_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> recordAdverseReaction(
            @PathVariable UUID patientId,
            @RequestBody Map<String, Object> request) {
        UUID drugId = UUID.fromString((String) request.get("drugId"));
        String reaction = (String) request.get("reaction");
        String severity = (String) request.get("severity");
        String onsetDate = (String) request.get("onsetDate");
        String notes = (String) request.get("notes");

        BaseResponseDTO<Void> response = patientMedicationService.recordAdverseReaction(patientId, drugId, reaction, severity, onsetDate, notes);
        return ResponseEntity.ok(response);
    }
}
