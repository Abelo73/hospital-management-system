package com.act.hospitalmanagementsystem.medical.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "vaccinations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Vaccination extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "vaccine_name", nullable = false, length = 200)
    private String vaccineName;

    @Column(name = "vaccine_type", length = 100)
    private String vaccineType;

    @Column(name = "administration_date", nullable = false)
    private LocalDate administrationDate;

    @Column(name = "dose_number")
    private Integer doseNumber;

    @Column(name = "lot_number", length = 50)
    private String lotNumber;

    @Column(name = "administering_provider_id")
    private UUID administeringProviderId;

    @Column(name = "next_due_date")
    private LocalDate nextDueDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
