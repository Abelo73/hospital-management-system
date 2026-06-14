package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.RecruitmentDTO;
import com.act.hospitalmanagementsystem.hr.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/hr/recruitment")
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<RecruitmentDTO>> createJobPosting(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        String jobTitle = (String) request.get("jobTitle");
        String department = (String) request.get("department");
        String description = (String) request.get("description");
        String requirements = (String) request.get("requirements");
        String responsibilities = (String) request.get("responsibilities");
        Integer vacancies = (Integer) request.get("vacancies");
        String postingDate = (String) request.get("postingDate");
        String closingDate = (String) request.get("closingDate");
        String salaryRange = (String) request.get("salaryRange");
        String location = (String) request.get("location");
        String employmentType = (String) request.get("employmentType");
        String createdBy = authentication.getName();

        BaseResponseDTO<RecruitmentDTO> response = recruitmentService.createJobPosting(
                jobTitle, department, description, requirements, responsibilities, vacancies,
                postingDate, closingDate, salaryRange, location, employmentType, createdBy);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<RecruitmentDTO>>> getJobPostings(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String department,
            Pageable pageable) {
        BaseResponseDTO<List<RecruitmentDTO>> response = recruitmentService.getJobPostings(status, department, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR_ADMIN')")
    public ResponseEntity<BaseResponseDTO<RecruitmentDTO>> updateJobPosting(
            @PathVariable UUID id,
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        String status = request.get("status");
        String updatedBy = authentication.getName();

        BaseResponseDTO<RecruitmentDTO> response = recruitmentService.updateJobPosting(id, status, updatedBy);
        return ResponseEntity.ok(response);
    }
}
