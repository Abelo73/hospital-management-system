package com.act.hospitalmanagementsystem.hr.mapper;

import com.act.hospitalmanagementsystem.hr.dto.RecruitmentDTO;
import com.act.hospitalmanagementsystem.hr.entity.Recruitment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecruitmentMapper {

    public RecruitmentDTO toDTO(Recruitment recruitment) {
        if (recruitment == null) {
            return null;
        }

        RecruitmentDTO dto = new RecruitmentDTO();
        dto.setId(recruitment.getId());
        dto.setJobTitle(recruitment.getJobTitle());
        dto.setDepartment(recruitment.getDepartment());
        dto.setDescription(recruitment.getDescription());
        dto.setRequirements(recruitment.getRequirements());
        dto.setResponsibilities(recruitment.getResponsibilities());
        dto.setVacancies(recruitment.getVacancies());
        dto.setPostingDate(recruitment.getPostingDate());
        dto.setClosingDate(recruitment.getClosingDate());
        dto.setStatus(recruitment.getStatus());
        dto.setSalaryRange(recruitment.getSalaryRange());
        dto.setLocation(recruitment.getLocation());
        dto.setEmploymentType(recruitment.getEmploymentType());
        dto.setNotes(recruitment.getNotes());
        dto.setCreatedAt(recruitment.getCreatedAt());
        dto.setCreatedBy(recruitment.getCreatedBy());
        return dto;
    }

    public List<RecruitmentDTO> toDTOList(List<Recruitment> recruitments) {
        return recruitments.stream().map(this::toDTO).toList();
    }
}
