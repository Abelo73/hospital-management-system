package com.act.hospitalmanagementsystem.billing.entity;

import com.act.hospitalmanagementsystem.billing.enums.PaymentMethod;
import com.act.hospitalmanagementsystem.billing.enums.PaymentStatus;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity @Table(name = "billing_payments")
public class Payment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "invoice_id", nullable = false) private Invoice invoice;
    @Column(name = "patient_id", nullable = false) private UUID patientId;
    @Column(name = "payment_date", nullable = false) private LocalDate paymentDate;
    @Enumerated(EnumType.STRING) @Column(name = "payment_method", nullable = false, length = 20) private PaymentMethod paymentMethod;
    @Column(name = "amount", nullable = false, precision = 12, scale = 2) private BigDecimal amount;
    @Column(name = "reference_number", length = 100) private String referenceNumber;
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false, length = 20) private PaymentStatus status = PaymentStatus.COMPLETED;
    @Column(name = "notes", columnDefinition = "TEXT") private String notes;
}
