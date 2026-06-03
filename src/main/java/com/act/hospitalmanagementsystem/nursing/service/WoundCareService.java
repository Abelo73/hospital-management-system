package com.act.hospitalmanagementsystem.nursing.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.nursing.dto.CreateWoundCareRequest;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateWoundCareRequest;
import com.act.hospitalmanagementsystem.nursing.dto.WoundCareDTO;
import com.act.hospitalmanagementsystem.nursing.entity.WoundCare;
import com.act.hospitalmanagementsystem.nursing.enums.WoundType;
import com.act.hospitalmanagementsystem.nursing.mapper.WoundCareMapper;
import com.act.hospitalmanagementsystem.nursing.repository.WoundCareRepository;
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
public class WoundCareService {

    private final WoundCareRepository woundCareRepository;
    private final WoundCareMapper woundCareMapper;

    public WoundCareDTO createWoundCare(CreateWoundCareRequest request) {
        WoundCare woundCare = woundCareMapper.toEntity(request);
        woundCare = woundCareRepository.save(woundCare);
        return woundCareMapper.toDTO(woundCare);
    }

    public WoundCareDTO getWoundCareById(UUID id) {
        WoundCare woundCare = woundCareRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wound Care", "id", id));
        return woundCareMapper.toDTO(woundCare);
    }

    public List<WoundCareDTO> getWoundCareByPatientId(UUID patientId) {
        List<WoundCare> woundCareList = woundCareRepository.findByPatientIdAndDeletedFalseOrderByAssessmentDateDesc(patientId);
        return woundCareMapper.toDTOList(woundCareList);
    }

    public Page<WoundCareDTO> getWoundCareByPatientId(UUID patientId, Pageable pageable) {
        Page<WoundCare> woundCareList = woundCareRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return woundCareList.map(woundCareMapper::toDTO);
    }

    public Page<WoundCareDTO> getWoundCareByType(WoundType woundType, Pageable pageable) {
        Page<WoundCare> woundCareList = woundCareRepository.findByWoundTypeAndDeletedFalse(woundType, pageable);
        return woundCareList.map(woundCareMapper::toDTO);
    }

    public Page<WoundCareDTO> getWoundCareByPatientIdAndType(UUID patientId, WoundType woundType, Pageable pageable) {
        Page<WoundCare> woundCareList = woundCareRepository.findByPatientIdAndWoundTypeAndDeletedFalse(patientId, woundType, pageable);
        return woundCareList.map(woundCareMapper::toDTO);
    }

    public Page<WoundCareDTO> getWoundCareByAssessedBy(UUID assessedBy, Pageable pageable) {
        Page<WoundCare> woundCareList = woundCareRepository.findByAssessedByAndDeletedFalse(assessedBy, pageable);
        return woundCareList.map(woundCareMapper::toDTO);
    }

    public List<WoundCareDTO> getWoundCareByDate(LocalDate date) {
        List<WoundCare> woundCareList = woundCareRepository.findByAssessmentDate(date);
        return woundCareMapper.toDTOList(woundCareList);
    }

    public Page<WoundCareDTO> searchWoundCare(String searchTerm, Pageable pageable) {
        Page<WoundCare> woundCareList = woundCareRepository.searchWoundCare(searchTerm, pageable);
        return woundCareList.map(woundCareMapper::toDTO);
    }

    public WoundCareDTO updateWoundCare(UUID id, UpdateWoundCareRequest request) {
        WoundCare woundCare = woundCareRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wound Care", "id", id));
        woundCareMapper.updateEntityFromDTO(request, woundCare);
        woundCare = woundCareRepository.save(woundCare);
        return woundCareMapper.toDTO(woundCare);
    }

    public void deleteWoundCare(UUID id) {
        WoundCare woundCare = woundCareRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wound Care", "id", id));
        woundCare.setDeleted(true);
        woundCareRepository.save(woundCare);
    }
}
