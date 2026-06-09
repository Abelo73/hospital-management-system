package com.act.hospitalmanagementsystem.laboratory.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.laboratory.enums.LabTestCategory;
import com.act.hospitalmanagementsystem.laboratory.enums.SpecimenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lab_tests")
public class LabTest extends BaseEntity {

    @Column(name = "test_code", unique = true, nullable = false, length = 50)
    private String testCode;

    @Column(name = "test_name", nullable = false, length = 200)
    private String testName;

    @Enumerated(EnumType.STRING)
    @Column(name = "test_category", nullable = false, length = 50)
    private LabTestCategory testCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "specimen_type", nullable = false, length = 50)
    private SpecimenType specimenType;

    @Column(name = "specimen_container", length = 100)
    private String specimenContainer;

    @Column(name = "specimen_volume", length = 50)
    private String specimenVolume;

    @Column(name = "collection_instructions", columnDefinition = "TEXT")
    private String collectionInstructions;

    @Column(name = "processing_instructions", columnDefinition = "TEXT")
    private String processingInstructions;

    @Column(name = "reference_range", columnDefinition = "TEXT")
    private String referenceRange;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "methodology", length = 100)
    private String methodology;

    @Column(name = "turnaround_time")
    private Integer turnaroundTime; // in hours

    @Column(name = "price", precision = 19, scale = 4)
    private BigDecimal price;

    @Column(name = "cpt_code", length = 20)
    private String cptCode;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_panel", nullable = false)
    private Boolean isPanel = false;

    @Column(name = "department", length = 100)
    private String department;
}
