package com.act.hospitalmanagementsystem.notification.mapper;

import com.act.hospitalmanagementsystem.notification.dto.NotificationDTO;
import com.act.hospitalmanagementsystem.notification.entity.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationMapper {

    private final ObjectMapper objectMapper;

    public NotificationMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public NotificationDTO toDTO(Notification notification) {
        if (notification == null) {
            return null;
        }

        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setNotificationType(notification.getNotificationType());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setChannels(notification.getChannels());
        dto.setPriority(notification.getPriority());
        dto.setStatus(notification.getStatus());
        dto.setRecipientType(notification.getRecipientType());
        dto.setRecipientId(notification.getRecipientId());
        dto.setRecipientEmail(notification.getRecipientEmail());
        dto.setRecipientPhone(notification.getRecipientPhone());
        dto.setTemplateId(notification.getTemplate() != null ? notification.getTemplate().getId() : null);
        dto.setVariables(notification.getVariables());
        dto.setScheduledFor(notification.getScheduledFor());
        dto.setSentAt(notification.getSentAt());
        dto.setDeliveredAt(notification.getDeliveredAt());
        dto.setFailedAt(notification.getFailedAt());
        dto.setFailureReason(notification.getFailureReason());
        dto.setRetryCount(notification.getRetryCount());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setCreatedBy(notification.getCreatedBy());
        return dto;
    }

    public Notification toEntity(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }

        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setNotificationType(dto.getNotificationType());
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        notification.setChannels(dto.getChannels());
        notification.setPriority(dto.getPriority());
        notification.setStatus(dto.getStatus());
        notification.setRecipientType(dto.getRecipientType());
        notification.setRecipientId(dto.getRecipientId());
        notification.setRecipientEmail(dto.getRecipientEmail());
        notification.setRecipientPhone(dto.getRecipientPhone());
        notification.setVariables(dto.getVariables());
        notification.setScheduledFor(dto.getScheduledFor());
        notification.setSentAt(dto.getSentAt());
        notification.setDeliveredAt(dto.getDeliveredAt());
        notification.setFailedAt(dto.getFailedAt());
        notification.setFailureReason(dto.getFailureReason());
        notification.setRetryCount(dto.getRetryCount());
        return notification;
    }

    public List<NotificationDTO> toDTOList(List<Notification> notifications) {
        return notifications.stream()
                .map(this::toDTO)
                .toList();
    }

    public String channelsToJson(List<String> channels) {
        try {
            return objectMapper.writeValueAsString(channels);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    public String variablesToJson(Object variables) {
        try {
            return objectMapper.writeValueAsString(variables);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
