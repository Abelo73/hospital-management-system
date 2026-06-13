package com.act.hospitalmanagementsystem.notification.repository;

import com.act.hospitalmanagementsystem.notification.entity.Notification;
import com.act.hospitalmanagementsystem.notification.enums.NotificationStatus;
import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Optional<Notification> findByRecipientIdAndStatus(String recipientId, NotificationStatus status);

    Page<Notification> findByRecipientId(String recipientId, Pageable pageable);

    Page<Notification> findByStatus(NotificationStatus status, Pageable pageable);

    List<Notification> findByStatusAndScheduledForBefore(NotificationStatus status, LocalDateTime scheduledFor);

    @Query("SELECT n FROM Notification n WHERE n.deleted = false AND n.status = :status AND n.nextRetryAt <= :now")
    List<Notification> findFailedNotificationsForRetry(@Param("status") NotificationStatus status, @Param("now") LocalDateTime now);

    @Query("SELECT n FROM Notification n WHERE n.deleted = false AND n.notificationType = :type")
    Page<Notification> findByNotificationType(@Param("type") NotificationType type, Pageable pageable);

    @Query("SELECT n FROM Notification n WHERE n.deleted = false AND n.recipientId = :recipientId AND n.status = :status")
    List<Notification> findByRecipientIdAndStatusOrderByCreatedAtDesc(@Param("recipientId") String recipientId, @Param("status") NotificationStatus status);
}
