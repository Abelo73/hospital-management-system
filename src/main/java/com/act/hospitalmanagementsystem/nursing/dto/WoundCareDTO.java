package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WoundCareDTO {
    private UUID id;
    private UUID patientId;
    private UUID admissionId;
    private String woundLocation;
    private WoundType woundType;
    private WoundStage woundStage;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal depth;
    private String undermining;
    private ExudateAmount exudateAmount;
    private ExudateType exudateType;
    private String woundBedColor;
    private String periwoundSkin;
    private OdorLevel odor;
    private Integer painScore;
    private String treatment;
    private String dressingType;
    private LocalDate assessmentDate;
    private UUID assessedBy;
    private String photoUrl;
    private HealingProgress healingProgress;
    private String notes;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}
