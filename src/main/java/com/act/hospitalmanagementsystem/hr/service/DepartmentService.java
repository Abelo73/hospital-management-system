package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.DepartmentDTO;
import com.act.hospitalmanagementsystem.hr.entity.Department;
import com.act.hospitalmanagementsystem.hr.mapper.DepartmentMapper;
import com.act.hospitalmanagementsystem.hr.repository.DepartmentRepository;
import com.act.hospitalmanagementsystem.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentMapper departmentMapper;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<DepartmentDTO>> getAll(UUID branchId) {
        try {
            List<Department> depts = branchId != null
                    ? departmentRepository.findByBranchIdAndDeletedFalse(branchId)
                    : departmentRepository.findByDeletedFalseOrderByNameAsc();
            List<DepartmentDTO> dtos = departmentMapper.toDTOList(depts);
            dtos.forEach(dto -> {
                long count = employeeRepository.countByBranchIdAndDeletedFalse(dto.getId());
                dto.setEmployeeCount(count);
            });
            return BaseResponseDTO.success("Departments retrieved", dtos);
        } catch (Exception e) {
            log.error("Error retrieving departments", e);
            return BaseResponseDTO.error("Failed to retrieve departments: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<DepartmentDTO> getById(UUID id) {
        try {
            Department dept = departmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            DepartmentDTO dto = departmentMapper.toDTO(dept);
            dto.setEmployeeCount(employeeRepository.countByBranchIdAndDeletedFalse(dept.getId()));
            return BaseResponseDTO.success("Department retrieved", dto);
        } catch (Exception e) {
            log.error("Error retrieving department {}", id, e);
            return BaseResponseDTO.error("Department not found: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<DepartmentDTO> create(Department dept, String createdBy) {
        try {
            if (departmentRepository.existsByCodeAndDeletedFalse(dept.getCode())) {
                return BaseResponseDTO.error("Department code already exists: " + dept.getCode());
            }
            dept.setCreatedBy(createdBy);
            dept.setDeleted(false);
            if (dept.getStatus() == null) dept.setStatus("ACTIVE");
            Department saved = departmentRepository.save(dept);
            return BaseResponseDTO.success("Department created", departmentMapper.toDTO(saved));
        } catch (Exception e) {
            log.error("Error creating department", e);
            return BaseResponseDTO.error("Failed to create department: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<DepartmentDTO> update(UUID id, Department updates, String updatedBy) {
        try {
            Department dept = departmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            if (updates.getName() != null) dept.setName(updates.getName());
            if (updates.getBranchId() != null) dept.setBranchId(updates.getBranchId());
            if (updates.getDepartmentHeadEmployeeId() != null) dept.setDepartmentHeadEmployeeId(updates.getDepartmentHeadEmployeeId());
            if (updates.getBudget() != null) dept.setBudget(updates.getBudget());
            if (updates.getDescription() != null) dept.setDescription(updates.getDescription());
            if (updates.getParentDepartmentId() != null) dept.setParentDepartmentId(updates.getParentDepartmentId());
            if (updates.getStatus() != null) dept.setStatus(updates.getStatus());
            dept.setUpdatedBy(updatedBy);
            return BaseResponseDTO.success("Department updated", departmentMapper.toDTO(departmentRepository.save(dept)));
        } catch (Exception e) {
            log.error("Error updating department {}", id, e);
            return BaseResponseDTO.error("Failed to update department: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> delete(UUID id, String updatedBy) {
        try {
            Department dept = departmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            long employeeCount = employeeRepository.countByBranchIdAndDeletedFalse(id);
            if (employeeCount > 0) {
                return BaseResponseDTO.error("Cannot delete: " + employeeCount + " active employee(s) in this department");
            }
            dept.setDeleted(true);
            dept.setStatus("INACTIVE");
            dept.setUpdatedBy(updatedBy);
            departmentRepository.save(dept);
            return BaseResponseDTO.success("Department deleted", null);
        } catch (Exception e) {
            log.error("Error deleting department {}", id, e);
            return BaseResponseDTO.error("Failed to delete department: " + e.getMessage());
        }
    }
}
