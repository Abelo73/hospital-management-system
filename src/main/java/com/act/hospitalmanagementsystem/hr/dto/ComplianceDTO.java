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
public class ComplianceDTO {
    private UUID id;
    private UUID employeeId;
    private String complianceType;
    private String documentName;
    private String documentUrl;
    private String issuingAuthority;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String status;
    private LocalDate reminderDate;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
}
