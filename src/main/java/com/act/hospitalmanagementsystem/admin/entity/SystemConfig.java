package com.act.hospitalmanagementsystem.admin.entity;

import com.act.hospitalmanagementsystem.admin.enums.ConfigType;
import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admin_system_configs")
public class SystemConfig extends BaseEntity {

    @Column(name = "config_key", unique = true, nullable = false, length = 100)
    private String configKey;

    @Column(name = "config_value", columnDefinition = "TEXT")
    private String configValue;

    @Column(name = "default_value", columnDefinition = "TEXT")
    private String defaultValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "config_type", nullable = false, length = 20)
    private ConfigType configType = ConfigType.STRING;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "is_editable", nullable = false)
    private Boolean isEditable = true;

    @Column(name = "requires_restart", nullable = false)
    private Boolean requiresRestart = false;
}
