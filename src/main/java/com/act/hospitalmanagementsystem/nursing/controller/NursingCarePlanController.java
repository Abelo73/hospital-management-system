package com.act.hospitalmanagementsystem.nursing.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingCarePlanRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingCarePlanDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingCarePlanRequest;
import com.act.hospitalmanagementsystem.nursing.enums.CarePlanStatus;
import com.act.hospitalmanagementsystem.nursing.enums.CarePlanType;
import com.act.hospitalmanagementsystem.nursing.service.NursingCarePlanService;
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
@RequestMapping("/nursing/care-plans")
@RequiredArgsConstructor
public class NursingCarePlanController {

    private final NursingCarePlanService nursingCarePlanService;

    @PostMapping
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<NursingCarePlanDTO>> createCarePlan(@Valid @RequestBody CreateNursingCarePlanRequest request) {
        NursingCarePlanDTO carePlan = nursingCarePlanService.createCarePlan(request);
        return ResponseEntity.ok(BaseResponseDTO.success(carePlan));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<NursingCarePlanDTO>> getCarePlanById(@PathVariable UUID id) {
        NursingCarePlanDTO carePlan = nursingCarePlanService.getCarePlanById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(carePlan));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<List<NursingCarePlanDTO>>> getCarePlansByPatientId(@PathVariable UUID patientId) {
        List<NursingCarePlanDTO> carePlans = nursingCarePlanService.getCarePlansByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(carePlans));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingCarePlanDTO>>> getCarePlansByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingCarePlanDTO> carePlans = nursingCarePlanService.getCarePlansByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(carePlans));
    }

    @GetMapping("/type/{planType}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingCarePlanDTO>>> getCarePlansByType(
            @PathVariable CarePlanType planType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingCarePlanDTO> carePlans = nursingCarePlanService.getCarePlansByType(planType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(carePlans));
    }

    @GetMapping("/patient/{patientId}/type/{planType}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingCarePlanDTO>>> getCarePlansByPatientIdAndType(
            @PathVariable UUID patientId,
            @PathVariable CarePlanType planType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingCarePlanDTO> carePlans = nursingCarePlanService.getCarePlansByPatientIdAndType(patientId, planType, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(carePlans));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingCarePlanDTO>>> getCarePlansByStatus(
            @PathVariable CarePlanStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingCarePlanDTO> carePlans = nursingCarePlanService.getCarePlansByStatus(status, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(carePlans));
    }

    @GetMapping("/nurse/{primaryNurseId}")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingCarePlanDTO>>> getCarePlansByPrimaryNurse(
            @PathVariable UUID primaryNurseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingCarePlanDTO> carePlans = nursingCarePlanService.getCarePlansByPrimaryNurse(primaryNurseId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(carePlans));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('NURSING_READ')")
    public ResponseEntity<BaseResponseDTO<Page<NursingCarePlanDTO>>> searchCarePlans(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NursingCarePlanDTO> carePlans = nursingCarePlanService.searchCarePlans(searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(carePlans));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_WRITE')")
    public ResponseEntity<BaseResponseDTO<NursingCarePlanDTO>> updateCarePlan(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateNursingCarePlanRequest request
    ) {
        NursingCarePlanDTO carePlan = nursingCarePlanService.updateCarePlan(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(carePlan));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NURSING_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteCarePlan(@PathVariable UUID id) {
        nursingCarePlanService.deleteCarePlan(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
