package com.act.hospitalmanagementsystem.laboratory.entity;

import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.laboratory.enums.RequestStatus;
import com.act.hospitalmanagementsystem.laboratory.enums.ResultFlag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lab_test_request_items")
public class LabTestRequestItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private LabTestRequest request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private LabTest test;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name = "result_value", length = 500)
    private String resultValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "result_flag", length = 30)
    private ResultFlag resultFlag;

    @Column(name = "reference_range", length = 200)
    private String referenceRange;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by")
    private User performedBy;

    @Column(name = "performed_date")
    private LocalDate performedDate;

    @Column(name = "performed_time")
    private LocalTime performedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by")
    private User verifiedBy;

    @Column(name = "verified_date")
    private LocalDate verifiedDate;

    @Column(name = "verified_time")
    private LocalTime verifiedTime;
}
