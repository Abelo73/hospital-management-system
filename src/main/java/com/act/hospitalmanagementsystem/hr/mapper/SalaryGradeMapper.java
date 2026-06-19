package com.act.hospitalmanagementsystem.hr.mapper;

import com.act.hospitalmanagementsystem.hr.dto.SalaryGradeDTO;
import com.act.hospitalmanagementsystem.hr.entity.SalaryGrade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalaryGradeMapper {

    public SalaryGradeDTO toDTO(SalaryGrade grade) {
        if (grade == null) return null;
        SalaryGradeDTO dto = new SalaryGradeDTO();
        dto.setId(grade.getId());
        dto.setName(grade.getName());
        dto.setCode(grade.getCode());
        dto.setMinSalary(grade.getMinSalary());
        dto.setMaxSalary(grade.getMaxSalary());
        dto.setCurrency(grade.getCurrency());
        dto.setCreatedAt(grade.getCreatedAt());
        dto.setCreatedBy(grade.getCreatedBy());
        return dto;
    }

    public List<SalaryGradeDTO> toDTOList(List<SalaryGrade> grades) {
        return grades.stream().map(this::toDTO).toList();
    }
}
