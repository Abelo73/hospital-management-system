package com.act.hospitalmanagementsystem.patient.service;

import com.act.hospitalmanagementsystem.common.exception.BadRequestException;
import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.patient.dto.CreatePatientRequest;
import com.act.hospitalmanagementsystem.patient.dto.PatientDTO;
import com.act.hospitalmanagementsystem.patient.dto.UpdatePatientRequest;
import com.act.hospitalmanagementsystem.patient.entity.Patient;
import com.act.hospitalmanagementsystem.patient.enums.PatientStatus;
import com.act.hospitalmanagementsystem.patient.mapper.PatientMapper;
import com.act.hospitalmanagementsystem.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public Page<PatientDTO> getAllPatients(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Patient> patients = patientRepository.findByDeletedFalse(pageable);
        return patients.map(patientMapper::toDTO);
    }

    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findByDeletedFalse();
        return patientMapper.toDTOList(patients);
    }

    public PatientDTO getPatientById(UUID id) {
        Patient patient = patientRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));
        return patientMapper.toDTO(patient);
    }

    public PatientDTO getPatientByMedicalRecordNumber(String medicalRecordNumber) {
        Patient patient = patientRepository.findByMedicalRecordNumberAndDeletedFalse(medicalRecordNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "medicalRecordNumber", medicalRecordNumber));
        return patientMapper.toDTO(patient);
    }

    public Page<PatientDTO> searchPatients(String searchTerm, int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Patient> patients = patientRepository.searchPatients(searchTerm, pageable);
        return patients.map(patientMapper::toDTO);
    }

    public List<PatientDTO> searchPatients(String searchTerm) {
        List<Patient> patients = patientRepository.searchPatients(searchTerm);
        return patientMapper.toDTOList(patients);
    }

    public List<PatientDTO> getPatientsByStatus(PatientStatus status) {
        List<Patient> patients = patientRepository.findByStatusAndDeletedFalse(status);
        return patientMapper.toDTOList(patients);
    }

    @Transactional
    public PatientDTO createPatient(CreatePatientRequest request) {
        // Generate unique medical record number
        String medicalRecordNumber = generateMedicalRecordNumber();

        Patient patient = new Patient();
        patient.setMedicalRecordNumber(medicalRecordNumber);
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setBloodType(request.getBloodType());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setCity(request.getCity());
        patient.setState(request.getState());
        patient.setPostalCode(request.getPostalCode());
        patient.setCountry(request.getCountry());
        patient.setEmergencyContactName(request.getEmergencyContactName());
        patient.setEmergencyContactPhone(request.getEmergencyContactPhone());
        patient.setEmergencyContactRelationship(request.getEmergencyContactRelationship());
        patient.setAllergies(request.getAllergies());
        patient.setChronicConditions(request.getChronicConditions());
        patient.setCurrentMedications(request.getCurrentMedications());
        patient.setInsuranceProvider(request.getInsuranceProvider());
        patient.setInsurancePolicyNumber(request.getInsurancePolicyNumber());
        patient.setRegistrationDate(LocalDateTime.now());
        patient.setStatus(PatientStatus.ACTIVE);
        patient.setNotes(request.getNotes());
        patient.setDeleted(false);

        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDTO(savedPatient);
    }

    @Transactional
    public PatientDTO updatePatient(UUID id, UpdatePatientRequest request) {
        Patient patient = patientRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));

        if (request.getFirstName() != null) {
            patient.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            patient.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null) {
            patient.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null) {
            patient.setGender(request.getGender());
        }
        if (request.getBloodType() != null) {
            patient.setBloodType(request.getBloodType());
        }
        if (request.getPhoneNumber() != null) {
            patient.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getEmail() != null) {
            patient.setEmail(request.getEmail());
        }
        if (request.getAddress() != null) {
            patient.setAddress(request.getAddress());
        }
        if (request.getCity() != null) {
            patient.setCity(request.getCity());
        }
        if (request.getState() != null) {
            patient.setState(request.getState());
        }
        if (request.getPostalCode() != null) {
            patient.setPostalCode(request.getPostalCode());
        }
        if (request.getCountry() != null) {
            patient.setCountry(request.getCountry());
        }
        if (request.getEmergencyContactName() != null) {
            patient.setEmergencyContactName(request.getEmergencyContactName());
        }
        if (request.getEmergencyContactPhone() != null) {
            patient.setEmergencyContactPhone(request.getEmergencyContactPhone());
        }
        if (request.getEmergencyContactRelationship() != null) {
            patient.setEmergencyContactRelationship(request.getEmergencyContactRelationship());
        }
        if (request.getAllergies() != null) {
            patient.setAllergies(request.getAllergies());
        }
        if (request.getChronicConditions() != null) {
            patient.setChronicConditions(request.getChronicConditions());
        }
        if (request.getCurrentMedications() != null) {
            patient.setCurrentMedications(request.getCurrentMedications());
        }
        if (request.getInsuranceProvider() != null) {
            patient.setInsuranceProvider(request.getInsuranceProvider());
        }
        if (request.getInsurancePolicyNumber() != null) {
            patient.setInsurancePolicyNumber(request.getInsurancePolicyNumber());
        }
        if (request.getStatus() != null) {
            patient.setStatus(request.getStatus());
        }
        if (request.getNotes() != null) {
            patient.setNotes(request.getNotes());
        }

        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.toDTO(updatedPatient);
    }

    @Transactional
    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));
        patient.setDeleted(true);
        patientRepository.save(patient);
    }

    private String generateMedicalRecordNumber() {
        // Generate a unique medical record number in format: MRN-YYYY-XXXXX
        String year = String.valueOf(LocalDateTime.now().getYear());
        long count = patientRepository.count() + 1;
        return String.format("MRN-%s-%05d", year, count);
    }
}
