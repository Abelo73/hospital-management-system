package com.act.hospitalmanagementsystem.nursing.dto;

import com.act.hospitalmanagementsystem.nursing.enums.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVitalSignRequest {
    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    private UUID admissionId;

    @NotNull(message = "Recorded date is required")
    private LocalDate recordedDate;

    @NotNull(message = "Recorded time is required")
    private LocalTime recordedTime;

    private UUID recordedBy;

    private BigDecimal temperature;

    private TemperatureUnit temperatureUnit;

    private TemperatureSite temperatureSite;

    private Integer systolicBP;

    private Integer diastolicBP;

    private BloodPressureSite bloodPressureSite;

    private Integer heartRate;

    private HeartRateRhythm heartRateRhythm;

    private Integer respiratoryRate;

    private BigDecimal oxygenSaturation;

    private Boolean oxygenSupplement;

    private BigDecimal oxygenFlowRate;

    private OxygenDeliveryMethod oxygenDeliveryMethod;

    private BigDecimal bloodGlucose;

    private BloodGlucoseUnit bloodGlucoseUnit;

    private BloodGlucoseTiming bloodGlucoseTiming;

    private Integer painScore;

    private PainScale painScale;

    private BigDecimal height;

    private BigDecimal weight;

    private BigDecimal bmi;

    private BigDecimal headCircumference;

    @Size(max = 5000, message = "Notes must not exceed 5000 characters")
    private String notes;

    private Boolean isAbnormal;
}
