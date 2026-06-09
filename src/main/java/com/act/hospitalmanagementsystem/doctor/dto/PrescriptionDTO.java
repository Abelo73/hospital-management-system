package com.act.hospitalmanagementsystem.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {
    private UUID id;
    private UUID consultationId;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String duration;
    private String route;
    private String instructions;
    private Boolean isDispensed;
    private LocalDateTime dispensedAt;
    private String notes;
}
