package com.act.hospitalmanagementsystem.pharmacy.dto;

import com.act.hospitalmanagementsystem.pharmacy.enums.DrugCategory;
import com.act.hospitalmanagementsystem.pharmacy.enums.DosageForm;
import com.act.hospitalmanagementsystem.pharmacy.enums.DrugSchedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugDTO {
    private UUID id;
    private String drugCode;
    private String genericName;
    private String brandName;
    private DrugCategory drugCategory;
    private DosageForm dosageForm;
    private String strength;
    private String manufacturer;
    private String ndc;
    private DrugSchedule schedule;
    private Boolean isControlledSubstance;
    private Boolean requiresPrescription;
    private String storageConditions;
    private Integer shelfLife;
    private String description;
    private String indications;
    private String contraindications;
    private String sideEffects;
    private String interactions;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private String createdBy;
}
