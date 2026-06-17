package com.act.hospitalmanagementsystem.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateConfigRequest {
    @NotBlank(message = "Config value is required")
    private String configValue;
}
