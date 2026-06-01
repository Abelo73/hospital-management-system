package com.act.hospitalmanagementsystem.medical.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.medical.dto.DiagnosisDTO;
import com.act.hospitalmanagementsystem.medical.dto.CreateDiagnosisRequest;
import com.act.hospitalmanagementsystem.medical.dto.UpdateDiagnosisRequest;
import com.act.hospitalmanagementsystem.medical.entity.Diagnosis;
import com.act.hospitalmanagementsystem.medical.mapper.DiagnosisMapper;
import com.act.hospitalmanagementsystem.medical.repository.DiagnosisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final DiagnosisMapper diagnosisMapper;

    public DiagnosisDTO createDiagnosis(CreateDiagnosisRequest request) {
        Diagnosis diagnosis = diagnosisMapper.toEntity(request);
        diagnosis = diagnosisRepository.save(diagnosis);
        return diagnosisMapper.toDTO(diagnosis);
    }

    public DiagnosisDTO getDiagnosisById(UUID id) {
        Diagnosis diagnosis = diagnosisRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis", "id", id));
        return diagnosisMapper.toDTO(diagnosis);
    }

    public List<DiagnosisDTO> getDiagnosesByMedicalRecordId(UUID medicalRecordId) {
        List<Diagnosis> diagnoses = diagnosisRepository.findByMedicalRecordIdAndDeletedFalseOrderByDiagnosisDateDesc(medicalRecordId);
        return diagnosisMapper.toDTOList(diagnoses);
    }

    public Page<DiagnosisDTO> getDiagnosesByMedicalRecordId(UUID medicalRecordId, Pageable pageable) {
        Page<Diagnosis> diagnoses = diagnosisRepository.findByMedicalRecordIdAndDeletedFalse(medicalRecordId, pageable);
        return diagnoses.map(diagnosisMapper::toDTO);
    }

    public Page<DiagnosisDTO> searchMedicalRecordDiagnoses(UUID medicalRecordId, String searchTerm, Pageable pageable) {
        Page<Diagnosis> diagnoses = diagnosisRepository.searchMedicalRecordDiagnoses(medicalRecordId, searchTerm, pageable);
        return diagnoses.map(diagnosisMapper::toDTO);
    }

    public DiagnosisDTO updateDiagnosis(UUID id, UpdateDiagnosisRequest request) {
        Diagnosis diagnosis = diagnosisRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis", "id", id));
        diagnosisMapper.updateEntityFromDTO(request, diagnosis);
        diagnosis = diagnosisRepository.save(diagnosis);
        return diagnosisMapper.toDTO(diagnosis);
    }

    public void deleteDiagnosis(UUID id) {
        Diagnosis diagnosis = diagnosisRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis", "id", id));
        diagnosis.setDeleted(true);
        diagnosisRepository.save(diagnosis);
    }
}
