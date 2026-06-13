package com.act.hospitalmanagementsystem.pharmacy.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.pharmacy.enums.DrugCategory;
import com.act.hospitalmanagementsystem.pharmacy.enums.DosageForm;
import com.act.hospitalmanagementsystem.pharmacy.enums.DrugSchedule;
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
@Table(name = "pharmacy_drugs")
public class Drug extends BaseEntity {

    @Column(name = "drug_code", unique = true, nullable = false)
    private String drugCode;

    @Column(name = "generic_name", nullable = false)
    private String genericName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "drug_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private DrugCategory drugCategory;

    @Column(name = "dosage_form", nullable = false)
    @Enumerated(EnumType.STRING)
    private DosageForm dosageForm;

    @Column(name = "strength")
    private String strength;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "ndc")
    private String ndc;

    @Column(name = "schedule", nullable = false)
    @Enumerated(EnumType.STRING)
    private DrugSchedule schedule = DrugSchedule.UNSCHEDULED;

    @Column(name = "is_controlled_substance", nullable = false)
    private Boolean isControlledSubstance = false;

    @Column(name = "requires_prescription", nullable = false)
    private Boolean requiresPrescription = false;

    @Column(name = "storage_conditions", columnDefinition = "TEXT")
    private String storageConditions;

    @Column(name = "shelf_life")
    private Integer shelfLife;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "indications", columnDefinition = "JSON")
    private String indications;

    @Column(name = "contraindications", columnDefinition = "JSON")
    private String contraindications;

    @Column(name = "side_effects", columnDefinition = "JSON")
    private String sideEffects;

    @Column(name = "interactions", columnDefinition = "JSON")
    private String interactions;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
