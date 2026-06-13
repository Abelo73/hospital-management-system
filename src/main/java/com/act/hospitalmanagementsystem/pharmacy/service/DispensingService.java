package com.act.hospitalmanagementsystem.pharmacy.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.pharmacy.dto.DispensingDTO;
import com.act.hospitalmanagementsystem.pharmacy.entity.Dispensing;
import com.act.hospitalmanagementsystem.pharmacy.entity.Prescription;
import com.act.hospitalmanagementsystem.pharmacy.enums.DispensingStatus;
import com.act.hospitalmanagementsystem.pharmacy.mapper.DispensingMapper;
import com.act.hospitalmanagementsystem.pharmacy.repository.DispensingRepository;
import com.act.hospitalmanagementsystem.pharmacy.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispensingService {

    private final DispensingRepository dispensingRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final DispensingMapper dispensingMapper;

    @Transactional
    public BaseResponseDTO<DispensingDTO> createDispensing(UUID prescriptionId, List<Map<String, Object>> items, String createdBy) {
        try {
            Prescription prescription = prescriptionRepository.findById(prescriptionId)
                    .orElseThrow(() -> new RuntimeException("Prescription not found"));

            Dispensing dispensing = new Dispensing();
            dispensing.setDispensingNumber(generateDispensingNumber());
            dispensing.setPrescription(prescription);
            dispensing.setPatientId(prescription.getPatientId());
            dispensing.setDispensingDate(LocalDate.now());
            dispensing.setStatus(DispensingStatus.PENDING);
            dispensing.setCreatedBy(createdBy);

            // TODO: Calculate totals based on items
            // TODO: Create dispensing items

            Dispensing saved = dispensingRepository.save(dispensing);
            return BaseResponseDTO.success(dispensingMapper.toDTO(saved), "Dispensing created successfully");
        } catch (Exception e) {
            log.error("Error creating dispensing", e);
            return BaseResponseDTO.error("Failed to create dispensing: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<DispensingDTO> getDispensingByNumber(String dispensingNumber) {
        try {
            Dispensing dispensing = dispensingRepository.findByDispensingNumber(dispensingNumber)
                    .orElseThrow(() -> new RuntimeException("Dispensing not found"));
            return BaseResponseDTO.success(dispensingMapper.toDTO(dispensing), "Dispensing retrieved");
        } catch (Exception e) {
            log.error("Error getting dispensing", e);
            return BaseResponseDTO.error("Failed to get dispensing: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<DispensingDTO> completeDispensing(UUID id, Boolean counselingProvided, String counselingNotes, UUID receivedBy) {
        try {
            Dispensing dispensing = dispensingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Dispensing not found"));

            dispensing.setStatus(DispensingStatus.COMPLETED);
            dispensing.setCounselingProvided(counselingProvided);
            dispensing.setCounselingNotes(counselingNotes);
            dispensing.setUpdatedBy(receivedBy.toString());

            // Update prescription status
            if (dispensing.getPrescription() != null) {
                dispensing.getPrescription().setStatus(com.act.hospitalmanagementsystem.pharmacy.enums.PrescriptionStatus.DISPENSED);
                dispensing.getPrescription().setDispensedAt(java.time.LocalDateTime.now());
                prescriptionRepository.save(dispensing.getPrescription());
            }

            Dispensing saved = dispensingRepository.save(dispensing);
            return BaseResponseDTO.success(dispensingMapper.toDTO(saved), "Dispensing completed");
        } catch (Exception e) {
            log.error("Error completing dispensing", e);
            return BaseResponseDTO.error("Failed to complete dispensing: " + e.getMessage());
        }
    }

    private String generateDispensingNumber() {
        return "DSP-" + System.currentTimeMillis();
    }
}
