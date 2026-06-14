package com.act.hospitalmanagementsystem.hr.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import com.act.hospitalmanagementsystem.hr.enums.PayStatus;
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
@Table(name = "hr_payroll")
public class Payroll extends BaseEntity {

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "pay_period_start", nullable = false)
    private LocalDate payPeriodStart;

    @Column(name = "pay_period_end", nullable = false)
    private LocalDate payPeriodEnd;

    @Column(name = "pay_date")
    private LocalDate payDate;

    @Column(name = "gross_pay", nullable = false)
    private Double grossPay;

    @Column(name = "net_pay", nullable = false)
    private Double netPay;

    @Column(name = "tax_deduction")
    private Double taxDeduction;

    @Column(name = "insurance_deduction")
    private Double insuranceDeduction;

    @Column(name = "other_deductions")
    private Double otherDeductions;

    @Column(name = "bonuses")
    private Double bonuses;

    @Column(name = "overtime_pay")
    private Double overtimePay;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PayStatus status = PayStatus.PENDING;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
