package com.act.hospitalmanagementsystem.notification.repository;

import com.act.hospitalmanagementsystem.notification.entity.DeliveryReceipt;
import com.act.hospitalmanagementsystem.notification.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrackingRepository {

    // NotificationLog operations
    NotificationLog saveNotificationLog(NotificationLog log);
    List<NotificationLog> findLogsByNotificationId(UUID notificationId);
    NotificationLog findLogById(UUID logId);

    // DeliveryReceipt operations
    DeliveryReceipt saveDeliveryReceipt(DeliveryReceipt receipt);
    List<DeliveryReceipt> findReceiptsByLogId(UUID logId);
}
