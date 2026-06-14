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
@Table(name = "hr_training")
public class Training extends BaseEntity {

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "training_type", length = 50)
    private String trainingType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "location")
    private String location;

    @Column(name = "instructor")
    private String instructor;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "status", length = 20)
    private String status; // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
