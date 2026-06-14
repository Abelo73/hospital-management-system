package com.act.hospitalmanagementsystem.hr.controller;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.PerformanceReviewDTO;
import com.act.hospitalmanagementsystem.hr.enums.PerformanceRating;
import com.act.hospitalmanagementsystem.hr.service.PerformanceReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/hr/performance-reviews")
@RequiredArgsConstructor
public class PerformanceReviewController {

    private final PerformanceReviewService performanceReviewService;

    @PostMapping
    @PreAuthorize("hasAuthority('HR_WRITE')")
    public ResponseEntity<BaseResponseDTO<PerformanceReviewDTO>> createPerformanceReview(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        UUID employeeId = UUID.fromString((String) request.get("employeeId"));
        UUID reviewerId = UUID.fromString((String) request.get("reviewerId"));
        String reviewPeriodStart = (String) request.get("reviewPeriodStart");
        String reviewPeriodEnd = (String) request.get("reviewPeriodEnd");
        String reviewDate = (String) request.get("reviewDate");
        PerformanceRating rating = PerformanceRating.valueOf((String) request.get("rating"));
        String goalsAchieved = (String) request.get("goalsAchieved");
        String areasForImprovement = (String) request.get("areasForImprovement");
        String strengths = (String) request.get("strengths");
        String comments = (String) request.get("comments");
        String createdBy = authentication.getName();

        BaseResponseDTO<PerformanceReviewDTO> response = performanceReviewService.createPerformanceReview(
                employeeId, reviewerId, reviewPeriodStart, reviewPeriodEnd, reviewDate, rating,
                goalsAchieved, areasForImprovement, strengths, comments, createdBy);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('HR_READ')")
    public ResponseEntity<BaseResponseDTO<List<PerformanceReviewDTO>>> getPerformanceReviews(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) UUID reviewerId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        BaseResponseDTO<List<PerformanceReviewDTO>> response = performanceReviewService.getPerformanceReviews(employeeId, reviewerId, startDate, endDate);
        return ResponseEntity.ok(response);
    }
}
