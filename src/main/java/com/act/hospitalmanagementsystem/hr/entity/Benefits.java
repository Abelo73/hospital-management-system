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
@Table(name = "hr_benefits")
public class Benefits extends BaseEntity {

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "benefit_type", nullable = false, length = 50)
    private String benefitType; // HEALTH_INSURANCE, DENTAL, VISION, LIFE_INSURANCE, RETIREMENT, OTHER

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "provider")
    private String provider;

    @Column(name = "coverage_amount")
    private Double coverageAmount;

    @Column(name = "employee_contribution")
    private Double employeeContribution;

    @Column(name = "employer_contribution")
    private Double employerContribution;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @Column(name = "status", length = 20)
    private String status; // ACTIVE, INACTIVE, PENDING

    @Column(name = "dependents", columnDefinition = "JSON")
    private String dependents;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
