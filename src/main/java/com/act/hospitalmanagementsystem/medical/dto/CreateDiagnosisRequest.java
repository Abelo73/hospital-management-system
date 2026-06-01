package com.act.hospitalmanagementsystem.medical.dto;

import com.act.hospitalmanagementsystem.medical.enums.DiagnosisType;
import com.act.hospitalmanagementsystem.medical.enums.ConditionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiagnosisRequest {
    @NotNull(message = "Medical record ID is required")
    private java.util.UUID medicalRecordId;

    @Size(max = 20, message = "ICD-10 code must not exceed 20 characters")
    private String icd10Code;

    @NotBlank(message = "Diagnosis name is required")
    @Size(max = 200, message = "Diagnosis name must not exceed 200 characters")
    private String diagnosisName;

    @NotNull(message = "Diagnosis type is required")
    private DiagnosisType diagnosisType;

    @NotNull(message = "Condition status is required")
    private ConditionStatus conditionStatus;

    @NotNull(message = "Diagnosis date is required")
    private LocalDate diagnosisDate;

    private LocalDate resolvedDate;

    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;
}
