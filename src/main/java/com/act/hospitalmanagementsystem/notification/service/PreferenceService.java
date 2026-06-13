package com.act.hospitalmanagementsystem.notification.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.notification.dto.PreferenceDTO;
import com.act.hospitalmanagementsystem.notification.dto.UpdatePreferenceRequest;
import com.act.hospitalmanagementsystem.notification.entity.UserPreference;
import com.act.hospitalmanagementsystem.notification.enums.NotificationType;
import com.act.hospitalmanagementsystem.notification.mapper.PreferenceMapper;
import com.act.hospitalmanagementsystem.notification.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final PreferenceMapper preferenceMapper;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<PreferenceDTO>> getUserPreferences(UUID userId) {
        try {
            List<UserPreference> preferences = preferenceRepository.findByUserId(userId);
            return BaseResponseDTO.success(preferenceMapper.toDTOList(preferences), "Preferences retrieved");
        } catch (Exception e) {
            log.error("Error getting user preferences", e);
            return BaseResponseDTO.error("Failed to get preferences: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<PreferenceDTO> updatePreference(UUID userId, UpdatePreferenceRequest request, String updatedBy) {
        try {
            UserPreference preference = preferenceRepository
                    .findByUserIdAndNotificationType(userId, request.getNotificationType())
                    .orElse(new UserPreference());

            if (preference.getId() == null) {
                preference = preferenceMapper.toEntity(request, userId);
                preference.setCreatedBy(updatedBy);
            } else {
                preference.setEmailEnabled(request.getEmailEnabled() != null ? request.getEmailEnabled() : preference.getEmailEnabled());
                preference.setSmsEnabled(request.getSmsEnabled() != null ? request.getSmsEnabled() : preference.getSmsEnabled());
                preference.setPushEnabled(request.getPushEnabled() != null ? request.getPushEnabled() : preference.getPushEnabled());
                preference.setInAppEnabled(request.getInAppEnabled() != null ? request.getInAppEnabled() : preference.getInAppEnabled());
                preference.setQuietHoursEnabled(request.getQuietHoursEnabled() != null ? request.getQuietHoursEnabled() : preference.getQuietHoursEnabled());
                preference.setQuietHoursStart(request.getQuietHoursStart());
                preference.setQuietHoursEnd(request.getQuietHoursEnd());
                preference.setFrequency(request.getFrequency() != null ? request.getFrequency() : preference.getFrequency());
                preference.setUpdatedBy(updatedBy);
            }

            UserPreference saved = preferenceRepository.save(preference);
            return BaseResponseDTO.success(preferenceMapper.toDTO(saved), "Preference updated successfully");
        } catch (Exception e) {
            log.error("Error updating preference", e);
            return BaseResponseDTO.error("Failed to update preference: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<PreferenceDTO>> getDefaultPreferences() {
        try {
            List<PreferenceDTO> defaultPreferences = new java.util.ArrayList<>();
            for (NotificationType type : NotificationType.values()) {
                PreferenceDTO dto = new PreferenceDTO();
                dto.setNotificationType(type);
                dto.setEmailEnabled(true);
                dto.setSmsEnabled(true);
                dto.setPushEnabled(true);
                dto.setInAppEnabled(true);
                dto.setQuietHoursEnabled(false);
                dto.setFrequency("IMMEDIATE");
                defaultPreferences.add(dto);
            }
            return BaseResponseDTO.success(defaultPreferences, "Default preferences retrieved");
        } catch (Exception e) {
            log.error("Error getting default preferences", e);
            return BaseResponseDTO.error("Failed to get default preferences: " + e.getMessage());
        }
    }
}
