package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.AdministrationStatus;
import com.act.hospitalmanagementsystem.nursing.enums.MedicationRoute;
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
public class UpdateMedicationAdministrationRequest {
    private UUID admissionId;

    private UUID medicationId;

    @NotNull(message = "Scheduled date is required")
    private LocalDate scheduledDate;

    @NotNull(message = "Scheduled time is required")
    private LocalTime scheduledTime;

    private LocalDate administeredDate;

    private LocalTime administeredTime;

    private UUID administeredBy;

    private UUID verifiedBy;

    @Size(max = 100, message = "Dose must not exceed 100 characters")
    private String dose;

    private MedicationRoute route;

    @Size(max = 200, message = "Site must not exceed 200 characters")
    private String site;

    @NotNull(message = "Administration status is required")
    private AdministrationStatus administrationStatus;

    @Size(max = 500, message = "Refusal reason must not exceed 500 characters")
    private String refusalReason;

    @Size(max = 500, message = "Hold reason must not exceed 500 characters")
    private String holdReason;

    @Size(max = 500, message = "Adverse reaction must not exceed 500 characters")
    private String adverseReaction;

    @Size(max = 500, message = "Effectiveness must not exceed 500 characters")
    private String effectiveness;

    @Size(max = 5000, message = "Notes must not exceed 5000 characters")
    private String notes;

    private Boolean barcodeScanned;
}
