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
@Table(name = "hr_positions")
public class Position extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "department_id")
    private UUID departmentId;

    @Column(name = "grade_id")
    private UUID gradeId;

    @Column(name = "min_salary")
    private Double minSalary;

    @Column(name = "max_salary")
    private Double maxSalary;

    @Column(name = "responsibilities", columnDefinition = "TEXT")
    private String responsibilities;

    @Column(name = "required_skills", columnDefinition = "TEXT")
    private String requiredSkills;

    @Column(name = "required_qualifications", columnDefinition = "TEXT")
    private String requiredQualifications;

    @Column(name = "reporting_position_id")
    private UUID reportingPositionId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
