package com.act.hospitalmanagementsystem.notification.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "delivery_receipts")
public class DeliveryReceipt extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_log_id")
    private NotificationLog notificationLog;

    @Column(name = "receipt_type", nullable = false, length = 20)
    private String receiptType; // DELIVERY, READ, CLICK, BOUNCE

    @Column(name = "receipt_data", columnDefinition = "JSON")
    private String receiptData;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    @Column(name = "provider", length = 50)
    private String provider;
}
