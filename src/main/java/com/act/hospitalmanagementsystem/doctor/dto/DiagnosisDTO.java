package com.act.hospitalmanagementsystem.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisDTO {
    private UUID id;
    private UUID consultationId;
    private String icd10Code;
    private String description;
    private Boolean isPrimary;
    private String notes;
}
