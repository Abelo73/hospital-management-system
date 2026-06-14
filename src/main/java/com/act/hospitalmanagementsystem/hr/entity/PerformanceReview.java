package com.act.hospitalmanagementsystem.hr.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.hr.enums.PerformanceRating;
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
@Table(name = "hr_performance_reviews")
public class PerformanceReview extends BaseEntity {

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "reviewer_id", nullable = false)
    private UUID reviewerId;

    @Column(name = "review_period_start", nullable = false)
    private LocalDate reviewPeriodStart;

    @Column(name = "review_period_end", nullable = false)
    private LocalDate reviewPeriodEnd;

    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;

    @Column(name = "rating", nullable = false)
    @Enumerated(EnumType.STRING)
    private PerformanceRating rating;

    @Column(name = "goals_achieved", columnDefinition = "TEXT")
    private String goalsAchieved;

    @Column(name = "areas_for_improvement", columnDefinition = "TEXT")
    private String areasForImprovement;

    @Column(name = "strengths", columnDefinition = "TEXT")
    private String strengths;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "employee_comments", columnDefinition = "TEXT")
    private String employeeComments;

    @Column(name = "development_plan", columnDefinition = "TEXT")
    private String developmentPlan;
}
