package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.IncidentSeverity;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentStatus;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentType;
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
public class IncidentReportDTO {
    private UUID id;
    private UUID patientId;
    private UUID admissionId;
    private LocalDate incidentDate;
    private LocalTime incidentTime;
    private IncidentType incidentType;
    private IncidentSeverity severity;
    private String location;
    private String description;
    private String immediateAction;
    private String witnesses;
    private UUID reportedBy;
    private LocalDate reportedDate;
    private LocalTime reportedTime;
    private Boolean supervisorNotified;
    private UUID supervisorNotifiedBy;
    private Boolean physicianNotified;
    private UUID physicianNotifiedBy;
    private Boolean familyNotified;
    private String followUpActions;
    private IncidentStatus status;
    private String attachments;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private UUID updatedBy;
    private LocalDateTime updatedAt;
}
