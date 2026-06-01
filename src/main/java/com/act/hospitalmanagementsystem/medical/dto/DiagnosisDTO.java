package com.act.hospitalmanagementsystem.medical.dto;

import com.act.hospitalmanagementsystem.medical.enums.ConditionStatus;
import com.act.hospitalmanagementsystem.medical.enums.DiagnosisType;
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
public class DiagnosisDTO {
    private UUID id;
    private UUID medicalRecordId;
    private String icd10Code;
    private String diagnosisName;
    private DiagnosisType diagnosisType;
    private ConditionStatus conditionStatus;
    private LocalDate diagnosisDate;
    private LocalDate resolvedDate;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
