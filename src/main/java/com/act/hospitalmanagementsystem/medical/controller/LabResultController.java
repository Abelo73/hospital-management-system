package com.act.hospitalmanagementsystem.medical.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.medical.dto.CreateLabResultRequest;
import com.act.hospitalmanagementsystem.medical.dto.LabResultDTO;
import com.act.hospitalmanagementsystem.medical.dto.UpdateLabResultRequest;
import com.act.hospitalmanagementsystem.medical.enums.LabResultStatus;
import com.act.hospitalmanagementsystem.medical.service.LabResultService;
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
@RequestMapping("/lab-results")
@RequiredArgsConstructor
public class LabResultController {

    private final LabResultService labResultService;

    @PostMapping
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<LabResultDTO>> createLabResult(@Valid @RequestBody CreateLabResultRequest request) {
        LabResultDTO labResult = labResultService.createLabResult(request);
        return ResponseEntity.ok(BaseResponseDTO.success(labResult));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<LabResultDTO>> getLabResultById(@PathVariable UUID id) {
        LabResultDTO labResult = labResultService.getLabResultById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(labResult));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<List<LabResultDTO>>> getLabResultsByPatientId(@PathVariable UUID patientId) {
        List<LabResultDTO> labResults = labResultService.getLabResultsByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(labResults));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<LabResultDTO>>> getLabResultsByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "testDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<LabResultDTO> labResults = labResultService.getLabResultsByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(labResults));
    }

    @GetMapping("/patient/{patientId}/status/{status}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<LabResultDTO>>> getLabResultsByPatientIdAndStatus(
            @PathVariable UUID patientId,
            @PathVariable LabResultStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "testDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<LabResultDTO> labResults = labResultService.getLabResultsByPatientIdAndStatus(patientId, status, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(labResults));
    }

    @GetMapping("/patient/{patientId}/search")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<LabResultDTO>>> searchPatientLabResults(
            @PathVariable UUID patientId,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "testDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<LabResultDTO> labResults = labResultService.searchPatientLabResults(patientId, searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(labResults));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<LabResultDTO>> updateLabResult(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateLabResultRequest request
    ) {
        LabResultDTO labResult = labResultService.updateLabResult(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(labResult));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteLabResult(@PathVariable UUID id) {
        labResultService.deleteLabResult(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
