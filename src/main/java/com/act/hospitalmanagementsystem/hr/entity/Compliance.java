package com.act.hospitalmanagementsystem.hr.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hr_compliance")
public class Compliance extends BaseEntity {

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "compliance_type", nullable = false, length = 50)
    private String complianceType; // BACKGROUND_CHECK, LICENSE, CERTIFICATION, TRAINING, OTHER

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_url")
    private String documentUrl;

    @Column(name = "issuing_authority")
    private String issuingAuthority;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "status", length = 20)
    private String status; // VALID, EXPIRED, PENDING, REVOKED

    @Column(name = "reminder_date")
    private LocalDate reminderDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
