package com.act.hospitalmanagementsystem.hr.dto;

import com.act.hospitalmanagementsystem.hr.enums.PerformanceRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReviewDTO {
    private UUID id;
    private UUID employeeId;
    private UUID reviewerId;
    private LocalDate reviewPeriodStart;
    private LocalDate reviewPeriodEnd;
    private LocalDate reviewDate;
    private PerformanceRating rating;
    private String goalsAchieved;
    private String areasForImprovement;
    private String strengths;
    private String comments;
    private String employeeComments;
    private String developmentPlan;
    private LocalDateTime createdAt;
    private String createdBy;
}
