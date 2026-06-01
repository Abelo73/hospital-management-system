package com.act.hospitalmanagementsystem.medical.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.medical.dto.CreateLabResultRequest;
import com.act.hospitalmanagementsystem.medical.dto.LabResultDTO;
import com.act.hospitalmanagementsystem.medical.dto.UpdateLabResultRequest;
import com.act.hospitalmanagementsystem.medical.entity.LabResult;
import com.act.hospitalmanagementsystem.medical.enums.LabResultStatus;
import com.act.hospitalmanagementsystem.medical.mapper.LabResultMapper;
import com.act.hospitalmanagementsystem.medical.repository.LabResultRepository;
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
public class LabResultService {

    private final LabResultRepository labResultRepository;
    private final LabResultMapper labResultMapper;

    public LabResultDTO createLabResult(CreateLabResultRequest request) {
        LabResult labResult = labResultMapper.toEntity(request);
        labResult = labResultRepository.save(labResult);
        return labResultMapper.toDTO(labResult);
    }

    public LabResultDTO getLabResultById(UUID id) {
        LabResult labResult = labResultRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab Result", "id", id));
        return labResultMapper.toDTO(labResult);
    }

    public List<LabResultDTO> getLabResultsByPatientId(UUID patientId) {
        List<LabResult> labResults = labResultRepository.findByPatientIdAndDeletedFalseOrderByTestDateDesc(patientId);
        return labResultMapper.toDTOList(labResults);
    }

    public Page<LabResultDTO> getLabResultsByPatientId(UUID patientId, Pageable pageable) {
        Page<LabResult> labResults = labResultRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return labResults.map(labResultMapper::toDTO);
    }

    public Page<LabResultDTO> getLabResultsByPatientIdAndStatus(UUID patientId, LabResultStatus status, Pageable pageable) {
        Page<LabResult> labResults = labResultRepository.findByPatientIdAndStatusAndDeletedFalse(patientId, status, pageable);
        return labResults.map(labResultMapper::toDTO);
    }

    public Page<LabResultDTO> searchPatientLabResults(UUID patientId, String searchTerm, Pageable pageable) {
        Page<LabResult> labResults = labResultRepository.searchPatientLabResults(patientId, searchTerm, pageable);
        return labResults.map(labResultMapper::toDTO);
    }

    public LabResultDTO updateLabResult(UUID id, UpdateLabResultRequest request) {
        LabResult labResult = labResultRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab Result", "id", id));
        labResultMapper.updateEntityFromDTO(request, labResult);
        labResult = labResultRepository.save(labResult);
        return labResultMapper.toDTO(labResult);
    }

    public void deleteLabResult(UUID id) {
        LabResult labResult = labResultRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab Result", "id", id));
        labResult.setDeleted(true);
        labResultRepository.save(labResult);
    }
}
