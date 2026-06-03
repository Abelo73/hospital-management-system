package com.act.hospitalmanagementsystem.nursing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentSeverity;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentStatus;
import com.act.hospitalmanagementsystem.nursing.enums.IncidentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "incident_reports")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IncidentReport extends BaseEntity {

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "admission_id")
    private UUID admissionId;

    @Column(name = "incident_date", nullable = false)
    private LocalDate incidentDate;

    @Column(name = "incident_time", nullable = false)
    private LocalTime incidentTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "incident_type", nullable = false, length = 50)
    private IncidentType incidentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 50)
    private IncidentSeverity severity;

    @Column(name = "location", length = 200)
    private String location;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "immediate_action", columnDefinition = "TEXT")
    private String immediateAction;

    @Column(name = "witnesses", columnDefinition = "TEXT")
    private String witnesses;

    @Column(name = "reported_by")
    private UUID reportedBy;

    @Column(name = "reported_date", nullable = false)
    private LocalDate reportedDate;

    @Column(name = "reported_time", nullable = false)
    private LocalTime reportedTime;

    @Column(name = "supervisor_notified")
    private Boolean supervisorNotified;

    @Column(name = "supervisor_notified_by")
    private UUID supervisorNotifiedBy;

    @Column(name = "physician_notified")
    private Boolean physicianNotified;

    @Column(name = "physician_notified_by")
    private UUID physicianNotifiedBy;

    @Column(name = "family_notified")
    private Boolean familyNotified;

    @Column(name = "follow_up_actions", columnDefinition = "TEXT")
    private String followUpActions;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private IncidentStatus status;

    @Column(name = "attachments", columnDefinition = "TEXT")
    private String attachments;
}
