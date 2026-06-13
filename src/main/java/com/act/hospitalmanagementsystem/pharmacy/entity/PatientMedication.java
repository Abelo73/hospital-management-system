package com.act.hospitalmanagementsystem.pharmacy.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pharmacy_patient_medications")
public class PatientMedication extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id")
    private Drug drug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "route")
    private String route;

    @Column(name = "instructions", columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "adherence")
    private Double adherence;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
