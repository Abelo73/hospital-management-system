package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.PositionDTO;
import com.act.hospitalmanagementsystem.hr.entity.Position;
import com.act.hospitalmanagementsystem.hr.mapper.PositionMapper;
import com.act.hospitalmanagementsystem.hr.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<PositionDTO>> getAll(UUID departmentId) {
        try {
            List<Position> positions = departmentId != null
                    ? positionRepository.findByDepartmentIdAndDeletedFalse(departmentId)
                    : positionRepository.findByDeletedFalseOrderByTitleAsc();
            return BaseResponseDTO.success("Positions retrieved", positionMapper.toDTOList(positions));
        } catch (Exception e) {
            log.error("Error retrieving positions", e);
            return BaseResponseDTO.error("Failed to retrieve positions: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<PositionDTO> getById(UUID id) {
        try {
            Position position = positionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Position not found"));
            return BaseResponseDTO.success("Position retrieved", positionMapper.toDTO(position));
        } catch (Exception e) {
            log.error("Error retrieving position {}", id, e);
            return BaseResponseDTO.error("Position not found: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<PositionDTO> create(Position position, String createdBy) {
        try {
            if (positionRepository.existsByCodeAndDeletedFalse(position.getCode())) {
                return BaseResponseDTO.error("Position code already exists: " + position.getCode());
            }
            if (position.getMinSalary() != null && position.getMaxSalary() != null
                    && position.getMinSalary() >= position.getMaxSalary()) {
                return BaseResponseDTO.error("minSalary must be less than maxSalary");
            }
            if (position.getIsActive() == null) {
                position.setIsActive(true);
            }
            position.setCreatedBy(createdBy);
            position.setDeleted(false);
            Position saved = positionRepository.save(position);
            return BaseResponseDTO.success("Position created", positionMapper.toDTO(saved));
        } catch (Exception e) {
            log.error("Error creating position", e);
            return BaseResponseDTO.error("Failed to create position: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<PositionDTO> update(UUID id, Position updates, String updatedBy) {
        try {
            Position position = positionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Position not found"));
            if (updates.getTitle() != null) position.setTitle(updates.getTitle());
            if (updates.getDepartmentId() != null) position.setDepartmentId(updates.getDepartmentId());
            if (updates.getGradeId() != null) position.setGradeId(updates.getGradeId());
            if (updates.getMinSalary() != null) position.setMinSalary(updates.getMinSalary());
            if (updates.getMaxSalary() != null) position.setMaxSalary(updates.getMaxSalary());
            if (updates.getResponsibilities() != null) position.setResponsibilities(updates.getResponsibilities());
            if (updates.getRequiredSkills() != null) position.setRequiredSkills(updates.getRequiredSkills());
            if (updates.getRequiredQualifications() != null) position.setRequiredQualifications(updates.getRequiredQualifications());
            if (updates.getReportingPositionId() != null) position.setReportingPositionId(updates.getReportingPositionId());
            if (updates.getIsActive() != null) position.setIsActive(updates.getIsActive());
            if (position.getMinSalary() != null && position.getMaxSalary() != null
                    && position.getMinSalary() >= position.getMaxSalary()) {
                return BaseResponseDTO.error("minSalary must be less than maxSalary");
            }
            position.setUpdatedBy(updatedBy);
            return BaseResponseDTO.success("Position updated", positionMapper.toDTO(positionRepository.save(position)));
        } catch (Exception e) {
            log.error("Error updating position {}", id, e);
            return BaseResponseDTO.error("Failed to update position: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> delete(UUID id, String updatedBy) {
        try {
            Position position = positionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Position not found"));
            position.setIsActive(false);
            position.setDeleted(true);
            position.setUpdatedBy(updatedBy);
            positionRepository.save(position);
            return BaseResponseDTO.success("Position deleted", null);
        } catch (Exception e) {
            log.error("Error deleting position {}", id, e);
            return BaseResponseDTO.error("Failed to delete position: " + e.getMessage());
        }
    }
}
