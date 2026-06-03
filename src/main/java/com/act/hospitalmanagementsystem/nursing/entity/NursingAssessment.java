package com.act.hospitalmanagementsystem.nursing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.nursing.enums.AssessmentType;
import com.act.hospitalmanagementsystem.nursing.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "nursing_assessments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NursingAssessment extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "admission_id")
    private UUID admissionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "assessment_type", nullable = false, length = 50)
    private AssessmentType assessmentType;

    @Column(name = "assessment_date", nullable = false)
    private LocalDate assessmentDate;

    @Column(name = "assessment_time", nullable = false)
    private LocalTime assessmentTime;

    @Column(name = "nurse_id")
    private UUID nurseId;

    @Column(name = "general_appearance", length = 500)
    private String generalAppearance;

    @Column(name = "mental_status", length = 500)
    private String mentalStatus;

    @Column(name = "skin_condition", length = 500)
    private String skinCondition;

    @Column(name = "pain_score")
    private Integer painScore;

    @Column(name = "pain_location", length = 200)
    private String painLocation;

    @Column(name = "pain_character", length = 200)
    private String painCharacter;

    @Enumerated(EnumType.STRING)
    @Column(name = "fall_risk", length = 50)
    private RiskLevel fallRisk;

    @Enumerated(EnumType.STRING)
    @Column(name = "pressure_ulcer_risk", length = 50)
    private RiskLevel pressureUlcerRisk;

    @Column(name = "nutritional_status", length = 500)
    private String nutritionalStatus;

    @Column(name = "mobility", length = 500)
    private String mobility;

    @Column(name = "elimination", length = 500)
    private String elimination;

    @Column(name = "sleep_pattern", length = 500)
    private String sleepPattern;

    @Column(name = "psychosocial", length = 500)
    private String psychosocial;

    @Column(name = "spiritual_needs", length = 500)
    private String spiritualNeeds;

    @Column(name = "cultural_considerations", length = 500)
    private String culturalConsiderations;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
