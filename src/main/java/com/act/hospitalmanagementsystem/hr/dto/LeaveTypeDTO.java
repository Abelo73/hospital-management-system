package com.act.hospitalmanagementsystem.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveTypeDTO {
    private UUID id;
    private String name;
    private String code;
    private Integer annualDays;
    private Boolean isPaid;
    private Boolean requiresApproval;
    private Boolean requiresAttachment;
    private Boolean isGenderSpecific;
    private String applicableGender;
    private Integer maxCarryoverDays;
    private Integer minServiceMonthsRequired;
    private String accrualFrequency;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private String createdBy;
}
