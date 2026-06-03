package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.CarePlanStatus;
import com.act.hospitalmanagementsystem.nursing.enums.CarePlanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NursingCarePlanDTO {
    private UUID id;
    private UUID patientId;
    private UUID admissionId;
    private String planName;
    private CarePlanType planType;
    private LocalDate startDate;
    private LocalDate endDate;
    private CarePlanStatus status;
    private UUID primaryNurseId;
    private String assessment;
    private String nursingDiagnosis;
    private String goals;
    private String interventions;
    private String evaluation;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}
