package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.RecruitmentDTO;
import com.act.hospitalmanagementsystem.hr.entity.Recruitment;
import com.act.hospitalmanagementsystem.hr.mapper.RecruitmentMapper;
import com.act.hospitalmanagementsystem.hr.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentMapper recruitmentMapper;

    @Transactional
    public BaseResponseDTO<RecruitmentDTO> createJobPosting(String jobTitle, String department, String description,
            String requirements, String responsibilities, Integer vacancies, String postingDate, String closingDate,
            String salaryRange, String location, String employmentType, String createdBy) {
        try {
            Recruitment recruitment = new Recruitment();
            recruitment.setJobTitle(jobTitle);
            recruitment.setDepartment(department);
            recruitment.setDescription(description);
            recruitment.setRequirements(requirements);
            recruitment.setResponsibilities(responsibilities);
            recruitment.setVacancies(vacancies);
            recruitment.setPostingDate(java.time.LocalDate.parse(postingDate));
            recruitment.setClosingDate(closingDate != null ? java.time.LocalDate.parse(closingDate) : null);
            recruitment.setStatus("OPEN");
            recruitment.setSalaryRange(salaryRange);
            recruitment.setLocation(location);
            recruitment.setEmploymentType(employmentType);
            recruitment.setCreatedBy(createdBy);

            Recruitment saved = recruitmentRepository.save(recruitment);
            return BaseResponseDTO.success(recruitmentMapper.toDTO(saved), "Job posting created successfully");
        } catch (Exception e) {
            log.error("Error creating job posting", e);
            return BaseResponseDTO.error("Failed to create job posting: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<RecruitmentDTO>> getJobPostings(String status, String department, Pageable pageable) {
        try {
            Page<Recruitment> recruitments;
            if (status != null) {
                recruitments = recruitmentRepository.findByStatus(status, pageable);
            } else if (department != null) {
                recruitments = recruitmentRepository.findByDepartment(department, pageable);
            } else {
                recruitments = recruitmentRepository.findAll(pageable);
            }
            return BaseResponseDTO.success(recruitmentMapper.toDTOList(recruitments.getContent()), "Job postings retrieved");
        } catch (Exception e) {
            log.error("Error getting job postings", e);
            return BaseResponseDTO.error("Failed to get job postings: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<RecruitmentDTO> updateJobPosting(UUID id, String status, String updatedBy) {
        try {
            Recruitment recruitment = recruitmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Job posting not found"));

            recruitment.setStatus(status);
            recruitment.setUpdatedBy(updatedBy);

            Recruitment saved = recruitmentRepository.save(recruitment);
            return BaseResponseDTO.success(recruitmentMapper.toDTO(saved), "Job posting updated successfully");
        } catch (Exception e) {
            log.error("Error updating job posting", e);
            return BaseResponseDTO.error("Failed to update job posting: " + e.getMessage());
        }
    }
}
