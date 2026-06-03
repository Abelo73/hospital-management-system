package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.ShiftType;
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
public class UpdateFluidBalanceRequest {
    private UUID admissionId;

    @NotNull(message = "Record date is required")
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

    @Size(max = 5000, message = "Notes must not exceed 5000 characters")
    private String notes;
}
