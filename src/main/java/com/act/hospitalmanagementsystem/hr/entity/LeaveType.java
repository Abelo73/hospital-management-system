package com.act.hospitalmanagementsystem.hr.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hr_leave_types")
public class LeaveType extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "annual_days", nullable = false)
    private Integer annualDays = 0;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid = true;

    @Column(name = "requires_approval", nullable = false)
    private Boolean requiresApproval = true;

    @Column(name = "requires_attachment", nullable = false)
    private Boolean requiresAttachment = false;

    @Column(name = "is_gender_specific", nullable = false)
    private Boolean isGenderSpecific = false;

    @Column(name = "applicable_gender", length = 10)
    private String applicableGender;

    @Column(name = "max_carryover_days", nullable = false)
    private Integer maxCarryoverDays = 0;

    @Column(name = "min_service_months_required", nullable = false)
    private Integer minServiceMonthsRequired = 0;

    @Column(name = "accrual_frequency", nullable = false)
    private String accrualFrequency = "ANNUAL";

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
