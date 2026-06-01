package com.act.hospitalmanagementsystem.medical.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.medical.enums.ConditionStatus;
import com.act.hospitalmanagementsystem.medical.enums.DiagnosisType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "diagnoses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Diagnosis extends BaseEntity {

    @Column(name = "medical_record_id", nullable = false)
    private UUID medicalRecordId;

    @Column(name = "icd10_code", length = 20)
    private String icd10Code;

    @Column(name = "diagnosis_name", nullable = false, length = 200)
    private String diagnosisName;

    @Enumerated(EnumType.STRING)
    @Column(name = "diagnosis_type", nullable = false, length = 50)
    private DiagnosisType diagnosisType;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_status", nullable = false, length = 50)
    private ConditionStatus conditionStatus;

    @Column(name = "diagnosis_date", nullable = false)
    private LocalDate diagnosisDate;

    @Column(name = "resolved_date")
    private LocalDate resolvedDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
