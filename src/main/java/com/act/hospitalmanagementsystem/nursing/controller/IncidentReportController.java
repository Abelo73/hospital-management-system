package com.act.hospitalmanagementsystem.nursing.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.nursing.dto.CreateIncidentReportRequest;
import com.act.hospitalmanagementsystem.nursing.dto.IncidentReportDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateIncidentReportRequest;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentSeverity;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentStatus;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentType;
import com.act.hospitalmanagementsystem.nursing.service.IncidentReportService;
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
import java.util.UUID;

@RestController
@RequestMapping("/nursing/incident-reports")
@RequiredArgsConstructor
public class IncidentReportController {

    private final IncidentReportService incidentReportService;

    @PostMapping
    public ResponseEntity<BaseResponseDTO<IncidentReportDTO>> createIncidentReport(@Valid @RequestBody CreateIncidentReportRequest request) {
        IncidentReportDTO incidentReport = incidentReportService.createIncidentReport(request);
        return ResponseEntity.ok(BaseResponseDTO.success(incidentReport));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<IncidentReportDTO>> getIncidentReportById(@PathVariable UUID id) {
        IncidentReportDTO incidentReport = incidentReportService.getIncidentReportById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(incidentReport));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<BaseResponseDTO<Page<IncidentReportDTO>>> getIncidentReportsByPatientId(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "incidentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IncidentReportDTO> incidentReports = incidentReportService.getIncidentReportsByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(incidentReports));
    }

    @GetMapping("/type/{incidentType}")
    public ResponseEntity<BaseResponseDTO<Page<IncidentReportDTO>>> getIncidentReportsByType(
            @PathVariable IncidentType incidentType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "incidentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IncidentReportDTO> incidentReports = incidentReportService.getIncidentReportsByType(incidentType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(incidentReports));
    }

    @GetMapping("/severity/{severity}")
    public ResponseEntity<BaseResponseDTO<Page<IncidentReportDTO>>> getIncidentReportsBySeverity(
            @PathVariable IncidentSeverity severity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "incidentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IncidentReportDTO> incidentReports = incidentReportService.getIncidentReportsBySeverity(severity, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(incidentReports));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<BaseResponseDTO<Page<IncidentReportDTO>>> getIncidentReportsByStatus(
            @PathVariable IncidentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "incidentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IncidentReportDTO> incidentReports = incidentReportService.getIncidentReportsByStatus(status, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(incidentReports));
    }

    @GetMapping("/reported-by/{reportedBy}")
    public ResponseEntity<BaseResponseDTO<Page<IncidentReportDTO>>> getIncidentReportsByReportedBy(
            @PathVariable UUID reportedBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "incidentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IncidentReportDTO> incidentReports = incidentReportService.getIncidentReportsByReportedBy(reportedBy, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(incidentReports));
    }

    @GetMapping("/date-range")
    public ResponseEntity<BaseResponseDTO<Page<IncidentReportDTO>>> getIncidentReportsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "incidentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IncidentReportDTO> incidentReports = incidentReportService.getIncidentReportsByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(incidentReports));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponseDTO<Page<IncidentReportDTO>>> searchIncidentReports(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "incidentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<IncidentReportDTO> incidentReports = incidentReportService.searchIncidentReports(searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(incidentReports));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<IncidentReportDTO>> updateIncidentReport(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateIncidentReportRequest request
    ) {
        IncidentReportDTO incidentReport = incidentReportService.updateIncidentReport(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(incidentReport));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<Void>> deleteIncidentReport(@PathVariable UUID id) {
        incidentReportService.deleteIncidentReport(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
