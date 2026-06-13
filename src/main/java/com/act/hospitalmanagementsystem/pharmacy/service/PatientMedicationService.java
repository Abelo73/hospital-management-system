package com.act.hospitalmanagementsystem.pharmacy.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.pharmacy.dto.PatientMedicationDTO;
import com.act.hospitalmanagementsystem.pharmacy.entity.PatientMedication;
import com.act.hospitalmanagementsystem.pharmacy.mapper.PatientMedicationMapper;
import com.act.hospitalmanagementsystem.pharmacy.repository.PatientMedicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientMedicationService {

    private final PatientMedicationRepository patientMedicationRepository;
    private final PatientMedicationMapper patientMedicationMapper;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<PatientMedicationDTO>> getMedicationHistory(UUID patientId) {
        try {
            List<PatientMedication> medications = patientMedicationRepository.findByPatientId(patientId);
            return BaseResponseDTO.success(patientMedicationMapper.toDTOList(medications), "Medication history retrieved");
        } catch (Exception e) {
            log.error("Error getting medication history", e);
            return BaseResponseDTO.error("Failed to get medication history: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<PatientMedicationDTO>> getCurrentMedications(UUID patientId) {
        try {
            List<PatientMedication> medications = patientMedicationRepository.findByPatientIdAndIsActiveTrue(patientId);
            return BaseResponseDTO.success(patientMedicationMapper.toDTOList(medications), "Current medications retrieved");
        } catch (Exception e) {
            log.error("Error getting current medications", e);
            return BaseResponseDTO.error("Failed to get current medications: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> addDrugAllergy(UUID patientId, UUID drugId, String allergen, String severity, String reaction, String reportedBy) {
        try {
            // TODO: Implement drug allergy recording
            return BaseResponseDTO.success(null, "Drug allergy recorded");
        } catch (Exception e) {
            log.error("Error adding drug allergy", e);
            return BaseResponseDTO.error("Failed to add drug allergy: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> recordAdverseReaction(UUID patientId, UUID drugId, String reaction, String severity, String onsetDate, String notes) {
        try {
            // TODO: Implement adverse reaction recording
            return BaseResponseDTO.success(null, "Adverse reaction recorded");
        } catch (Exception e) {
            log.error("Error recording adverse reaction", e);
            return BaseResponseDTO.error("Failed to record adverse reaction: " + e.getMessage());
        }
    }
}
