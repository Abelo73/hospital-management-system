package com.act.hospitalmanagementsystem.admin.mapper;

import com.act.hospitalmanagementsystem.admin.dto.AuditLogDTO;
import com.act.hospitalmanagementsystem.admin.dto.ScheduledTaskDTO;
import com.act.hospitalmanagementsystem.admin.dto.SystemConfigDTO;
import com.act.hospitalmanagementsystem.admin.entity.AuditLog;
import com.act.hospitalmanagementsystem.admin.entity.ScheduledTask;
import com.act.hospitalmanagementsystem.admin.entity.SystemConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AuditLogDTO toDTO(AuditLog entity);

    SystemConfigDTO toDTO(SystemConfig entity);

    ScheduledTaskDTO toDTO(ScheduledTask entity);
}
