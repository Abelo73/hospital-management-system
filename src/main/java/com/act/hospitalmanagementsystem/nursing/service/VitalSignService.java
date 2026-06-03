package com.act.hospitalmanagementsystem.nursing.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.nursing.dto.CreateVitalSignRequest;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateVitalSignRequest;
import com.act.hospitalmanagementsystem.nursing.dto.VitalSignDTO;
import com.act.hospitalmanagementsystem.nursing.entity.VitalSign;
import com.act.hospitalmanagementsystem.nursing.mapper.VitalSignMapper;
import com.act.hospitalmanagementsystem.nursing.repository.VitalSignRepository;
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
public class VitalSignService {

    private final VitalSignRepository vitalSignRepository;
    private final VitalSignMapper vitalSignMapper;

    public VitalSignDTO createVitalSign(CreateVitalSignRequest request) {
        VitalSign vitalSign = vitalSignMapper.toEntity(request);
        vitalSign = vitalSignRepository.save(vitalSign);
        return vitalSignMapper.toDTO(vitalSign);
    }

    public VitalSignDTO getVitalSignById(UUID id) {
        VitalSign vitalSign = vitalSignRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vital Sign", "id", id));
        return vitalSignMapper.toDTO(vitalSign);
    }

    public List<VitalSignDTO> getVitalSignsByPatientId(UUID patientId) {
        List<VitalSign> vitalSigns = vitalSignRepository.findByPatientIdAndDeletedFalseOrderByRecordedDateDescRecordedTimeDesc(patientId);
        return vitalSignMapper.toDTOList(vitalSigns);
    }

    public Page<VitalSignDTO> getVitalSignsByPatientId(UUID patientId, Pageable pageable) {
        Page<VitalSign> vitalSigns = vitalSignRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return vitalSigns.map(vitalSignMapper::toDTO);
    }

    public Page<VitalSignDTO> getVitalSignsByRecordedBy(UUID recordedBy, Pageable pageable) {
        Page<VitalSign> vitalSigns = vitalSignRepository.findByRecordedByAndDeletedFalse(recordedBy, pageable);
        return vitalSigns.map(vitalSignMapper::toDTO);
    }

    public List<VitalSignDTO> getVitalSignsByPatientIdAndDate(UUID patientId, LocalDate date) {
        List<VitalSign> vitalSigns = vitalSignRepository.findByPatientIdAndRecordedDate(patientId, date);
        return vitalSignMapper.toDTOList(vitalSigns);
    }

    public Page<VitalSignDTO> getAbnormalVitalSigns(Pageable pageable) {
        Page<VitalSign> vitalSigns = vitalSignRepository.findAbnormalVitalSigns(pageable);
        return vitalSigns.map(vitalSignMapper::toDTO);
    }

    public Page<VitalSignDTO> getAbnormalVitalSignsByPatient(UUID patientId, Pageable pageable) {
        Page<VitalSign> vitalSigns = vitalSignRepository.findAbnormalVitalSignsByPatient(patientId, pageable);
        return vitalSigns.map(vitalSignMapper::toDTO);
    }

    public List<VitalSignDTO> getLatestVitalSigns(UUID patientId, Pageable pageable) {
        List<VitalSign> vitalSigns = vitalSignRepository.findLatestVitalSigns(patientId, pageable);
        return vitalSignMapper.toDTOList(vitalSigns);
    }

    public VitalSignDTO updateVitalSign(UUID id, UpdateVitalSignRequest request) {
        VitalSign vitalSign = vitalSignRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vital Sign", "id", id));
        vitalSignMapper.updateEntityFromDTO(request, vitalSign);
        vitalSign = vitalSignRepository.save(vitalSign);
        return vitalSignMapper.toDTO(vitalSign);
    }

    public void deleteVitalSign(UUID id) {
        VitalSign vitalSign = vitalSignRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vital Sign", "id", id));
        vitalSign.setDeleted(true);
        vitalSignRepository.save(vitalSign);
    }
}
