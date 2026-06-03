package com.act.hospitalmanagementsystem.nursing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.nursing.enums.CarePlanStatus;
import com.act.hospitalmanagementsystem.nursing.enums.CarePlanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "nursing_care_plans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NursingCarePlan extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "admission_id")
    private UUID admissionId;

    @Column(name = "plan_name", nullable = false, length = 200)
    private String planName;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type", nullable = false, length = 50)
    private CarePlanType planType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private CarePlanStatus status;

    @Column(name = "primary_nurse_id")
    private UUID primaryNurseId;

    @Column(name = "assessment", columnDefinition = "TEXT")
    private String assessment;

    @Column(name = "nursing_diagnosis", length = 500)
    private String nursingDiagnosis;

    @Column(name = "goals", columnDefinition = "TEXT")
    private String goals;

    @Column(name = "interventions", columnDefinition = "TEXT")
    private String interventions;

    @Column(name = "evaluation", columnDefinition = "TEXT")
    private String evaluation;
}
