package com.act.hospitalmanagementsystem.notification.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.notification.dto.ScheduleDTO;
import com.act.hospitalmanagementsystem.notification.entity.NotificationSchedule;
import com.act.hospitalmanagementsystem.notification.entity.Template;
import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import com.act.hospitalmanagementsystem.notification.repository.ScheduleRepository;
import com.act.hospitalmanagementsystem.notification.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TemplateRepository templateRepository;
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<ScheduleDTO>> getSchedules(Boolean isActive) {
        try {
            List<NotificationSchedule> schedules;
            if (isActive != null) {
                schedules = isActive ? scheduleRepository.findByIsActiveTrue() : scheduleRepository.findByIsActiveFalse();
            } else {
                schedules = scheduleRepository.findAll();
            }
            return BaseResponseDTO.success(toDTOList(schedules), "Schedules retrieved");
        } catch (Exception e) {
            log.error("Error getting schedules", e);
            return BaseResponseDTO.error("Failed to get schedules: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<ScheduleDTO> createSchedule(String scheduleName, NotificationType notificationType, 
            UUID templateId, Map<String, Object> recipients, String cronExpression, String timezone, String createdBy) {
        try {
            NotificationSchedule schedule = new NotificationSchedule();
            schedule.setScheduleName(scheduleName);
            schedule.setNotificationType(notificationType);
            
            if (templateId != null) {
                Template template = templateRepository.findById(templateId)
                        .orElseThrow(() -> new RuntimeException("Template not found"));
                schedule.setTemplate(template);
            }
            
            schedule.setRecipients(recipients.toString());
            schedule.setCronExpression(cronExpression);
            schedule.setTimezone(timezone != null ? timezone : "Africa/Nairobi");
            schedule.setIsActive(true);
            schedule.setRunCount(0);
            schedule.setFailureCount(0);
            schedule.setCreatedBy(createdBy);
            
            // Calculate next run time based on cron expression
            schedule.setNextRunAt(calculateNextRunTime(cronExpression, timezone));
            
            NotificationSchedule saved = scheduleRepository.save(schedule);
            return BaseResponseDTO.success(toDTO(saved), "Schedule created successfully");
        } catch (Exception e) {
            log.error("Error creating schedule", e);
            return BaseResponseDTO.error("Failed to create schedule: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<ScheduleDTO> updateSchedule(UUID id, Boolean isActive, String updatedBy) {
        try {
            NotificationSchedule schedule = scheduleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
            
            schedule.setIsActive(isActive);
            schedule.setUpdatedBy(updatedBy);
            
            NotificationSchedule saved = scheduleRepository.save(schedule);
            return BaseResponseDTO.success(toDTO(saved), "Schedule updated successfully");
        } catch (Exception e) {
            log.error("Error updating schedule", e);
            return BaseResponseDTO.error("Failed to update schedule: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> triggerScheduleManually(UUID id) {
        try {
            NotificationSchedule schedule = scheduleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
            
            executeSchedule(schedule);
            return BaseResponseDTO.success(null, "Schedule triggered successfully");
        } catch (Exception e) {
            log.error("Error triggering schedule", e);
            return BaseResponseDTO.error("Failed to trigger schedule: " + e.getMessage());
        }
    }

    @Transactional
    public void executeScheduledNotifications() {
        try {
            List<NotificationSchedule> schedules = scheduleRepository.findActiveSchedulesDueForExecution(LocalDateTime.now());
            
            for (NotificationSchedule schedule : schedules) {
                executeSchedule(schedule);
            }
        } catch (Exception e) {
            log.error("Error executing scheduled notifications", e);
        }
    }

    private void executeSchedule(NotificationSchedule schedule) {
        try {
            schedule.setLastRunAt(LocalDateTime.now());
            schedule.setRunCount(schedule.getRunCount() + 1);
            
            // TODO: Parse recipients and send notifications based on the schedule configuration
            // This would involve querying for users/patients based on the recipient filter
            // and sending notifications using the notification service
            
            // Calculate next run time
            schedule.setNextRunAt(calculateNextRunTime(schedule.getCronExpression(), schedule.getTimezone()));
            
            scheduleRepository.save(schedule);
            log.info("Schedule {} executed successfully", schedule.getScheduleName());
        } catch (Exception e) {
            log.error("Error executing schedule {}", schedule.getScheduleName(), e);
            schedule.setFailureCount(schedule.getFailureCount() + 1);
            scheduleRepository.save(schedule);
        }
    }

    private LocalDateTime calculateNextRunTime(String cronExpression, String timezone) {
        // TODO: Implement cron expression parsing to calculate next run time
        // For now, return a placeholder
        return LocalDateTime.now().plusHours(24);
    }

    private ScheduleDTO toDTO(NotificationSchedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setScheduleName(schedule.getScheduleName());
        dto.setNotificationType(schedule.getNotificationType());
        dto.setTemplateId(schedule.getTemplate() != null ? schedule.getTemplate().getId() : null);
        dto.setRecipients(schedule.getRecipients());
        dto.setCronExpression(schedule.getCronExpression());
        dto.setTimezone(schedule.getTimezone());
        dto.setIsActive(schedule.getIsActive());
        dto.setLastRunAt(schedule.getLastRunAt());
        dto.setNextRunAt(schedule.getNextRunAt());
        dto.setRunCount(schedule.getRunCount());
        dto.setFailureCount(schedule.getFailureCount());
        dto.setCreatedAt(schedule.getCreatedAt());
        dto.setCreatedBy(schedule.getCreatedBy());
        return dto;
    }

    private List<ScheduleDTO> toDTOList(List<NotificationSchedule> schedules) {
        return schedules.stream().map(this::toDTO).toList();
    }
}
