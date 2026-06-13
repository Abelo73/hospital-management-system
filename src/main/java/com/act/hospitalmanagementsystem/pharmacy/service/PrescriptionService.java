package com.act.hospitalmanagementsystem.pharmacy.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.pharmacy.dto.PrescriptionDTO;
import com.act.hospitalmanagementsystem.pharmacy.entity.Prescription;
import com.act.hospitalmanagementsystem.pharmacy.enums.PrescriptionStatus;
import com.act.hospitalmanagementsystem.pharmacy.mapper.PrescriptionMapper;
import com.act.hospitalmanagementsystem.pharmacy.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<PrescriptionDTO>> getPrescriptions(UUID patientId, UUID doctorId, PrescriptionStatus status, Pageable pageable) {
        try {
            Page<Prescription> prescriptions;
            if (patientId != null) {
                prescriptions = prescriptionRepository.findByPatientId(patientId, pageable);
            } else if (doctorId != null) {
                prescriptions = prescriptionRepository.findByDoctorId(doctorId, pageable);
            } else if (status != null) {
                prescriptions = prescriptionRepository.findByStatus(status, pageable);
            } else {
                prescriptions = prescriptionRepository.findAll(pageable);
            }
            return BaseResponseDTO.success(prescriptionMapper.toDTOList(prescriptions.getContent()), "Prescriptions retrieved");
        } catch (Exception e) {
            log.error("Error getting prescriptions", e);
            return BaseResponseDTO.error("Failed to get prescriptions: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<PrescriptionDTO> getPrescriptionByNumber(String prescriptionNumber) {
        try {
            Prescription prescription = prescriptionRepository.findByPrescriptionNumber(prescriptionNumber)
                    .orElseThrow(() -> new RuntimeException("Prescription not found"));
            return BaseResponseDTO.success(prescriptionMapper.toDTO(prescription), "Prescription retrieved");
        } catch (Exception e) {
            log.error("Error getting prescription", e);
            return BaseResponseDTO.error("Failed to get prescription: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<PrescriptionDTO> validatePrescription(UUID id, Boolean validated, String notes, String validatedBy) {
        try {
            Prescription prescription = prescriptionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Prescription not found"));

            if (validated) {
                prescription.setStatus(PrescriptionStatus.VALIDATED);
                prescription.setValidatedBy(UUID.fromString(validatedBy));
                prescription.setValidatedAt(java.time.LocalDateTime.now());
            } else {
                prescription.setStatus(PrescriptionStatus.ON_HOLD);
            }
            prescription.setNotes(notes);
            prescription.setUpdatedBy(validatedBy);

            Prescription saved = prescriptionRepository.save(prescription);
            return BaseResponseDTO.success(prescriptionMapper.toDTO(saved), "Prescription validated");
        } catch (Exception e) {
            log.error("Error validating prescription", e);
            return BaseResponseDTO.error("Failed to validate prescription: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<PrescriptionDTO> processPrescription(UUID id, List<Object> items, String updatedBy) {
        try {
            Prescription prescription = prescriptionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Prescription not found"));

            prescription.setStatus(PrescriptionStatus.DISPENSING);
            prescription.setUpdatedBy(updatedBy);

            // TODO: Process prescription items and update stock

            Prescription saved = prescriptionRepository.save(prescription);
            return BaseResponseDTO.success(prescriptionMapper.toDTO(saved), "Prescription processed");
        } catch (Exception e) {
            log.error("Error processing prescription", e);
            return BaseResponseDTO.error("Failed to process prescription: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> cancelPrescription(UUID id, String reason, String updatedBy) {
        try {
            Prescription prescription = prescriptionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Prescription not found"));

            prescription.setStatus(PrescriptionStatus.CANCELLED);
            prescription.setNotes(reason);
            prescription.setUpdatedBy(updatedBy);

            prescriptionRepository.save(prescription);
            return BaseResponseDTO.success(null, "Prescription cancelled");
        } catch (Exception e) {
            log.error("Error cancelling prescription", e);
            return BaseResponseDTO.error("Failed to cancel prescription: " + e.getMessage());
        }
    }
}
