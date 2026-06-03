package com.act.hospitalmanagementsystem.nursing.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.nursing.dto.CreateMedicationAdministrationRequest;
import com.act.hospitalmanagementsystem.nursing.dto.MedicationAdministrationDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateMedicationAdministrationRequest;
import com.act.hospitalmanagementsystem.nursing.entity.MedicationAdministration;
import com.act.hospitalmanagementsystem.nursing.enums.AdministrationStatus;
import com.act.hospitalmanagementsystem.nursing.mapper.MedicationAdministrationMapper;
import com.act.hospitalmanagementsystem.nursing.repository.MedicationAdministrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicationAdministrationService {

    private final MedicationAdministrationRepository medicationAdministrationRepository;
    private final MedicationAdministrationMapper medicationAdministrationMapper;

    public MedicationAdministrationDTO createMedicationAdministration(CreateMedicationAdministrationRequest request) {
        MedicationAdministration administration = medicationAdministrationMapper.toEntity(request);
        administration = medicationAdministrationRepository.save(administration);
        return medicationAdministrationMapper.toDTO(administration);
    }

    public MedicationAdministrationDTO getMedicationAdministrationById(UUID id) {
        MedicationAdministration administration = medicationAdministrationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication Administration", "id", id));
        return medicationAdministrationMapper.toDTO(administration);
    }

    public List<MedicationAdministrationDTO> getMedicationAdministrationsByPatientId(UUID patientId) {
        List<MedicationAdministration> administrations = medicationAdministrationRepository.findByPatientIdAndDeletedFalseOrderByScheduledDateDescScheduledTimeDesc(patientId);
        return medicationAdministrationMapper.toDTOList(administrations);
    }

    public Page<MedicationAdministrationDTO> getMedicationAdministrationsByPatientId(UUID patientId, Pageable pageable) {
        Page<MedicationAdministration> administrations = medicationAdministrationRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return administrations.map(medicationAdministrationMapper::toDTO);
    }

    public Page<MedicationAdministrationDTO> getMedicationAdministrationsByAdministeredBy(UUID administeredBy, Pageable pageable) {
        Page<MedicationAdministration> administrations = medicationAdministrationRepository.findByAdministeredByAndDeletedFalse(administeredBy, pageable);
        return administrations.map(medicationAdministrationMapper::toDTO);
    }

    public Page<MedicationAdministrationDTO> getMedicationAdministrationsByStatus(AdministrationStatus status, Pageable pageable) {
        Page<MedicationAdministration> administrations = medicationAdministrationRepository.findByAdministrationStatusAndDeletedFalse(status, pageable);
        return administrations.map(medicationAdministrationMapper::toDTO);
    }

    public List<MedicationAdministrationDTO> getScheduledMedications(LocalDate date) {
        List<MedicationAdministration> administrations = medicationAdministrationRepository.findScheduledMedications(date);
        return medicationAdministrationMapper.toDTOList(administrations);
    }

    public List<MedicationAdministrationDTO> getOverdueMedications(LocalDate date, LocalTime time) {
        List<MedicationAdministration> administrations = medicationAdministrationRepository.findOverdueMedications(date, time);
        return medicationAdministrationMapper.toDTOList(administrations);
    }

    public Page<MedicationAdministrationDTO> getMedicationAdministrationsByDateRange(UUID patientId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<MedicationAdministration> administrations = medicationAdministrationRepository.findByPatientIdAndDateRange(patientId, startDate, endDate, pageable);
        return administrations.map(medicationAdministrationMapper::toDTO);
    }

    public MedicationAdministrationDTO updateMedicationAdministration(UUID id, UpdateMedicationAdministrationRequest request) {
        MedicationAdministration administration = medicationAdministrationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication Administration", "id", id));
        medicationAdministrationMapper.updateEntityFromDTO(request, administration);
        administration = medicationAdministrationRepository.save(administration);
        return medicationAdministrationMapper.toDTO(administration);
    }

    public void deleteMedicationAdministration(UUID id) {
        MedicationAdministration administration = medicationAdministrationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication Administration", "id", id));
        administration.setDeleted(true);
        medicationAdministrationRepository.save(administration);
    }
}
