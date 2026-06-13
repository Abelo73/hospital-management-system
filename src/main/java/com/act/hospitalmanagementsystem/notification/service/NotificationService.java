package com.act.hospitalmanagementsystem.notification.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.notification.dto.NotificationDTO;
import com.act.hospitalmanagementsystem.notification.dto.SendNotificationRequest;
import com.act.hospitalmanagementsystem.notification.entity.Notification;
import com.act.hospitalmanagementsystem.notification.enums.NotificationChannel;
import com.act.hospitalmanagementsystem.notification.enums.NotificationStatus;
import com.act.hospitalmanagementsystem.notification.enums.Priority;
import com.act.hospitalmanagementsystem.notification.mapper.NotificationMapper;
import com.act.hospitalmanagementsystem.notification.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final SMSService smsService;
    private final PushNotificationService pushNotificationService;
    private final InAppNotificationService inAppNotificationService;

    @Transactional
    public BaseResponseDTO<NotificationDTO> sendNotification(SendNotificationRequest request, String createdBy) {
        try {
            Notification notification = new Notification();
            notification.setNotificationType(request.getNotificationType());
            notification.setTitle(request.getTitle());
            notification.setMessage(request.getMessage());
            notification.setChannels(notificationMapper.channelsToJson(request.getChannels()));
            notification.setPriority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM);
            notification.setStatus(NotificationStatus.PENDING);
            notification.setRecipientType(request.getRecipientType());
            notification.setRecipientId(request.getRecipientId());
            notification.setRecipientEmail(request.getRecipientEmail());
            notification.setRecipientPhone(request.getRecipientPhone());
            notification.setVariables(notificationMapper.variablesToJson(request.getVariables()));
            notification.setScheduledFor(request.getScheduledFor());
            notification.setRetryCount(0);
            notification.setMaxRetries(3);

            Notification saved = notificationRepository.save(notification);

            if (request.getScheduledFor() == null || request.getScheduledFor().isBefore(LocalDateTime.now().plusMinutes(1))) {
                processNotification(saved);
            }

            return BaseResponseDTO.success(notificationMapper.toDTO(saved), "Notification queued successfully");
        } catch (Exception e) {
            log.error("Error sending notification", e);
            return BaseResponseDTO.error("Failed to send notification: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<NotificationDTO> sendBulkNotification(SendNotificationRequest request, List<String> recipientIds, String createdBy) {
        try {
            for (String recipientId : recipientIds) {
                SendNotificationRequest individualRequest = new SendNotificationRequest(
                        request.getNotificationType(),
                        request.getTitle(),
                        request.getMessage(),
                        request.getChannels(),
                        request.getPriority(),
                        request.getRecipientType(),
                        recipientId,
                        request.getRecipientEmail(),
                        request.getRecipientPhone(),
                        request.getTemplateId(),
                        request.getVariables(),
                        request.getScheduledFor()
                );
                sendNotification(individualRequest, createdBy);
            }
            return BaseResponseDTO.success(null, "Bulk notifications queued successfully");
        } catch (Exception e) {
            log.error("Error sending bulk notification", e);
            return BaseResponseDTO.error("Failed to send bulk notifications: " + e.getMessage());
        }
    }

    @Async
    @Transactional
    public void processNotification(Notification notification) {
        try {
            notification.setSentAt(LocalDateTime.now());
            notification.setStatus(NotificationStatus.SENT);
            notificationRepository.save(notification);

            List<NotificationChannel> channels = objectMapper.readValue(
                    notification.getChannels(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, NotificationChannel.class))
            ;

            for (NotificationChannel channel : channels) {
                switch (channel) {
                    case EMAIL:
                        emailService.sendEmail(notification);
                        break;
                    case SMS:
                        smsService.sendSMS(notification);
                        break;
                    case PUSH:
                        pushNotificationService.sendPushNotification(notification);
                        break;
                    case IN_APP:
                        inAppNotificationService.sendInAppNotification(notification);
                        break;
                    default:
                        log.warn("Unsupported notification channel: {}", channel);
                }
            }

            notification.setDeliveredAt(LocalDateTime.now());
            notification.setStatus(NotificationStatus.DELIVERED);
            notificationRepository.save(notification);

        } catch (Exception e) {
            log.error("Error processing notification {}", notification.getId(), e);
            handleFailedNotification(notification, e.getMessage());
        }
    }

    @Transactional
    public void handleFailedNotification(Notification notification, String errorMessage) {
        try {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailedAt(LocalDateTime.now());
            notification.setFailureReason(errorMessage);
            notification.setRetryCount(notification.getRetryCount() + 1);

            if (notification.getRetryCount() < notification.getMaxRetries()) {
                notification.setNextRetryAt(LocalDateTime.now().plusMinutes(5 * notification.getRetryCount()));
            }

            notificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Error handling failed notification", e);
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<NotificationDTO> getNotificationStatus(UUID id) {
        try {
            Notification notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));
            return BaseResponseDTO.success(notificationMapper.toDTO(notification), "Notification status retrieved");
        } catch (Exception e) {
            log.error("Error getting notification status", e);
            return BaseResponseDTO.error("Failed to get notification status: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<NotificationDTO>> getUserNotifications(String recipientId, NotificationStatus status, Pageable pageable) {
        try {
            Page<Notification> notifications;
            if (status != null) {
                notifications = notificationRepository.findByRecipientIdAndStatusOrderByCreatedAtDesc(recipientId, status);
            } else {
                notifications = notificationRepository.findByRecipientId(recipientId, pageable);
            }
            return BaseResponseDTO.success(notificationMapper.toDTOList(notifications.getContent()), "Notifications retrieved");
        } catch (Exception e) {
            log.error("Error getting user notifications", e);
            return BaseResponseDTO.error("Failed to get notifications: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> markAsRead(UUID id) {
        try {
            Notification notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));
            notification.setStatus(NotificationStatus.DELIVERED);
            notificationRepository.save(notification);
            return BaseResponseDTO.success(null, "Notification marked as read");
        } catch (Exception e) {
            log.error("Error marking notification as read", e);
            return BaseResponseDTO.error("Failed to mark notification as read: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> deleteNotification(UUID id) {
        try {
            Notification notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));
            notification.setDeleted(true);
            notificationRepository.save(notification);
            return BaseResponseDTO.success(null, "Notification deleted");
        } catch (Exception e) {
            log.error("Error deleting notification", e);
            return BaseResponseDTO.error("Failed to delete notification: " + e.getMessage());
        }
    }

    @Transactional
    public void processScheduledNotifications() {
        try {
            List<Notification> scheduledNotifications = notificationRepository
                    .findByStatusAndScheduledForBefore(NotificationStatus.PENDING, LocalDateTime.now());

            for (Notification notification : scheduledNotifications) {
                processNotification(notification);
            }
        } catch (Exception e) {
            log.error("Error processing scheduled notifications", e);
        }
    }

    @Transactional
    public void retryFailedNotifications() {
        try {
            List<Notification> failedNotifications = notificationRepository
                    .findFailedNotificationsForRetry(NotificationStatus.FAILED, LocalDateTime.now());

            for (Notification notification : failedNotifications) {
                notification.setStatus(NotificationStatus.PENDING);
                notification.setFailureReason(null);
                notificationRepository.save(notification);
                processNotification(notification);
            }
        } catch (Exception e) {
            log.error("Error retrying failed notifications", e);
        }
    }
}
