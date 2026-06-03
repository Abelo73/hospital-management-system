package com.act.hospitalmanagementsystem.nursing.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.nursing.dto.CreateMedicationAdministrationRequest;
import com.act.hospitalmanagementsystem.nursing.dto.MedicationAdministrationDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateMedicationAdministrationRequest;
import com.act.hospitalmanagementsystem.nursing.enums.AdministrationStatus;
import com.act.hospitalmanagementsystem.nursing.service.MedicationAdministrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/nursing/medication-administrations")
@RequiredArgsConstructor
public class MedicationAdministrationController {

    private final MedicationAdministrationService medicationAdministrationService;

    @PostMapping
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<MedicationAdministrationDTO>> createMedicationAdministration(@Valid @RequestBody CreateMedicationAdministrationRequest request) {
        MedicationAdministrationDTO administration = medicationAdministrationService.createMedicationAdministration(request);
        return ResponseEntity.ok(BaseResponseDTO.success(administration));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<MedicationAdministrationDTO>> getMedicationAdministrationById(@PathVariable UUID id) {
        MedicationAdministrationDTO administration = medicationAdministrationService.getMedicationAdministrationById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(administration));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<MedicationAdministrationDTO>>> getMedicationAdministrationsByPatientId(@PathVariable UUID patientId) {
        List<MedicationAdministrationDTO> administrations = medicationAdministrationService.getMedicationAdministrationsByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(administrations));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicationAdministrationDTO>>> getMedicationAdministrationsByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicationAdministrationDTO> administrations = medicationAdministrationService.getMedicationAdministrationsByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(administrations));
    }

    @GetMapping("/administered-by/{administeredBy}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicationAdministrationDTO>>> getMedicationAdministrationsByAdministeredBy(
            @PathVariable UUID administeredBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicationAdministrationDTO> administrations = medicationAdministrationService.getMedicationAdministrationsByAdministeredBy(administeredBy, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(administrations));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicationAdministrationDTO>>> getMedicationAdministrationsByStatus(
            @PathVariable AdministrationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicationAdministrationDTO> administrations = medicationAdministrationService.getMedicationAdministrationsByStatus(status, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(administrations));
    }

    @GetMapping("/scheduled/{date}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<MedicationAdministrationDTO>>> getScheduledMedications(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<MedicationAdministrationDTO> administrations = medicationAdministrationService.getScheduledMedications(date);
        return ResponseEntity.ok(BaseResponseDTO.success(administrations));
    }

    @GetMapping("/overdue/{date}/{time}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<MedicationAdministrationDTO>>> getOverdueMedications(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time
    ) {
        List<MedicationAdministrationDTO> administrations = medicationAdministrationService.getOverdueMedications(date, time);
        return ResponseEntity.ok(BaseResponseDTO.success(administrations));
    }

    @GetMapping("/patient/{patientId}/date-range")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<MedicationAdministrationDTO>>> getMedicationAdministrationsByDateRange(
            @PathVariable UUID patientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicationAdministrationDTO> administrations = medicationAdministrationService.getMedicationAdministrationsByDateRange(patientId, startDate, endDate, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(administrations));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<MedicationAdministrationDTO>> updateMedicationAdministration(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMedicationAdministrationRequest request
    ) {
        MedicationAdministrationDTO administration = medicationAdministrationService.updateMedicationAdministration(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(administration));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteMedicationAdministration(@PathVariable UUID id) {
        medicationAdministrationService.deleteMedicationAdministration(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
