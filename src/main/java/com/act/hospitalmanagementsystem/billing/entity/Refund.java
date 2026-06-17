package com.act.hospitalmanagementsystem.billing.entity;

import com.act.hospitalmanagementsystem.billing.enums.RefundStatus;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity @Table(name = "billing_refunds")
public class Refund extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "payment_id") private Payment payment;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "invoice_id") private Invoice invoice;
    @Column(name = "patient_id", nullable = false) private UUID patientId;
    @Column(name = "refund_date", nullable = false) private LocalDate refundDate;
    @Column(name = "amount", nullable = false, precision = 12, scale = 2) private BigDecimal amount;
    @Column(name = "reason", length = 1000) private String reason;
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false, length = 20) private RefundStatus status = RefundStatus.PENDING;
    @Column(name = "processed_by", length = 100) private String processedBy;
}
