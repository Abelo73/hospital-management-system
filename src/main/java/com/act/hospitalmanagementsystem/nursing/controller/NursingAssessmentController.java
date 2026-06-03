package com.act.hospitalmanagementsystem.nursing.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingAssessmentRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingAssessmentDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingAssessmentRequest;
import com.act.hospitalmanagementsystem.nursing.enums.AssessmentType;
import com.act.hospitalmanagementsystem.nursing.service.NursingAssessmentService;
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
@RequestMapping("/nursing/assessments")
@RequiredArgsConstructor
public class NursingAssessmentController {

    private final NursingAssessmentService nursingAssessmentService;

    @PostMapping
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<NursingAssessmentDTO>> createAssessment(@Valid @RequestBody CreateNursingAssessmentRequest request) {
        NursingAssessmentDTO assessment = nursingAssessmentService.createAssessment(request);
        return ResponseEntity.ok(BaseResponseDTO.success(assessment));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<NursingAssessmentDTO>> getAssessmentById(@PathVariable UUID id) {
        NursingAssessmentDTO assessment = nursingAssessmentService.getAssessmentById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(assessment));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<NursingAssessmentDTO>>> getAssessmentsByPatientId(@PathVariable UUID patientId) {
        List<NursingAssessmentDTO> assessments = nursingAssessmentService.getAssessmentsByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(assessments));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingAssessmentDTO>>> getAssessmentsByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assessmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingAssessmentDTO> assessments = nursingAssessmentService.getAssessmentsByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(assessments));
    }

    @GetMapping("/type/{assessmentType}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingAssessmentDTO>>> getAssessmentsByType(
            @PathVariable AssessmentType assessmentType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assessmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingAssessmentDTO> assessments = nursingAssessmentService.getAssessmentsByType(assessmentType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(assessments));
    }

    @GetMapping("/patient/{patientId}/type/{assessmentType}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingAssessmentDTO>>> getAssessmentsByPatientIdAndType(
            @PathVariable UUID patientId,
            @PathVariable AssessmentType assessmentType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assessmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingAssessmentDTO> assessments = nursingAssessmentService.getAssessmentsByPatientIdAndType(patientId, assessmentType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(assessments));
    }

    @GetMapping("/nurse/{nurseId}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingAssessmentDTO>>> getAssessmentsByNurseId(
            @PathVariable UUID nurseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assessmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingAssessmentDTO> assessments = nursingAssessmentService.getAssessmentsByNurseId(nurseId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(assessments));
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<NursingAssessmentDTO>>> getAssessmentsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<NursingAssessmentDTO> assessments = nursingAssessmentService.getAssessmentsByDate(date);
        return ResponseEntity.ok(BaseResponseDTO.success(assessments));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingAssessmentDTO>>> searchAssessments(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "assessmentDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingAssessmentDTO> assessments = nursingAssessmentService.searchAssessments(searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(assessments));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<NursingAssessmentDTO>> updateAssessment(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateNursingAssessmentRequest request
    ) {
        NursingAssessmentDTO assessment = nursingAssessmentService.updateAssessment(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(assessment));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteAssessment(@PathVariable UUID id) {
        nursingAssessmentService.deleteAssessment(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
