package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.AssessmentType;
import com.act.hospitalmanagementsystem.nursing.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NursingAssessmentDTO {
    private UUID id;
    private UUID patientId;
    private UUID admissionId;
    private AssessmentType assessmentType;
    private LocalDate assessmentDate;
    private LocalTime assessmentTime;
    private UUID nurseId;
    private String generalAppearance;
    private String mentalStatus;
    private String skinCondition;
    private Integer painScore;
    private String painLocation;
    private String painCharacter;
    private RiskLevel fallRisk;
    private RiskLevel pressureUlcerRisk;
    private String nutritionalStatus;
    private String mobility;
    private String elimination;
    private String sleepPattern;
    private String psychosocial;
    private String spiritualNeeds;
    private String culturalConsiderations;
    private String notes;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}
