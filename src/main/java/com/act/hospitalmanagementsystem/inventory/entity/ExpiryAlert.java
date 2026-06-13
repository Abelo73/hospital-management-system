package com.act.hospitalmanagementsystem.inventory.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
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
@Table(name = "inventory_expiry_alerts")
public class ExpiryAlert extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "days_to_expiry", nullable = false)
    private Integer daysToExpiry;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "alert_type", nullable = false, length = 20)
    private String alertType; // WARNING, CRITICAL, EXPIRED

    @Column(name = "alert_date", nullable = false)
    private LocalDate alertDate;

    @Column(name = "is_acknowledged", nullable = false)
    private Boolean isAcknowledged = false;

    @Column(name = "acknowledged_by")
    private UUID acknowledgedBy;

    @Column(name = "acknowledged_on")
    private java.time.LocalDateTime acknowledgedOn;

    @Column(name = "action_taken", columnDefinition = "TEXT")
    private String actionTaken;
}
