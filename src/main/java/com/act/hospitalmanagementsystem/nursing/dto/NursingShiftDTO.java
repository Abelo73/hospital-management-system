package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.ShiftRecordStatus;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftType;
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
public class NursingShiftDTO {
    private UUID id;
    private LocalDate shiftDate;
    private ShiftType shiftType;
    private String unit;
    private UUID nurseId;
    private UUID chargeNurseId;
    private Integer patientCount;
    private String patientIds;
    private LocalTime shiftStartTime;
    private LocalTime shiftEndTime;
    private ShiftRecordStatus status;
    private String handoffNotes;
    private String incidents;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}
