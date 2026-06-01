package com.act.hospitalmanagementsystem.medical.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.medical.dto.CreateMedicalRecordRequest;
import com.act.hospitalmanagementsystem.medical.dto.MedicalRecordDTO;
import com.act.hospitalmanagementsystem.medical.dto.UpdateMedicalRecordRequest;
import com.act.hospitalmanagementsystem.medical.enums.RecordStatus;
import com.act.hospitalmanagementsystem.medical.enums.RecordType;
import com.act.hospitalmanagementsystem.medical.service.MedicalRecordService;
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
@RequestMapping("/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<MedicalRecordDTO>> createMedicalRecord(@Valid @RequestBody CreateMedicalRecordRequest request) {
        MedicalRecordDTO medicalRecord = medicalRecordService.createMedicalRecord(request);
        return ResponseEntity.ok(BaseResponseDTO.success(medicalRecord));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<MedicalRecordDTO>> getMedicalRecordById(@PathVariable UUID id) {
        MedicalRecordDTO medicalRecord = medicalRecordService.getMedicalRecordById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(medicalRecord));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<List<MedicalRecordDTO>>> getMedicalRecordsByPatientId(@PathVariable UUID patientId) {
        List<MedicalRecordDTO> medicalRecords = medicalRecordService.getMedicalRecordsByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(medicalRecords));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicalRecordDTO>>> getMedicalRecordsByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicalRecordDTO> medicalRecords = medicalRecordService.getMedicalRecordsByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(medicalRecords));
    }

    @GetMapping("/type/{recordType}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicalRecordDTO>>> getMedicalRecordsByType(
            @PathVariable RecordType recordType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicalRecordDTO> medicalRecords = medicalRecordService.getMedicalRecordsByType(recordType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(medicalRecords));
    }

    @GetMapping("/patient/{patientId}/type/{recordType}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicalRecordDTO>>> getMedicalRecordsByPatientIdAndType(
            @PathVariable UUID patientId,
            @PathVariable RecordType recordType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicalRecordDTO> medicalRecords = medicalRecordService.getMedicalRecordsByPatientIdAndType(patientId, recordType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(medicalRecords));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicalRecordDTO>>> getMedicalRecordsByStatus(
            @PathVariable RecordStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicalRecordDTO> medicalRecords = medicalRecordService.getMedicalRecordsByStatus(status, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(medicalRecords));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicalRecordDTO>>> searchMedicalRecords(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicalRecordDTO> medicalRecords = medicalRecordService.searchMedicalRecords(searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(medicalRecords));
    }

    @GetMapping("/patient/{patientId}/search")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicalRecordDTO>>> searchPatientMedicalRecords(
            @PathVariable UUID patientId,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicalRecordDTO> medicalRecords = medicalRecordService.searchPatientMedicalRecords(patientId, searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(medicalRecords));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<MedicalRecordDTO>> updateMedicalRecord(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMedicalRecordRequest request
    ) {
        MedicalRecordDTO medicalRecord = medicalRecordService.updateMedicalRecord(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(medicalRecord));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteMedicalRecord(@PathVariable UUID id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
