package com.act.hospitalmanagementsystem.medical.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.medical.dto.AllergyDTO;
import com.act.hospitalmanagementsystem.medical.dto.CreateAllergyRequest;
import com.act.hospitalmanagementsystem.medical.dto.UpdateAllergyRequest;
import com.act.hospitalmanagementsystem.medical.service.AllergyService;
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
@RequestMapping("/allergies")
@RequiredArgsConstructor
public class AllergyController {

    private final AllergyService allergyService;

    @PostMapping
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<AllergyDTO>> createAllergy(@Valid @RequestBody CreateAllergyRequest request) {
        AllergyDTO allergy = allergyService.createAllergy(request);
        return ResponseEntity.ok(BaseResponseDTO.success(allergy));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<AllergyDTO>> getAllergyById(@PathVariable UUID id) {
        AllergyDTO allergy = allergyService.getAllergyById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(allergy));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<List<AllergyDTO>>> getAllergiesByPatientId(@PathVariable UUID patientId) {
        List<AllergyDTO> allergies = allergyService.getAllergiesByPatientId(patientId);
        return ResponseEntity.ok(BaseResponseDTO.success(allergies));
    }

    @GetMapping("/patient/{patientId}/paginated")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AllergyDTO>>> getAllergiesByPatientIdPaginated(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "onsetDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AllergyDTO> allergies = allergyService.getAllergiesByPatientId(patientId, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(allergies));
    }

    @GetMapping("/patient/{patientId}/search")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_READ')")
    public ResponseEntity<BaseResponseDTO<Page<AllergyDTO>>> searchPatientAllergies(
            @PathVariable UUID patientId,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "onsetDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AllergyDTO> allergies = allergyService.searchPatientAllergies(patientId, searchTerm, pageable);
        return ResponseEntity.ok(BaseResponseDTO.success(allergies));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_WRITE')")
    public ResponseEntity<BaseResponseDTO<AllergyDTO>> updateAllergy(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAllergyRequest request
    ) {
        AllergyDTO allergy = allergyService.updateAllergy(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success(allergy));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAL_RECORD_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deleteAllergy(@PathVariable UUID id) {
        allergyService.deleteAllergy(id);
        return ResponseEntity.ok(BaseResponseDTO.success(null));
    }
}
