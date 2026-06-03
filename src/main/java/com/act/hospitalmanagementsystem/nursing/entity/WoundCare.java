package com.act.hospitalmanagementsystem.nursing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.nursing.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "wound_care")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WoundCare extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "admission_id")
    private UUID admissionId;

    @Column(name = "wound_location", length = 200)
    private String woundLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "wound_type", length = 50)
    private WoundType woundType;

    @Enumerated(EnumType.STRING)
    @Column(name = "wound_stage", length = 50)
    private WoundStage woundStage;

    @Column(name = "length")
    private BigDecimal length;

    @Column(name = "width")
    private BigDecimal width;

    @Column(name = "depth")
    private BigDecimal depth;

    @Column(name = "undermining", length = 500)
    private String undermining;

    @Enumerated(EnumType.STRING)
    @Column(name = "exudate_amount", length = 50)
    private ExudateAmount exudateAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "exudate_type", length = 50)
    private ExudateType exudateType;

    @Column(name = "wound_bed_color", length = 500)
    private String woundBedColor;

    @Column(name = "periwound_skin", length = 500)
    private String periwoundSkin;

    @Enumerated(EnumType.STRING)
    @Column(name = "odor", length = 50)
    private OdorLevel odor;

    @Column(name = "pain_score")
    private Integer painScore;

    @Column(name = "treatment", columnDefinition = "TEXT")
    private String treatment;

    @Column(name = "dressing_type", length = 200)
    private String dressingType;

    @Column(name = "assessment_date", nullable = false)
    private LocalDate assessmentDate;

    @Column(name = "assessed_by")
    private UUID assessedBy;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "healing_progress", length = 50)
    private HealingProgress healingProgress;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
