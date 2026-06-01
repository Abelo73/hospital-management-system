package com.act.hospitalmanagementsystem.medical.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.medical.dto.CreateMedicationRequest;
import com.act.hospitalmanagementsystem.medical.dto.MedicationDTO;
import com.act.hospitalmanagementsystem.medical.dto.UpdateMedicationRequest;
import com.act.hospitalmanagementsystem.medical.enums.MedicationStatus;
import com.act.hospitalmanagementsystem.medical.service.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<MedicationDTO>> createMedication(@Valid @RequestBody CreateMedicationRequest request) {
        MedicationDTO medication = medicationService.createMedication(request);
        return ResponseEntity.ok(BaseResponseDTO.success(medication));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<MedicationDTO>> getMedicationById(@PathVariable UUID id) {
        MedicationDTO medication = medicationService.getMedicationById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(medication));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<List<MedicationDTO>>> getMedicationsByPatientId(@PathVariable UUID patientId) {
        List<MedicationDTO> medications = medicationService.getMedicationsByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(medications));
    }

    @GetMapping("/patient/{patientId}/active")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<List<MedicationDTO>>> getActiveMedicationsByPatientId(@PathVariable UUID patientId) {
        List<MedicationDTO> medications = medicationService.getActiveMedicationsByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(medications));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicationDTO>>> getMedicationsByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicationDTO> medications = medicationService.getMedicationsByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(medications));
    }

    @GetMapping("/patient/{patientId}/status/{status}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicationDTO>>> getMedicationsByPatientIdAndStatus(
            @PathVariable UUID patientId,
            @PathVariable MedicationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicationDTO> medications = medicationService.getMedicationsByPatientIdAndStatus(patientId, status, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(medications));
    }

    @GetMapping("/patient/{patientId}/search")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicationDTO>>> searchPatientMedications(
            @PathVariable UUID patientId,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicationDTO> medications = medicationService.searchPatientMedications(patientId, searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(medications));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<MedicationDTO>> updateMedication(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMedicationRequest request
    ) {
        MedicationDTO medication = medicationService.updateMedication(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(medication));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteMedication(@PathVariable UUID id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
