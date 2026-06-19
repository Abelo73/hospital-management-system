package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.SalaryGradeDTO;
import com.act.hospitalmanagementsystem.hr.entity.SalaryGrade;
import com.act.hospitalmanagementsystem.hr.mapper.SalaryGradeMapper;
import com.act.hospitalmanagementsystem.hr.repository.SalaryGradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryGradeService {

    private final SalaryGradeRepository salaryGradeRepository;
    private final SalaryGradeMapper salaryGradeMapper;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<SalaryGradeDTO>> getAll() {
        try {
            List<SalaryGrade> grades = salaryGradeRepository.findByDeletedFalseOrderByNameAsc();
            return BaseResponseDTO.success("Salary grades retrieved", salaryGradeMapper.toDTOList(grades));
        } catch (Exception e) {
            log.error("Error retrieving salary grades", e);
            return BaseResponseDTO.error("Failed to retrieve salary grades: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<SalaryGradeDTO> getById(UUID id) {
        try {
            SalaryGrade grade = salaryGradeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Salary grade not found"));
            return BaseResponseDTO.success("Salary grade retrieved", salaryGradeMapper.toDTO(grade));
        } catch (Exception e) {
            log.error("Error retrieving salary grade {}", id, e);
            return BaseResponseDTO.error("Salary grade not found: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<SalaryGradeDTO> create(SalaryGrade grade, String createdBy) {
        try {
            if (salaryGradeRepository.existsByCodeAndDeletedFalse(grade.getCode())) {
                return BaseResponseDTO.error("Salary grade code already exists: " + grade.getCode());
            }
            if (grade.getMinSalary() != null && grade.getMaxSalary() != null
                    && grade.getMinSalary() >= grade.getMaxSalary()) {
                return BaseResponseDTO.error("minSalary must be less than maxSalary");
            }
            if (grade.getCurrency() == null || grade.getCurrency().isBlank()) {
                grade.setCurrency("USD");
            }
            grade.setCreatedBy(createdBy);
            grade.setDeleted(false);
            SalaryGrade saved = salaryGradeRepository.save(grade);
            return BaseResponseDTO.success("Salary grade created", salaryGradeMapper.toDTO(saved));
        } catch (Exception e) {
            log.error("Error creating salary grade", e);
            return BaseResponseDTO.error("Failed to create salary grade: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<SalaryGradeDTO> update(UUID id, SalaryGrade updates, String updatedBy) {
        try {
            SalaryGrade grade = salaryGradeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Salary grade not found"));
            if (updates.getName() != null) grade.setName(updates.getName());
            if (updates.getMinSalary() != null) grade.setMinSalary(updates.getMinSalary());
            if (updates.getMaxSalary() != null) grade.setMaxSalary(updates.getMaxSalary());
            if (updates.getCurrency() != null) grade.setCurrency(updates.getCurrency());
            if (grade.getMinSalary() != null && grade.getMaxSalary() != null
                    && grade.getMinSalary() >= grade.getMaxSalary()) {
                return BaseResponseDTO.error("minSalary must be less than maxSalary");
            }
            grade.setUpdatedBy(updatedBy);
            return BaseResponseDTO.success("Salary grade updated", salaryGradeMapper.toDTO(salaryGradeRepository.save(grade)));
        } catch (Exception e) {
            log.error("Error updating salary grade {}", id, e);
            return BaseResponseDTO.error("Failed to update salary grade: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> delete(UUID id, String updatedBy) {
        try {
            SalaryGrade grade = salaryGradeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Salary grade not found"));
            grade.setDeleted(true);
            grade.setUpdatedBy(updatedBy);
            salaryGradeRepository.save(grade);
            return BaseResponseDTO.success("Salary grade deleted", null);
        } catch (Exception e) {
            log.error("Error deleting salary grade {}", id, e);
            return BaseResponseDTO.error("Failed to delete salary grade: " + e.getMessage());
        }
    }
}
