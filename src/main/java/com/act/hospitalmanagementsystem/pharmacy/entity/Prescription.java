package com.act.hospitalmanagementsystem.pharmacy.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.pharmacy.enums.PrescriptionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pharmacy_prescriptions")
public class Prescription extends BaseEntity {

    @Column(name = "prescription_number", unique = true, nullable = false)
    private String prescriptionNumber;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "doctor_id", nullable = false)
    private UUID doctorId;

    @Column(name = "prescription_date", nullable = false)
    private LocalDate prescriptionDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status = PrescriptionStatus.PENDING;

    @Column(name = "priority", length = 20)
    private String priority = "ROUTINE"; // ROUTINE, URGENT, STAT

    @Column(name = "facility")
    private String facility;

    @Column(name = "department")
    private String department;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "validated_by")
    private UUID validatedBy;

    @Column(name = "validated_at")
    private java.time.LocalDateTime validatedAt;

    @Column(name = "dispensed_by")
    private UUID dispensedBy;

    @Column(name = "dispensed_at")
    private java.time.LocalDateTime dispensedAt;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionItem> items;
}
