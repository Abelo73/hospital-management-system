package com.act.hospitalmanagementsystem.nursing.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingAssessmentRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingAssessmentDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingAssessmentRequest;
import com.act.hospitalmanagementsystem.nursing.entity.NursingAssessment;
import com.act.hospitalmanagementsystem.nursing.enums.AssessmentType;
import com.act.hospitalmanagementsystem.nursing.mapper.NursingAssessmentMapper;
import com.act.hospitalmanagementsystem.nursing.repository.NursingAssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NursingAssessmentService {

    private final NursingAssessmentRepository nursingAssessmentRepository;
    private final NursingAssessmentMapper nursingAssessmentMapper;

    public NursingAssessmentDTO createAssessment(CreateNursingAssessmentRequest request) {
        NursingAssessment assessment = nursingAssessmentMapper.toEntity(request);
        assessment = nursingAssessmentRepository.save(assessment);
        return nursingAssessmentMapper.toDTO(assessment);
    }

    public NursingAssessmentDTO getAssessmentById(UUID id) {
        NursingAssessment assessment = nursingAssessmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Assessment", "id", id));
        return nursingAssessmentMapper.toDTO(assessment);
    }

    public List<NursingAssessmentDTO> getAssessmentsByPatientId(UUID patientId) {
        List<NursingAssessment> assessments = nursingAssessmentRepository.findByPatientIdAndDeletedFalseOrderByAssessmentDateDesc(patientId);
        return nursingAssessmentMapper.toDTOList(assessments);
    }

    public Page<NursingAssessmentDTO> getAssessmentsByPatientId(UUID patientId, Pageable pageable) {
        Page<NursingAssessment> assessments = nursingAssessmentRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return assessments.map(nursingAssessmentMapper::toDTO);
    }

    public Page<NursingAssessmentDTO> getAssessmentsByType(AssessmentType assessmentType, Pageable pageable) {
        Page<NursingAssessment> assessments = nursingAssessmentRepository.findByAssessmentTypeAndDeletedFalse(assessmentType, pageable);
        return assessments.map(nursingAssessmentMapper::toDTO);
    }

    public Page<NursingAssessmentDTO> getAssessmentsByPatientIdAndType(UUID patientId, AssessmentType assessmentType, Pageable pageable) {
        Page<NursingAssessment> assessments = nursingAssessmentRepository.findByPatientIdAndAssessmentTypeAndDeletedFalse(patientId, assessmentType, pageable);
        return assessments.map(nursingAssessmentMapper::toDTO);
    }

    public Page<NursingAssessmentDTO> getAssessmentsByNurseId(UUID nurseId, Pageable pageable) {
        Page<NursingAssessment> assessments = nursingAssessmentRepository.findByNurseIdAndDeletedFalse(nurseId, pageable);
        return assessments.map(nursingAssessmentMapper::toDTO);
    }

    public List<NursingAssessmentDTO> getAssessmentsByDate(LocalDate date) {
        List<NursingAssessment> assessments = nursingAssessmentRepository.findByAssessmentDate(date);
        return nursingAssessmentMapper.toDTOList(assessments);
    }

    public Page<NursingAssessmentDTO> searchAssessments(String searchTerm, Pageable pageable) {
        Page<NursingAssessment> assessments = nursingAssessmentRepository.searchAssessments(searchTerm, pageable);
        return assessments.map(nursingAssessmentMapper::toDTO);
    }

    public NursingAssessmentDTO updateAssessment(UUID id, UpdateNursingAssessmentRequest request) {
        NursingAssessment assessment = nursingAssessmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Assessment", "id", id));
        nursingAssessmentMapper.updateEntityFromDTO(request, assessment);
        assessment = nursingAssessmentRepository.save(assessment);
        return nursingAssessmentMapper.toDTO(assessment);
    }

    public void deleteAssessment(UUID id) {
        NursingAssessment assessment = nursingAssessmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Assessment", "id", id));
        assessment.setDeleted(true);
        nursingAssessmentRepository.save(assessment);
    }
}
