package com.act.hospitalmanagementsystem.laboratory.entity;

import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.laboratory.enums.RequestPriority;
import com.act.hospitalmanagementsystem.laboratory.enums.RequestStatus;
import com.act.hospitalmanagementsystem.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lab_test_requests")
public class LabTestRequest extends BaseEntity {

    @Column(name = "request_number", unique = true, nullable = false, length = 50)
    private String requestNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordering_provider_id", nullable = false)
    private User orderingProvider;

    @Column(name = "ordering_department", length = 100)
    private String orderingDepartment;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "request_time", nullable = false)
    private LocalTime requestTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 30)
    private RequestPriority priority = RequestPriority.ROUTINE;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name = "clinical_information", columnDefinition = "TEXT")
    private String clinicalInformation;

    @Column(name = "diagnosis", length = 500)
    private String diagnosis;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabTestRequestItem> items = new ArrayList<>();

    @Column(name = "specimen_collection_date")
    private LocalDate specimenCollectionDate;

    @Column(name = "specimen_collection_time")
    private LocalTime specimenCollectionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specimen_collector_id")
    private User specimenCollector;

    @Column(name = "results_released_at")
    private LocalDateTime resultsReleasedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "results_released_by")
    private User resultsReleasedBy;
}
