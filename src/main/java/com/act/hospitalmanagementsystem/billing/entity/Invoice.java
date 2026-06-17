package com.act.hospitalmanagementsystem.billing.entity;

import com.act.hospitalmanagementsystem.billing.enums.InvoiceStatus;
import com.act.hospitalmanagementsystem.billing.enums.PaymentMethod;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity @Table(name = "billing_invoices")
public class Invoice extends BaseEntity {
    @Column(name = "invoice_number", unique = true, nullable = false, length = 50) private String invoiceNumber;
    @Column(name = "patient_id", nullable = false) private UUID patientId;
    @Column(name = "patient_name", length = 200) private String patientName;
    @Column(name = "invoice_date", nullable = false) private LocalDate invoiceDate;
    @Column(name = "due_date") private LocalDate dueDate;
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false, length = 20) private InvoiceStatus status = InvoiceStatus.DRAFT;
    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2) private BigDecimal subtotal = BigDecimal.ZERO;
    @Column(name = "tax", precision = 12, scale = 2) private BigDecimal tax = BigDecimal.ZERO;
    @Column(name = "discount", precision = 12, scale = 2) private BigDecimal discount = BigDecimal.ZERO;
    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2) private BigDecimal totalAmount = BigDecimal.ZERO;
    @Column(name = "paid_amount", precision = 12, scale = 2) private BigDecimal paidAmount = BigDecimal.ZERO;
    @Column(name = "balance_amount", precision = 12, scale = 2) private BigDecimal balanceAmount = BigDecimal.ZERO;
    @Enumerated(EnumType.STRING) @Column(name = "payment_method", length = 20) private PaymentMethod paymentMethod;
    @Column(name = "notes", columnDefinition = "TEXT") private String notes;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InvoiceLineItem> lineItems = new ArrayList<>();
}
