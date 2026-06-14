package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.EmployeeDTO;
import com.act.hospitalmanagementsystem.hr.entity.Employee;
import com.act.hospitalmanagementsystem.hr.enums.EmployeeStatus;
import com.act.hospitalmanagementsystem.hr.enums.EmployeeType;
import com.act.hospitalmanagementsystem.hr.mapper.EmployeeMapper;
import com.act.hospitalmanagementsystem.hr.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<EmployeeDTO>> getEmployees(String department, EmployeeType employeeType, EmployeeStatus status, String query, Pageable pageable) {
        try {
            Page<Employee> employees;
            if (query != null) {
                employees = employeeRepository.searchEmployees(query, pageable);
            } else if (department != null) {
                employees = employeeRepository.findByDepartment(department, pageable);
            } else if (employeeType != null) {
                employees = employeeRepository.findByEmployeeType(employeeType, pageable);
            } else if (status != null) {
                employees = employeeRepository.findByStatus(status, pageable);
            } else {
                employees = employeeRepository.findAll(pageable);
            }
            return BaseResponseDTO.success(employeeMapper.toDTOList(employees.getContent()), "Employees retrieved");
        } catch (Exception e) {
            log.error("Error getting employees", e);
            return BaseResponseDTO.error("Failed to get employees: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<EmployeeDTO> getEmployeeById(UUID id) {
        try {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            return BaseResponseDTO.success(employeeMapper.toDTO(employee), "Employee retrieved");
        } catch (Exception e) {
            log.error("Error getting employee", e);
            return BaseResponseDTO.error("Failed to get employee: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<EmployeeDTO> createEmployee(Employee employee, String createdBy) {
        try {
            if (employeeRepository.findByEmployeeNumber(employee.getEmployeeNumber()).isPresent()) {
                return BaseResponseDTO.error("Employee number already exists");
            }
            if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
                return BaseResponseDTO.error("Email already exists");
            }

            employee.setCreatedBy(createdBy);
            Employee saved = employeeRepository.save(employee);

            return BaseResponseDTO.success(employeeMapper.toDTO(saved), "Employee created successfully");
        } catch (Exception e) {
            log.error("Error creating employee", e);
            return BaseResponseDTO.error("Failed to create employee: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<EmployeeDTO> updateEmployee(UUID id, Employee employee, String updatedBy) {
        try {
            Employee existing = employeeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            // Update fields
            existing.setFirstName(employee.getFirstName());
            existing.setLastName(employee.getLastName());
            existing.setMiddleName(employee.getMiddleName());
            existing.setEmail(employee.getEmail());
            existing.setPhoneNumber(employee.getPhoneNumber());
            existing.setDateOfBirth(employee.getDateOfBirth());
            existing.setGender(employee.getGender());
            existing.setAddress(employee.getAddress());
            existing.setCity(employee.getCity());
            existing.setState(employee.getState());
            existing.setCountry(employee.getCountry());
            existing.setPostalCode(employee.getPostalCode());
            existing.setEmployeeType(employee.getEmployeeType());
            existing.setDepartment(employee.getDepartment());
            existing.setPosition(employee.getPosition());
            existing.setSalary(employee.getSalary());
            existing.setBankName(employee.getBankName());
            existing.setBankAccountNumber(employee.getBankAccountNumber());
            existing.setTaxId(employee.getTaxId());
            existing.setSocialSecurityNumber(employee.getSocialSecurityNumber());
            existing.setEmergencyContactName(employee.getEmergencyContactName());
            existing.setEmergencyContactPhone(employee.getEmergencyContactPhone());
            existing.setEmergencyContactRelationship(employee.getEmergencyContactRelationship());
            existing.setProfilePictureUrl(employee.getProfilePictureUrl());
            existing.setNotes(employee.getNotes());
            existing.setUpdatedBy(updatedBy);

            Employee saved = employeeRepository.save(existing);
            return BaseResponseDTO.success(employeeMapper.toDTO(saved), "Employee updated successfully");
        } catch (Exception e) {
            log.error("Error updating employee", e);
            return BaseResponseDTO.error("Failed to update employee: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> terminateEmployee(UUID id, String terminationDate, String reason, String updatedBy) {
        try {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            employee.setStatus(EmployeeStatus.TERMINATED);
            employee.setTerminationDate(java.time.LocalDate.parse(terminationDate));
            employee.setNotes(reason);
            employee.setUpdatedBy(updatedBy);

            employeeRepository.save(employee);
            return BaseResponseDTO.success(null, "Employee terminated successfully");
        } catch (Exception e) {
            log.error("Error terminating employee", e);
            return BaseResponseDTO.error("Failed to terminate employee: " + e.getMessage());
        }
    }
}
