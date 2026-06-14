package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.TrainingDTO;
import com.act.hospitalmanagementsystem.hr.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/hr/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<TrainingDTO>> createTraining(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        String trainingName = (String) request.get("trainingName");
        String description = (String) request.get("description");
        String trainingType = (String) request.get("trainingType");
        String startDate = (String) request.get("startDate");
        String endDate = (String) request.get("endDate");
        String location = (String) request.get("location");
        String instructor = (String) request.get("instructor");
        Double cost = request.get("cost") != null ? (Double) request.get("cost") : null;
        Integer maxParticipants = request.get("maxParticipants") != null ? (Integer) request.get("maxParticipants") : null;
        String createdBy = authentication.getName();

        BaseResponseDTO<TrainingDTO> response = trainingService.createTraining(
                trainingName, description, trainingType, startDate, endDate, location, instructor, cost, maxParticipants, createdBy);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<TrainingDTO>>> getTrainings(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        BaseResponseDTO<List<TrainingDTO>> response = trainingService.getTrainings(status, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{trainingId}/enroll")
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<Void>> enrollEmployee(
            @PathVariable UUID trainingId,
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        UUID employeeId = UUID.fromString(request.get("employeeId"));
        String createdBy = authentication.getName();

        BaseResponseDTO<Void> response = trainingService.enrollEmployee(trainingId, employeeId, createdBy);
        return ResponseEntity.ok(response);
    }
}
