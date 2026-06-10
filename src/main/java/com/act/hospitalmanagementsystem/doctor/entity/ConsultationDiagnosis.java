package com.act.hospitalmanagementsystem.doctor.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "consultation_diagnoses")
public class ConsultationDiagnosis extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @Column(name = "icd10_code", length = 20)
    private String icd10Code;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
