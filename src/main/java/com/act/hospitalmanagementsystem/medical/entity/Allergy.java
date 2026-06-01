package com.act.hospitalmanagementsystem.medical.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.medical.enums.AllergenType;
import com.act.hospitalmanagementsystem.medical.enums.Severity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "allergies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Allergy extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "allergen_type", nullable = false, length = 50)
    private AllergenType allergenType;

    @Column(name = "allergen_name", nullable = false, length = 200)
    private String allergenName;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 50)
    private Severity severity;

    @Column(name = "reaction", length = 500)
    private String reaction;

    @Column(name = "onset_date")
    private LocalDate onsetDate;

    @Column(name = "reported_by", length = 100)
    private String reportedBy;
}
