package com.act.hospitalmanagementsystem.nursing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftRecordStatus;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftType;
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
@Table(name = "nursing_shifts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NursingShift extends BaseEntity {

    @Column(name = "shift_date", nullable = false)
    private LocalDate shiftDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift_type", nullable = false, length = 50)
    private ShiftType shiftType;

    @Column(name = "unit", length = 100)
    private String unit;

    @Column(name = "nurse_id")
    private UUID nurseId;

    @Column(name = "charge_nurse_id")
    private UUID chargeNurseId;

    @Column(name = "patient_count")
    private Integer patientCount;

    @Column(name = "patient_ids", columnDefinition = "TEXT")
    private String patientIds;

    @Column(name = "shift_start_time")
    private LocalTime shiftStartTime;

    @Column(name = "shift_end_time")
    private LocalTime shiftEndTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ShiftRecordStatus status;

    @Column(name = "handoff_notes", columnDefinition = "TEXT")
    private String handoffNotes;

    @Column(name = "incidents", columnDefinition = "TEXT")
    private String incidents;
}
