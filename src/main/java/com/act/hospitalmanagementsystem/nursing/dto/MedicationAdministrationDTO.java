package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.AdministrationStatus;
import com.act.hospitalmanagementsystem.nursing.enums.MedicationRoute;
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
public class MedicationAdministrationDTO {
    private UUID id;
    private UUID patientId;
    private UUID admissionId;
    private UUID medicationId;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    private LocalDate administeredDate;
    private LocalTime administeredTime;
    private UUID administeredBy;
    private UUID verifiedBy;
    private String dose;
    private MedicationRoute route;
    private String site;
    private AdministrationStatus administrationStatus;
    private String refusalReason;
    private String holdReason;
    private String adverseReaction;
    private String effectiveness;
    private String notes;
    private Boolean barcodeScanned;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}
