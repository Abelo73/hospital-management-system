package com.act.hospitalmanagementsystem.nursing.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.nursing.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "vital_signs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VitalSign extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "admission_id")
    private UUID admissionId;

    @Column(name = "recorded_date", nullable = false)
    private LocalDate recordedDate;

    @Column(name = "recorded_time", nullable = false)
    private LocalTime recordedTime;

    @Column(name = "recorded_by")
    private UUID recordedBy;

    @Column(name = "temperature")
    private BigDecimal temperature;

    @Enumerated(EnumType.STRING)
    @Column(name = "temperature_unit", length = 50)
    private TemperatureUnit temperatureUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "temperature_site", length = 50)
    private TemperatureSite temperatureSite;

    @Column(name = "systolic_bp")
    private Integer systolicBP;

    @Column(name = "diastolic_bp")
    private Integer diastolicBP;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_pressure_site", length = 50)
    private BloodPressureSite bloodPressureSite;

    @Column(name = "heart_rate")
    private Integer heartRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "heart_rate_rhythm", length = 50)
    private HeartRateRhythm heartRateRhythm;

    @Column(name = "respiratory_rate")
    private Integer respiratoryRate;

    @Column(name = "oxygen_saturation")
    private BigDecimal oxygenSaturation;

    @Column(name = "oxygen_supplement")
    private Boolean oxygenSupplement;

    @Column(name = "oxygen_flow_rate")
    private BigDecimal oxygenFlowRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "oxygen_delivery_method", length = 50)
    private OxygenDeliveryMethod oxygenDeliveryMethod;

    @Column(name = "blood_glucose")
    private BigDecimal bloodGlucose;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_glucose_unit", length = 50)
    private BloodGlucoseUnit bloodGlucoseUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_glucose_timing", length = 50)
    private BloodGlucoseTiming bloodGlucoseTiming;

    @Column(name = "pain_score")
    private Integer painScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "pain_scale", length = 50)
    private PainScale painScale;

    @Column(name = "height")
    private BigDecimal height;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "bmi")
    private BigDecimal bmi;

    @Column(name = "head_circumference")
    private BigDecimal headCircumference;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_abnormal")
    private Boolean isAbnormal;
}
