package com.act.hospitalmanagementsystem.hr.mapper;

import com.act.hospitalmanagementsystem.hr.dto.PositionDTO;
import com.act.hospitalmanagementsystem.hr.entity.Position;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PositionMapper {

    public PositionDTO toDTO(Position position) {
        if (position == null) return null;
        PositionDTO dto = new PositionDTO();
        dto.setId(position.getId());
        dto.setTitle(position.getTitle());
        dto.setCode(position.getCode());
        dto.setDepartmentId(position.getDepartmentId());
        dto.setGradeId(position.getGradeId());
        dto.setMinSalary(position.getMinSalary());
        dto.setMaxSalary(position.getMaxSalary());
        dto.setResponsibilities(position.getResponsibilities());
        dto.setRequiredSkills(position.getRequiredSkills());
        dto.setRequiredQualifications(position.getRequiredQualifications());
        dto.setReportingPositionId(position.getReportingPositionId());
        dto.setIsActive(position.getIsActive());
        dto.setCreatedAt(position.getCreatedAt());
        dto.setCreatedBy(position.getCreatedBy());
        return dto;
    }

    public List<PositionDTO> toDTOList(List<Position> positions) {
        return positions.stream().map(this::toDTO).toList();
    }
}
