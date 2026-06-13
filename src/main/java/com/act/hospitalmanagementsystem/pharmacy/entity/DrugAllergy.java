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
@Table(name = "pharmacy_drug_allergies")
public class DrugAllergy extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id")
    private Drug drug;

    @Column(name = "allergen")
    private String allergen;

    @Column(name = "severity", length = 20)
    private String severity; // MILD, MODERATE, SEVERE, LIFE_THREATENING

    @Column(name = "reaction", columnDefinition = "TEXT")
    private String reaction;

    @Column(name = "onset_date")
    private LocalDate onsetDate;

    @Column(name = "reported_by")
    private UUID reportedBy;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
