package com.act.hospitalmanagementsystem.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitsDTO {
    private UUID id;
    private UUID employeeId;
    private String benefitType;
    private String planName;
    private String provider;
    private Double coverageAmount;
    private Double employeeContribution;
    private Double employerContribution;
    private LocalDate enrollmentDate;
    private LocalDate effectiveDate;
    private LocalDate terminationDate;
    private String status;
    private String dependents;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
}
