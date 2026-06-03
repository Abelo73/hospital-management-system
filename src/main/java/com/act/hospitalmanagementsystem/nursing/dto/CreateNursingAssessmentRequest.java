package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.AssessmentType;
import com.act.hospitalmanagementsystem.nursing.enums.RiskLevel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNursingAssessmentRequest {
    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    private UUID admissionId;

    @NotNull(message = "Assessment type is required")
    private AssessmentType assessmentType;

    @NotNull(message = "Assessment date is required")
    private LocalDate assessmentDate;

    @NotNull(message = "Assessment time is required")
    private LocalTime assessmentTime;

    private UUID nurseId;

    @Size(max = 500, message = "General appearance must not exceed 500 characters")
    private String generalAppearance;

    @Size(max = 500, message = "Mental status must not exceed 500 characters")
    private String mentalStatus;

    @Size(max = 500, message = "Skin condition must not exceed 500 characters")
    private String skinCondition;

    private Integer painScore;

    @Size(max = 200, message = "Pain location must not exceed 200 characters")
    private String painLocation;

    @Size(max = 200, message = "Pain character must not exceed 200 characters")
    private String painCharacter;

    private RiskLevel fallRisk;

    private RiskLevel pressureUlcerRisk;

    @Size(max = 500, message = "Nutritional status must not exceed 500 characters")
    private String nutritionalStatus;

    @Size(max = 500, message = "Mobility must not exceed 500 characters")
    private String mobility;

    @Size(max = 500, message = "Elimination must not exceed 500 characters")
    private String elimination;

    @Size(max = 500, message = "Sleep pattern must not exceed 500 characters")
    private String sleepPattern;

    @Size(max = 500, message = "Psychosocial must not exceed 500 characters")
    private String psychosocial;

    @Size(max = 500, message = "Spiritual needs must not exceed 500 characters")
    private String spiritualNeeds;

    @Size(max = 500, message = "Cultural considerations must not exceed 500 characters")
    private String culturalConsiderations;

    @Size(max = 5000, message = "Notes must not exceed 5000 characters")
    private String notes;
}
