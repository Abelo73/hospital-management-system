package com.act.hospitalmanagementsystem.hr.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hr_training_enrollments")
public class TrainingEnrollment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private Training training;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "enrollment_date", nullable = false)
    private java.time.LocalDate enrollmentDate;

    @Column(name = "completion_date")
    private java.time.LocalDate completionDate;

    @Column(name = "status", length = 20)
    private String status; // ENROLLED, IN_PROGRESS, COMPLETED, DROPPED

    @Column(name = "certificate_url")
    private String certificateUrl;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
