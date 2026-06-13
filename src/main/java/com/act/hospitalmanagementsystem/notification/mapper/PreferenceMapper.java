package com.act.hospitalmanagementsystem.notification.mapper;

import com.act.hospitalmanagementsystem.notification.dto.PreferenceDTO;
import com.act.hospitalmanagementsystem.notification.dto.UpdatePreferenceRequest;
import com.act.hospitalmanagementsystem.notification.entity.UserPreference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PreferenceMapper {

    public PreferenceDTO toDTO(UserPreference preference) {
        if (preference == null) {
            return null;
        }

        PreferenceDTO dto = new PreferenceDTO();
        dto.setId(preference.getId());
        dto.setUserId(preference.getUserId());
        dto.setNotificationType(preference.getNotificationType());
        dto.setEmailEnabled(preference.getEmailEnabled());
        dto.setSmsEnabled(preference.getSmsEnabled());
        dto.setPushEnabled(preference.getPushEnabled());
        dto.setInAppEnabled(preference.getInAppEnabled());
        dto.setQuietHoursStart(preference.getQuietHoursStart());
        dto.setQuietHoursEnd(preference.getQuietHoursEnd());
        dto.setQuietHoursEnabled(preference.getQuietHoursEnabled());
        dto.setFrequency(preference.getFrequency());
        return dto;
    }

    public UserPreference toEntity(UpdatePreferenceRequest request, UUID userId) {
        if (request == null) {
            return null;
        }

        UserPreference preference = new UserPreference();
        preference.setUserId(userId);
        preference.setNotificationType(request.getNotificationType());
        preference.setEmailEnabled(request.getEmailEnabled() != null ? request.getEmailEnabled() : true);
        preference.setSmsEnabled(request.getSmsEnabled() != null ? request.getSmsEnabled() : true);
        preference.setPushEnabled(request.getPushEnabled() != null ? request.getPushEnabled() : true);
        preference.setInAppEnabled(request.getInAppEnabled() != null ? request.getInAppEnabled() : true);
        preference.setQuietHoursEnabled(request.getQuietHoursEnabled() != null ? request.getQuietHoursEnabled() : false);
        preference.setQuietHoursStart(request.getQuietHoursStart());
        preference.setQuietHoursEnd(request.getQuietHoursEnd());
        preference.setFrequency(request.getFrequency() != null ? request.getFrequency() : "IMMEDIATE");
        return preference;
    }

    public List<PreferenceDTO> toDTOList(List<UserPreference> preferences) {
        return preferences.stream()
                .map(this::toDTO)
                .toList();
    }
}
