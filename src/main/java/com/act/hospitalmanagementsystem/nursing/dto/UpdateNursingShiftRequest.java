package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.ShiftRecordStatus;
import com.act.hospitalmanagementsystem.nursing.enums.ShiftType;
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
public class UpdateNursingShiftRequest {
    @NotNull(message = "Shift type is required")
    private ShiftType shiftType;

    @Size(max = 100, message = "Unit must not exceed 100 characters")
    private String unit;

    private UUID nurseId;

    private UUID chargeNurseId;

    private Integer patientCount;

    @Size(max = 5000, message = "Patient IDs must not exceed 5000 characters")
    private String patientIds;

    private LocalTime shiftStartTime;

    private LocalTime shiftEndTime;

    @NotNull(message = "Status is required")
    private ShiftRecordStatus status;

    @Size(max = 5000, message = "Handoff notes must not exceed 5000 characters")
    private String handoffNotes;

    @Size(max = 5000, message = "Incidents must not exceed 5000 characters")
    private String incidents;
}
