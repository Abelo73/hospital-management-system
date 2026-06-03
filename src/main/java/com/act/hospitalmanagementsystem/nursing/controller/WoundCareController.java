package com.act.hospitalmanagementsystem.nursing.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.nursing.dto.CreateWoundCareRequest;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateWoundCareRequest;
import com.act.hospitalmanagementsystem.nursing.dto.WoundCareDTO;
import com.act.hospitalmanagementsystem.nursing.enums.WoundType;
import com.act.hospitalmanagementsystem.nursing.service.WoundCareService;
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
@RequestMapping("/nursing/wound-care")
@RequiredArgsConstructor
public class WoundCareController {

    private final WoundCareService woundCareService;

    @PostMapping
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<WoundCareDTO>> createWoundCare(@Valid @RequestBody CreateWoundCareRequest request) {
        WoundCareDTO woundCare = woundCareService.createWoundCare(request);
        return ResponseEntity.ok(BaseResponseDTO.success(woundCare));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<WoundCareDTO>> getWoundCareById(@PathVariable UUID id) {
        WoundCareDTO woundCare = woundCareService.getWoundCareById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(woundCare));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<WoundCareDTO>>> getWoundCareByPatientId(@PathVariable UUID patientId) {
        List<WoundCareDTO> woundCareList = woundCareService.getWoundCareByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(woundCareList));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<WoundCareDTO>>> getWoundCareByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assessmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WoundCareDTO> woundCareList = woundCareService.getWoundCareByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(woundCareList));
    }

    @GetMapping("/type/{woundType}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<WoundCareDTO>>> getWoundCareByType(
            @PathVariable WoundType woundType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assessmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WoundCareDTO> woundCareList = woundCareService.getWoundCareByType(woundType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(woundCareList));
    }

    @GetMapping("/patient/{patientId}/type/{woundType}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<WoundCareDTO>>> getWoundCareByPatientIdAndType(
            @PathVariable UUID patientId,
            @PathVariable WoundType woundType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assessmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WoundCareDTO> woundCareList = woundCareService.getWoundCareByPatientIdAndType(patientId, woundType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(woundCareList));
    }

    @GetMapping("/assessed-by/{assessedBy}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<WoundCareDTO>>> getWoundCareByAssessedBy(
            @PathVariable UUID assessedBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assessmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WoundCareDTO> woundCareList = woundCareService.getWoundCareByAssessedBy(assessedBy, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(woundCareList));
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<WoundCareDTO>>> getWoundCareByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<WoundCareDTO> woundCareList = woundCareService.getWoundCareByDate(date);
        return ResponseEntity.ok(BaseResponseDTO.success(woundCareList));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<WoundCareDTO>>> searchWoundCare(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assessmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WoundCareDTO> woundCareList = woundCareService.searchWoundCare(searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(woundCareList));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<WoundCareDTO>> updateWoundCare(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateWoundCareRequest request
    ) {
        WoundCareDTO woundCare = woundCareService.updateWoundCare(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(woundCare));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteWoundCare(@PathVariable UUID id) {
        woundCareService.deleteWoundCare(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
