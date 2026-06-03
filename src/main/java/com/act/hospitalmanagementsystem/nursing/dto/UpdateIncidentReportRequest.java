package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.IncidentSeverity;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentStatus;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentType;
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
public class UpdateIncidentReportRequest {
    private UUID admissionId;

    @NotNull(message = "Incident date is required")
    private LocalDate incidentDate;

    @NotNull(message = "Incident time is required")
    private LocalTime incidentTime;

    @NotNull(message = "Incident type is required")
    private IncidentType incidentType;

    @NotNull(message = "Severity is required")
    private IncidentSeverity severity;

    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    @Size(max = 5000, message = "Immediate action must not exceed 5000 characters")
    private String immediateAction;

    @Size(max = 5000, message = "Witnesses must not exceed 5000 characters")
    private String witnesses;

    private Boolean supervisorNotified;

    private UUID supervisorNotifiedBy;

    private Boolean physicianNotified;

    private UUID physicianNotifiedBy;

    private Boolean familyNotified;

    @Size(max = 5000, message = "Follow up actions must not exceed 5000 characters")
    private String followUpActions;

    @NotNull(message = "Status is required")
    private IncidentStatus status;

    @Size(max = 5000, message = "Attachments must not exceed 5000 characters")
    private String attachments;
}
