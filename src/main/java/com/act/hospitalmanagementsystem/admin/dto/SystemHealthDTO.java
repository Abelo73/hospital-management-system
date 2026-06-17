package com.act.hospitalmanagementsystem.admin.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class SystemHealthDTO {
    private String status;
    private Map<String, Object> components;
    private LocalDateTime timestamp;
}
