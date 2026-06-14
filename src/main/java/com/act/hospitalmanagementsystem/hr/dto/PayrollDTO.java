package com.act.hospitalmanagementsystem.hr.dto;

import com.act.hospitalmanagementsystem.hr.enums.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollDTO {
    private UUID id;
    private UUID employeeId;
    private LocalDate payPeriodStart;
    private LocalDate payPeriodEnd;
    private LocalDate payDate;
    private Double grossPay;
    private Double netPay;
    private Double taxDeduction;
    private Double insuranceDeduction;
    private Double otherDeductions;
    private Double bonuses;
    private Double overtimePay;
    private PayStatus status;
    private String paymentMethod;
    private String transactionId;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
}
