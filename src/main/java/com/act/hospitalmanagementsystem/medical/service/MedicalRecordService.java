package com.act.hospitalmanagementsystem.medical.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.medical.dto.CreateMedicalRecordRequest;
import com.act.hospitalmanagementsystem.medical.dto.MedicalRecordDTO;
import com.act.hospitalmanagementsystem.medical.dto.UpdateMedicalRecordRequest;
import com.act.hospitalmanagementsystem.medical.entity.MedicalRecord;
import com.act.hospitalmanagementsystem.medical.enums.RecordStatus;
import com.act.hospitalmanagementsystem.medical.enums.RecordType;
import com.act.hospitalmanagementsystem.medical.mapper.MedicalRecordMapper;
import com.act.hospitalmanagementsystem.medical.repository.MedicalRecordRepository;
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
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    public MedicalRecordDTO createMedicalRecord(CreateMedicalRecordRequest request) {
        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(request);
        medicalRecord = medicalRecordRepository.save(medicalRecord);
        return medicalRecordMapper.toDTO(medicalRecord);
    }

    public MedicalRecordDTO getMedicalRecordById(UUID id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical Record", "id", id));
        return medicalRecordMapper.toDTO(medicalRecord);
    }

    public List<MedicalRecordDTO> getMedicalRecordsByPatientId(UUID patientId) {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientIdAndDeletedFalseOrderByRecordDateDesc(patientId);
        return medicalRecordMapper.toDTOList(medicalRecords);
    }

    public Page<MedicalRecordDTO> getMedicalRecordsByPatientId(UUID patientId, Pageable pageable) {
        Page<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return medicalRecords.map(medicalRecordMapper::toDTO);
    }

    public Page<MedicalRecordDTO> getMedicalRecordsByType(RecordType recordType, Pageable pageable) {
        Page<MedicalRecord> medicalRecords = medicalRecordRepository.findByRecordTypeAndDeletedFalse(recordType, pageable);
        return medicalRecords.map(medicalRecordMapper::toDTO);
    }

    public Page<MedicalRecordDTO> getMedicalRecordsByPatientIdAndType(UUID patientId, RecordType recordType, Pageable pageable) {
        Page<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientIdAndRecordTypeAndDeletedFalse(patientId, recordType, pageable);
        return medicalRecords.map(medicalRecordMapper::toDTO);
    }

    public Page<MedicalRecordDTO> getMedicalRecordsByStatus(RecordStatus status, Pageable pageable) {
        Page<MedicalRecord> medicalRecords = medicalRecordRepository.findByStatusAndDeletedFalse(status, pageable);
        return medicalRecords.map(medicalRecordMapper::toDTO);
    }

    public Page<MedicalRecordDTO> searchMedicalRecords(String searchTerm, Pageable pageable) {
        Page<MedicalRecord> medicalRecords = medicalRecordRepository.searchMedicalRecords(searchTerm, pageable);
        return medicalRecords.map(medicalRecordMapper::toDTO);
    }

    public Page<MedicalRecordDTO> searchPatientMedicalRecords(UUID patientId, String searchTerm, Pageable pageable) {
        Page<MedicalRecord> medicalRecords = medicalRecordRepository.searchPatientMedicalRecords(patientId, searchTerm, pageable);
        return medicalRecords.map(medicalRecordMapper::toDTO);
    }

    public MedicalRecordDTO updateMedicalRecord(UUID id, UpdateMedicalRecordRequest request) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical Record", "id", id));
        medicalRecordMapper.updateEntityFromDTO(request, medicalRecord);
        medicalRecord = medicalRecordRepository.save(medicalRecord);
        return medicalRecordMapper.toDTO(medicalRecord);
    }

    public void deleteMedicalRecord(UUID id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical Record", "id", id));
        medicalRecord.setDeleted(true);
        medicalRecordRepository.save(medicalRecord);
    }
}
