package com.act.hospitalmanagementsystem.medical.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.medical.dto.CreateMedicationRequest;
import com.act.hospitalmanagementsystem.medical.dto.MedicationDTO;
import com.act.hospitalmanagementsystem.medical.dto.UpdateMedicationRequest;
import com.act.hospitalmanagementsystem.medical.entity.Medication;
import com.act.hospitalmanagementsystem.medical.enums.MedicationStatus;
import com.act.hospitalmanagementsystem.medical.mapper.MedicationMapper;
import com.act.hospitalmanagementsystem.medical.repository.MedicationRepository;
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
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    public MedicationDTO createMedication(CreateMedicationRequest request) {
        Medication medication = medicationMapper.toEntity(request);
        medication = medicationRepository.save(medication);
        return medicationMapper.toDTO(medication);
    }

    public MedicationDTO getMedicationById(UUID id) {
        Medication medication = medicationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication", "id", id));
        return medicationMapper.toDTO(medication);
    }

    public List<MedicationDTO> getMedicationsByPatientId(UUID patientId) {
        List<Medication> medications = medicationRepository.findByPatientIdAndDeletedFalseOrderByStartDateDesc(patientId);
        return medicationMapper.toDTOList(medications);
    }

    public List<MedicationDTO> getActiveMedicationsByPatientId(UUID patientId) {
        List<Medication> medications = medicationRepository.findByPatientIdAndStatusAndDeletedFalse(patientId, MedicationStatus.ACTIVE);
        return medicationMapper.toDTOList(medications);
    }

    public Page<MedicationDTO> getMedicationsByPatientId(UUID patientId, Pageable pageable) {
        Page<Medication> medications = medicationRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return medications.map(medicationMapper::toDTO);
    }

    public Page<MedicationDTO> getMedicationsByPatientIdAndStatus(UUID patientId, MedicationStatus status, Pageable pageable) {
        Page<Medication> medications = medicationRepository.findByPatientIdAndStatusAndDeletedFalse(patientId, status, pageable);
        return medications.map(medicationMapper::toDTO);
    }

    public Page<MedicationDTO> searchPatientMedications(UUID patientId, String searchTerm, Pageable pageable) {
        Page<Medication> medications = medicationRepository.searchPatientMedications(patientId, searchTerm, pageable);
        return medications.map(medicationMapper::toDTO);
    }

    public MedicationDTO updateMedication(UUID id, UpdateMedicationRequest request) {
        Medication medication = medicationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication", "id", id));
        medicationMapper.updateEntityFromDTO(request, medication);
        medication = medicationRepository.save(medication);
        return medicationMapper.toDTO(medication);
    }

    public void deleteMedication(UUID id) {
        Medication medication = medicationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication", "id", id));
        medication.setDeleted(true);
        medicationRepository.save(medication);
    }
}
