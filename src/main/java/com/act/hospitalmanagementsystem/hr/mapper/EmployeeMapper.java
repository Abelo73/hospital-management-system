package com.act.hospitalmanagementsystem.hr.mapper;

import com.act.hospitalmanagementsystem.hr.dto.EmployeeDTO;
import com.act.hospitalmanagementsystem.hr.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeMapper {

    public EmployeeDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setEmployeeNumber(employee.getEmployeeNumber());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setMiddleName(employee.getMiddleName());
        dto.setEmail(employee.getEmail());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setGender(employee.getGender());
        dto.setAddress(employee.getAddress());
        dto.setCity(employee.getCity());
        dto.setState(employee.getState());
        dto.setCountry(employee.getCountry());
        dto.setPostalCode(employee.getPostalCode());
        dto.setEmployeeType(employee.getEmployeeType());
        dto.setDepartment(employee.getDepartment());
        dto.setPosition(employee.getPosition());
        dto.setHireDate(employee.getHireDate());
        dto.setTerminationDate(employee.getTerminationDate());
        dto.setStatus(employee.getStatus());
        dto.setSalary(employee.getSalary());
        dto.setBankName(employee.getBankName());
        dto.setBankAccountNumber(employee.getBankAccountNumber());
        dto.setTaxId(employee.getTaxId());
        dto.setSocialSecurityNumber(employee.getSocialSecurityNumber());
        dto.setEmergencyContactName(employee.getEmergencyContactName());
        dto.setEmergencyContactPhone(employee.getEmergencyContactPhone());
        dto.setEmergencyContactRelationship(employee.getEmergencyContactRelationship());
        dto.setProfilePictureUrl(employee.getProfilePictureUrl());
        dto.setNotes(employee.getNotes());
        dto.setCreatedAt(employee.getCreatedAt());
        dto.setCreatedBy(employee.getCreatedBy());
        return dto;
    }

    public List<EmployeeDTO> toDTOList(List<Employee> employees) {
        return employees.stream().map(this::toDTO).toList();
    }
}
