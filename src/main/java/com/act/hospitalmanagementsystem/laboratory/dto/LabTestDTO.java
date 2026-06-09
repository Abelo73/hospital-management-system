package com.act.hospitalmanagementsystem.laboratory.dto;

import com.act.hospitalmanagementsystem.laboratory.enums.LabTestCategory;
import com.act.hospitalmanagementsystem.laboratory.enums.SpecimenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabTestDTO {
    private UUID id;
    private String testCode;
    private String testName;
    private LabTestCategory testCategory;
    private SpecimenType specimenType;
    private String specimenContainer;
    private String specimenVolume;
    private String collectionInstructions;
    private String unit;
    private Integer turnaroundTime;
    private BigDecimal price;
    private String cptCode;
    private Boolean isActive;
}
