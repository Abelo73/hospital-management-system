package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.ShiftType;
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
public class FluidBalanceDTO {
    private UUID id;
    private UUID patientId;
    private UUID admissionId;
    private LocalDate recordDate;
    private ShiftType shift;
    private UUID recordedBy;
    private BigDecimal oralIntake;
    private BigDecimal ivIntake;
    private BigDecimal ivMedicationIntake;
    private BigDecimal tubeFeedingIntake;
    private BigDecimal totalIntake;
    private BigDecimal urineOutput;
    private BigDecimal stoolOutput;
    private BigDecimal emesisOutput;
    private BigDecimal drainageOutput;
    private BigDecimal otherOutput;
    private BigDecimal totalOutput;
    private BigDecimal netBalance;
    private BigDecimal fluidRestriction;
    private Boolean isRestrictionExceeded;
    private String notes;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}
