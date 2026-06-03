package com.act.hospitalmanagementsystem.nursing.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.nursing.dto.CreateVitalSignRequest;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateVitalSignRequest;
import com.act.hospitalmanagementsystem.nursing.dto.VitalSignDTO;
import com.act.hospitalmanagementsystem.nursing.service.VitalSignService;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/nursing/vital-signs")
@RequiredArgsConstructor
public class VitalSignController {

    private final VitalSignService vitalSignService;

    @PostMapping
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<VitalSignDTO>> createVitalSign(@Valid @RequestBody CreateVitalSignRequest request) {
        VitalSignDTO vitalSign = vitalSignService.createVitalSign(request);
        return ResponseEntity.ok(BaseResponseDTO.success(vitalSign));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<VitalSignDTO>> getVitalSignById(@PathVariable UUID id) {
        VitalSignDTO vitalSign = vitalSignService.getVitalSignById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(vitalSign));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<VitalSignDTO>>> getVitalSignsByPatientId(@PathVariable UUID patientId) {
        List<VitalSignDTO> vitalSigns = vitalSignService.getVitalSignsByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(vitalSigns));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<VitalSignDTO>>> getVitalSignsByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordedDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VitalSignDTO> vitalSigns = vitalSignService.getVitalSignsByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(vitalSigns));
    }

    @GetMapping("/recorded-by/{recordedBy}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<VitalSignDTO>>> getVitalSignsByRecordedBy(
            @PathVariable UUID recordedBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordedDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VitalSignDTO> vitalSigns = vitalSignService.getVitalSignsByRecordedBy(recordedBy, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(vitalSigns));
    }

    @GetMapping("/patient/{patientId}/date/{date}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<VitalSignDTO>>> getVitalSignsByPatientIdAndDate(
            @PathVariable UUID patientId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<VitalSignDTO> vitalSigns = vitalSignService.getVitalSignsByPatientIdAndDate(patientId, date);
        return ResponseEntity.ok(BaseResponseDTO.success(vitalSigns));
    }

    @GetMapping("/abnormal")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<VitalSignDTO>>> getAbnormalVitalSigns(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordedDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VitalSignDTO> vitalSigns = vitalSignService.getAbnormalVitalSigns(pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(vitalSigns));
    }

    @GetMapping("/patient/{patientId}/abnormal")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<VitalSignDTO>>> getAbnormalVitalSignsByPatient(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recordedDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VitalSignDTO> vitalSigns = vitalSignService.getAbnormalVitalSignsByPatient(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(vitalSigns));
    }

    @GetMapping("/patient/{patientId}/latest")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<VitalSignDTO>>> getLatestVitalSigns(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<VitalSignDTO> vitalSigns = vitalSignService.getLatestVitalSigns(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(vitalSigns));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<VitalSignDTO>> updateVitalSign(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateVitalSignRequest request
    ) {
        VitalSignDTO vitalSign = vitalSignService.updateVitalSign(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(vitalSign));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteVitalSign(@PathVariable UUID id) {
        vitalSignService.deleteVitalSign(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
