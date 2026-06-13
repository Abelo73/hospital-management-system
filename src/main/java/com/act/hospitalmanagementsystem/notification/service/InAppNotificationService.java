package com.act.hospitalmanagementsystem.notification.service;

import com.act.hospitalmanagementsystem.notification.entity.Notification;
import com.act.hospitalmanagementsystem.notification.entity.NotificationLog;
import com.act.hospitalmanagementsystem.notification.enums.NotificationChannel;
import com.act.hospitalmanagementsystem.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class InAppNotificationService {

    private final NotificationRepository notificationRepository;

    public void sendInAppNotification(Notification notification) {
        try {
            // In-app notifications are stored in the database and retrieved by the frontend
            // The notification is already saved in the database, so we just need to log it
            log.info("In-app notification sent to user {}: {}", notification.getRecipientId(), notification.getTitle());
            
            // Create a notification log entry
            NotificationLog log = new NotificationLog();
            log.setNotification(notification);
            log.setChannel(NotificationChannel.IN_APP);
            log.setStatus("SENT");
            log.setSentAt(LocalDateTime.now());
            log.setProvider("IN_APP");
            
            // TODO: Save notification log to repository when NotificationLogRepository is implemented
            // notificationLogRepository.save(log);
            
        } catch (Exception e) {
            log.error("Error sending in-app notification to user {}", notification.getRecipientId(), e);
            throw new RuntimeException("Failed to send in-app notification: " + e.getMessage());
        }
    }
}
