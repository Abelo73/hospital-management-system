package com.act.hospitalmanagementsystem.hr.mapper;

import com.act.hospitalmanagementsystem.hr.dto.DepartmentDTO;
import com.act.hospitalmanagementsystem.hr.entity.Department;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentMapper {

    public DepartmentDTO toDTO(Department dept) {
        if (dept == null) return null;
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(dept.getId());
        dto.setName(dept.getName());
        dto.setCode(dept.getCode());
        dto.setBranchId(dept.getBranchId());
        dto.setDepartmentHeadEmployeeId(dept.getDepartmentHeadEmployeeId());
        dto.setBudget(dept.getBudget());
        dto.setDescription(dept.getDescription());
        dto.setParentDepartmentId(dept.getParentDepartmentId());
        dto.setStatus(dept.getStatus());
        dto.setCreatedAt(dept.getCreatedAt());
        dto.setCreatedBy(dept.getCreatedBy());
        return dto;
    }

    public List<DepartmentDTO> toDTOList(List<Department> depts) {
        return depts.stream().map(this::toDTO).toList();
    }
}
