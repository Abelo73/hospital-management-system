package com.act.hospitalmanagementsystem.laboratory.controller;

import com.act.hospitalmanagementsystem.laboratory.dto.CreateLabTestRequest;
import com.act.hospitalmanagementsystem.laboratory.dto.LabTestDTO;
import com.act.hospitalmanagementsystem.laboratory.dto.LabTestRequestDTO;
import com.act.hospitalmanagementsystem.laboratory.service.LaboratoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/laboratory")
@RequiredArgsConstructor
public class LaboratoryController {

    private final LaboratoryService laboratoryService;

    @GetMapping("/tests")
    public ResponseEntity<List<LabTestDTO>> getAllTests() {
        return ResponseEntity.ok(laboratoryService.getAllTests());
    }

    @PostMapping("/requests")
    public ResponseEntity<LabTestRequestDTO> createRequest(@RequestBody CreateLabTestRequest request) {
        return new ResponseEntity<>(laboratoryService.createRequest(request), HttpStatus.CREATED);
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<LabTestRequestDTO> getRequest(@PathVariable UUID id) {
        return ResponseEntity.ok(laboratoryService.getRequest(id));
    }

    @GetMapping("/requests/pending")
    public ResponseEntity<List<LabTestRequestDTO>> getPendingRequests() {
        return ResponseEntity.ok(laboratoryService.getPendingRequests());
    }

    @GetMapping("/requests/patient/{patientId}")
    public ResponseEntity<List<LabTestRequestDTO>> getPatientRequests(@PathVariable UUID patientId) {
        return ResponseEntity.ok(laboratoryService.getPatientRequests(patientId));
    }
}
