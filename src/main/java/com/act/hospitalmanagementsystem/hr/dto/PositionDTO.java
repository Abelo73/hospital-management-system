package com.act.hospitalmanagementsystem.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {
    private UUID id;
    private String title;
    private String code;
    private UUID departmentId;
    private UUID gradeId;
    private Double minSalary;
    private Double maxSalary;
    private String responsibilities;
    private String requiredSkills;
    private String requiredQualifications;
    private UUID reportingPositionId;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private String createdBy;
}
