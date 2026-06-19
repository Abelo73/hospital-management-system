package com.act.hospitalmanagementsystem.hr.mapper;

import com.act.hospitalmanagementsystem.hr.dto.BranchDTO;
import com.act.hospitalmanagementsystem.hr.entity.Branch;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BranchMapper {

    public BranchDTO toDTO(Branch branch) {
        if (branch == null) return null;
        BranchDTO dto = new BranchDTO();
        dto.setId(branch.getId());
        dto.setName(branch.getName());
        dto.setCode(branch.getCode());
        dto.setAddress(branch.getAddress());
        dto.setCity(branch.getCity());
        dto.setCountry(branch.getCountry());
        dto.setPhone(branch.getPhone());
        dto.setEmail(branch.getEmail());
        dto.setBranchType(branch.getBranchType());
        dto.setParentBranchId(branch.getParentBranchId());
        dto.setStatus(branch.getStatus());
        dto.setCreatedAt(branch.getCreatedAt());
        dto.setCreatedBy(branch.getCreatedBy());
        return dto;
    }

    public List<BranchDTO> toDTOList(List<Branch> branches) {
        return branches.stream().map(this::toDTO).toList();
    }
}
