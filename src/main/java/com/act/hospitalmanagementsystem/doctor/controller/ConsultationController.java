package com.act.hospitalmanagementsystem.doctor.controller;

import com.act.hospitalmanagementsystem.doctor.dto.ConsultationDTO;
import com.act.hospitalmanagementsystem.doctor.dto.CreateConsultationRequest;
import com.act.hospitalmanagementsystem.doctor.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<ConsultationDTO> createConsultation(@RequestBody CreateConsultationRequest request) {
        return new ResponseEntity<>(consultationService.createConsultation(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationDTO> getConsultation(@PathVariable UUID id) {
        return ResponseEntity.ok(consultationService.getConsultation(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ConsultationDTO>> getPatientConsultations(@PathVariable UUID patientId) {
        return ResponseEntity.ok(consultationService.getPatientConsultations(patientId));
    }

    @PostMapping("/{id}/finalize")
    public ResponseEntity<Void> finalizeConsultation(@PathVariable UUID id) {
        consultationService.finalizeConsultation(id);
        return ResponseEntity.noContent().build();
    }
}
