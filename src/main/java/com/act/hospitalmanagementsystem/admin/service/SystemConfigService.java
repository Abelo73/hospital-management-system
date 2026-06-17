package com.act.hospitalmanagementsystem.admin.service;

import com.act.hospitalmanagementsystem.admin.dto.SystemConfigDTO;
import com.act.hospitalmanagementsystem.admin.dto.UpdateConfigRequest;
import com.act.hospitalmanagementsystem.admin.entity.SystemConfig;
import com.act.hospitalmanagementsystem.admin.enums.ConfigType;
import com.act.hospitalmanagementsystem.admin.mapper.AdminMapper;
import com.act.hospitalmanagementsystem.admin.repository.SystemConfigRepository;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemConfigService {

    private final SystemConfigRepository systemConfigRepository;
    private final AdminMapper adminMapper;

    @PostConstruct
    public void initDefaultConfigs() {
        Map<String, String[]> defaults = Map.of(
            "MAX_LOGIN_ATTEMPTS", new String[]{"5", "Maximum failed login attempts before lockout", "SECURITY"},
            "SESSION_TIMEOUT_MINUTES", new String[]{"60", "Session timeout in minutes", "SECURITY"},
            "PASSWORD_MIN_LENGTH", new String[]{"8", "Minimum password length", "SECURITY"},
            "PAGINATION_DEFAULT_SIZE", new String[]{"20", "Default pagination page size", "GENERAL"},
            "APPOINTMENT_REMINDER_HOURS", new String[]{"24", "Hours before appointment to send reminder", "APPOINTMENTS"}
        );
        defaults.forEach((key, val) -> {
            if (!systemConfigRepository.existsByConfigKeyAndDeletedFalse(key)) {
                SystemConfig config = new SystemConfig();
                config.setConfigKey(key);
                config.setConfigValue(val[0]);
                config.setDefaultValue(val[0]);
                config.setConfigType(ConfigType.NUMBER);
                config.setDescription(val[1]);
                config.setCategory(val[2]);
                config.setIsEditable(true);
                config.setRequiresRestart(false);
                systemConfigRepository.save(config);
            }
        });
    }

    public Page<SystemConfigDTO> getAllConfigs(Pageable pageable) {
        return systemConfigRepository.findByDeletedFalse(pageable).map(adminMapper::toDTO);
    }

    public SystemConfigDTO getByKey(String configKey) {
        SystemConfig config = systemConfigRepository.findByConfigKeyAndDeletedFalse(configKey)
                .orElseThrow(() -> new ResourceNotFoundException("SystemConfig", "key", configKey));
        return adminMapper.toDTO(config);
    }

    public List<SystemConfigDTO> getByCategory(String category) {
        return systemConfigRepository.findByCategoryAndDeletedFalse(category)
                .stream().map(adminMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public SystemConfigDTO updateConfig(String configKey, UpdateConfigRequest request) {
        SystemConfig config = systemConfigRepository.findByConfigKeyAndDeletedFalse(configKey)
                .orElseThrow(() -> new ResourceNotFoundException("SystemConfig", "key", configKey));
        if (!config.getIsEditable()) {
            throw new IllegalStateException("Configuration '" + configKey + "' is not editable");
        }
        config.setConfigValue(request.getConfigValue());
        return adminMapper.toDTO(systemConfigRepository.save(config));
    }

    @Transactional
    public SystemConfigDTO resetToDefault(String configKey) {
        SystemConfig config = systemConfigRepository.findByConfigKeyAndDeletedFalse(configKey)
                .orElseThrow(() -> new ResourceNotFoundException("SystemConfig", "key", configKey));
        config.setConfigValue(config.getDefaultValue());
        return adminMapper.toDTO(systemConfigRepository.save(config));
    }

    public String getValue(String configKey, String defaultValue) {
        return systemConfigRepository.findByConfigKeyAndDeletedFalse(configKey)
                .map(SystemConfig::getConfigValue)
                .orElse(defaultValue);
    }
}
