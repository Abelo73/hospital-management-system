package com.act.hospitalmanagementsystem.hr.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hr_recruitment")
public class Recruitment extends BaseEntity {

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "department")
    private String department;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "requirements", columnDefinition = "TEXT")
    private String requirements;

    @Column(name = "responsibilities", columnDefinition = "TEXT")
    private String responsibilities;

    @Column(name = "vacancies", nullable = false)
    private Integer vacancies;

    @Column(name = "posting_date", nullable = false)
    private LocalDate postingDate;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @Column(name = "status", length = 20)
    private String status; // OPEN, CLOSED, ON_HOLD, FILLED

    @Column(name = "salary_range")
    private String salaryRange;

    @Column(name = "location")
    private String location;

    @Column(name = "employment_type", length = 50)
    private String employmentType; // FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
