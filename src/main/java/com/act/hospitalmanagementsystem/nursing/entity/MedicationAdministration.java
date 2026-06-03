package com.act.hospitalmanagementsystem.nursing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.nursing.enums.AdministrationStatus;
import com.act.hospitalmanagementsystem.nursing.enums.MedicationRoute;
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
@Table(name = "medication_administrations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MedicationAdministration extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "admission_id")
    private UUID admissionId;

    @Column(name = "medication_id")
    private UUID medicationId;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;

    @Column(name = "scheduled_time", nullable = false)
    private LocalTime scheduledTime;

    @Column(name = "administered_date")
    private LocalDate administeredDate;

    @Column(name = "administered_time")
    private LocalTime administeredTime;

    @Column(name = "administered_by")
    private UUID administeredBy;

    @Column(name = "verified_by")
    private UUID verifiedBy;

    @Column(name = "dose", length = 100)
    private String dose;

    @Enumerated(EnumType.STRING)
    @Column(name = "route", length = 50)
    private MedicationRoute route;

    @Column(name = "site", length = 200)
    private String site;

    @Enumerated(EnumType.STRING)
    @Column(name = "administration_status", nullable = false, length = 50)
    private AdministrationStatus administrationStatus;

    @Column(name = "refusal_reason", length = 500)
    private String refusalReason;

    @Column(name = "hold_reason", length = 500)
    private String holdReason;

    @Column(name = "adverse_reaction", length = 500)
    private String adverseReaction;

    @Column(name = "effectiveness", length = 500)
    private String effectiveness;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "barcode_scanned")
    private Boolean barcodeScanned;
}
