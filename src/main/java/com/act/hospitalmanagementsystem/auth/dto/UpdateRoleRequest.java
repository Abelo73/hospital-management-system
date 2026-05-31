package com.act.hospitalmanagementsystem.auth.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequest {
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    private Set<String> permissions;
}
