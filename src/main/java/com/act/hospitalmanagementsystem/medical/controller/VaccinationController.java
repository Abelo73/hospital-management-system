package com.act.hospitalmanagementsystem.medical.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.medical.dto.CreateVaccinationRequest;
import com.act.hospitalmanagementsystem.medical.dto.UpdateVaccinationRequest;
import com.act.hospitalmanagementsystem.medical.dto.VaccinationDTO;
import com.act.hospitalmanagementsystem.medical.service.VaccinationService;
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
@RequestMapping("/vaccinations")
@RequiredArgsConstructor
public class VaccinationController {

    private final VaccinationService vaccinationService;

    @PostMapping
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<VaccinationDTO>> createVaccination(@Valid @RequestBody CreateVaccinationRequest request) {
        VaccinationDTO vaccination = vaccinationService.createVaccination(request);
        return ResponseEntity.ok(BaseResponseDTO.success(vaccination));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<VaccinationDTO>> getVaccinationById(@PathVariable UUID id) {
        VaccinationDTO vaccination = vaccinationService.getVaccinationById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(vaccination));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<List<VaccinationDTO>>> getVaccinationsByPatientId(@PathVariable UUID patientId) {
        List<VaccinationDTO> vaccinations = vaccinationService.getVaccinationsByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(vaccinations));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<VaccinationDTO>>> getVaccinationsByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "administrationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VaccinationDTO> vaccinations = vaccinationService.getVaccinationsByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(vaccinations));
    }

    @GetMapping("/patient/{patientId}/search")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<VaccinationDTO>>> searchPatientVaccinations(
            @PathVariable UUID patientId,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "administrationDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VaccinationDTO> vaccinations = vaccinationService.searchPatientVaccinations(patientId, searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(vaccinations));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<VaccinationDTO>> updateVaccination(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateVaccinationRequest request
    ) {
        VaccinationDTO vaccination = vaccinationService.updateVaccination(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(vaccination));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteVaccination(@PathVariable UUID id) {
        vaccinationService.deleteVaccination(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
