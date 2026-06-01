package com.act.hospitalmanagementsystem.medical.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.medical.enums.LabResultStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "lab_results")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LabResult extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "test_type", length = 100)
    private String testType;

    @Column(name = "test_name", nullable = false, length = 200)
    private String testName;

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate;

    @Column(name = "result_value", length = 500)
    private String resultValue;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "reference_range", length = 500)
    private String referenceRange;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private LabResultStatus status;

    @Column(name = "performed_by", length = 200)
    private String performedBy;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
