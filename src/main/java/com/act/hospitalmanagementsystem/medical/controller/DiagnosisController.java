package com.act.hospitalmanagementsystem.medical.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.medical.dto.DiagnosisDTO;
import com.act.hospitalmanagementsystem.medical.dto.CreateDiagnosisRequest;
import com.act.hospitalmanagementsystem.medical.dto.UpdateDiagnosisRequest;
import com.act.hospitalmanagementsystem.medical.service.DiagnosisService;
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
@RequestMapping("/diagnoses")
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    @PostMapping
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<DiagnosisDTO>> createDiagnosis(@Valid @RequestBody CreateDiagnosisRequest request) {
        DiagnosisDTO diagnosis = diagnosisService.createDiagnosis(request);
        return ResponseEntity.ok(BaseResponseDTO.success(diagnosis));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<DiagnosisDTO>> getDiagnosisById(@PathVariable UUID id) {
        DiagnosisDTO diagnosis = diagnosisService.getDiagnosisById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(diagnosis));
    }

    @GetMapping("/medical-record/{medicalRecordId}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<List<DiagnosisDTO>>> getDiagnosesByMedicalRecordId(@PathVariable UUID medicalRecordId) {
        List<DiagnosisDTO> diagnoses = diagnosisService.getDiagnosesByMedicalRecordId(medicalRecordId);
        return ResponseEntity.ok(BaseResponseDTO.success(diagnoses));
    }

    @GetMapping("/medical-record/{medicalRecordId}/paginated")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<DiagnosisDTO>>> getDiagnosesByMedicalRecordIdPaginated(
            @PathVariable UUID medicalRecordId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "diagnosisDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DiagnosisDTO> diagnoses = diagnosisService.getDiagnosesByMedicalRecordId(medicalRecordId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(diagnoses));
    }

    @GetMapping("/medical-record/{medicalRecordId}/search")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<DiagnosisDTO>>> searchMedicalRecordDiagnoses(
            @PathVariable UUID medicalRecordId,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "diagnosisDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DiagnosisDTO> diagnoses = diagnosisService.searchMedicalRecordDiagnoses(medicalRecordId, searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(diagnoses));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<DiagnosisDTO>> updateDiagnosis(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDiagnosisRequest request
    ) {
        DiagnosisDTO diagnosis = diagnosisService.updateDiagnosis(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(diagnosis));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteDiagnosis(@PathVariable UUID id) {
        diagnosisService.deleteDiagnosis(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
