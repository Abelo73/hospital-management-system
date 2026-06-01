package com.act.hospitalmanagementsystem.patient.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.patient.dto.CreatePatientRequest;
import com.act.hospitalmanagementsystem.patient.dto.PatientDTO;
import com.act.hospitalmanagementsystem.patient.dto.UpdatePatientRequest;
import com.act.hospitalmanagementsystem.patient.enums.PatientStatus;
import com.act.hospitalmanagementsystem.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @PreAuthorize("hasAuthority('PATIENT_READ')")
    public ResponseEntity<BaseResponseDTO<List<PatientDTO>>> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(BaseResponseDTO.success(patients));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT_READ')")
    public ResponseEntity<BaseResponseDTO<PatientDTO>> getPatientById(@PathVariable UUID id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(BaseResponseDTO.success(patient));
    }

    @GetMapping("/mrn/{medicalRecordNumber}")
    @PreAuthorize("hasAuthority('PATIENT_READ')")
    public ResponseEntity<BaseResponseDTO<PatientDTO>> getPatientByMedicalRecordNumber(@PathVariable String medicalRecordNumber) {
        PatientDTO patient = patientService.getPatientByMedicalRecordNumber(medicalRecordNumber);
        return ResponseEntity.ok(BaseResponseDTO.success(patient));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('PATIENT_READ')")
    public ResponseEntity<BaseResponseDTO<List<PatientDTO>>> searchPatients(@RequestParam String searchTerm) {
        List<PatientDTO> patients = patientService.searchPatients(searchTerm);
        return ResponseEntity.ok(BaseResponseDTO.success(patients));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('PATIENT_READ')")
    public ResponseEntity<BaseResponseDTO<List<PatientDTO>>> getPatientsByStatus(@PathVariable PatientStatus status) {
        List<PatientDTO> patients = patientService.getPatientsByStatus(status);
        return ResponseEntity.ok(BaseResponseDTO.success(patients));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PATIENT_WRITE')")
    public ResponseEntity<BaseResponseDTO<PatientDTO>> createPatient(@Valid @RequestBody CreatePatientRequest request) {
        PatientDTO patient = patientService.createPatient(request);
        return ResponseEntity.ok(BaseResponseDTO.success("Patient created successfully", patient));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT_WRITE')")
    public ResponseEntity<BaseResponseDTO<PatientDTO>> updatePatient(@PathVariable UUID id, @Valid @RequestBody UpdatePatientRequest request) {
        PatientDTO patient = patientService.updatePatient(id, request);
        return ResponseEntity.ok(BaseResponseDTO.success("Patient updated successfully", patient));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT_DELETE')")
    public ResponseEntity<BaseResponseDTO<Void>> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(BaseResponseDTO.success("Patient deleted successfully", null));
    }
}
