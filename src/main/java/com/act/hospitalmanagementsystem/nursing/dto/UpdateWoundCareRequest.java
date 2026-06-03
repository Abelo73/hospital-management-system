package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWoundCareRequest {
    private UUID admissionId;

    @Size(max = 200, message = "Wound location must not exceed 200 characters")
    private String woundLocation;

    private WoundType woundType;

    private WoundStage woundStage;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal depth;

    @Size(max = 500, message = "Undermining must not exceed 500 characters")
    private String undermining;

    private ExudateAmount exudateAmount;

    private ExudateType exudateType;

    @Size(max = 500, message = "Wound bed color must not exceed 500 characters")
    private String woundBedColor;

    @Size(max = 500, message = "Periwound skin must not exceed 500 characters")
    private String periwoundSkin;

    private OdorLevel odor;

    private Integer painScore;

    @Size(max = 5000, message = "Treatment must not exceed 5000 characters")
    private String treatment;

    @Size(max = 200, message = "Dressing type must not exceed 200 characters")
    private String dressingType;

    @NotNull(message = "Assessment date is required")
    private LocalDate assessmentDate;

    private UUID assessedBy;

    @Size(max = 500, message = "Photo URL must not exceed 500 characters")
    private String photoUrl;

    private HealingProgress healingProgress;

    @Size(max = 5000, message = "Notes must not exceed 5000 characters")
    private String notes;
}
