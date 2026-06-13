package com.act.hospitalmanagementsystem.pharmacy.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pharmacy_prescription_items")
public class PrescriptionItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id")
    private Drug drug;

    @Column(name = "drug_name")
    private String drugName;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "route")
    private String route;

    @Column(name = "duration")
    private String duration;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "instructions", columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "refills_allowed")
    private Integer refillsAllowed = 0;

    @Column(name = "refills_used")
    private Integer refillsUsed = 0;

    @Column(name = "dispensed_quantity")
    private Integer dispensedQuantity = 0;

    @Column(name = "dispensed")
    private Boolean dispensed = false;
}
