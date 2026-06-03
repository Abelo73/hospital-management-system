package com.act.hospitalmanagementsystem.nursing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftType;
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
@Table(name = "fluid_balance")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FluidBalance extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "admission_id")
    private UUID admissionId;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift", length = 50)
    private ShiftType shift;

    @Column(name = "recorded_by")
    private UUID recordedBy;

    @Column(name = "oral_intake")
    private BigDecimal oralIntake;

    @Column(name = "iv_intake")
    private BigDecimal ivIntake;

    @Column(name = "iv_medication_intake")
    private BigDecimal ivMedicationIntake;

    @Column(name = "tube_feeding_intake")
    private BigDecimal tubeFeedingIntake;

    @Column(name = "total_intake")
    private BigDecimal totalIntake;

    @Column(name = "urine_output")
    private BigDecimal urineOutput;

    @Column(name = "stool_output")
    private BigDecimal stoolOutput;

    @Column(name = "emesis_output")
    private BigDecimal emesisOutput;

    @Column(name = "drainage_output")
    private BigDecimal drainageOutput;

    @Column(name = "other_output")
    private BigDecimal otherOutput;

    @Column(name = "total_output")
    private BigDecimal totalOutput;

    @Column(name = "net_balance")
    private BigDecimal netBalance;

    @Column(name = "fluid_restriction")
    private BigDecimal fluidRestriction;

    @Column(name = "is_restriction_exceeded")
    private Boolean isRestrictionExceeded;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
