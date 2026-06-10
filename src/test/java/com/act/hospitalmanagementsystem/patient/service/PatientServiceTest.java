package com.act.hospitalmanagementsystem.patient.service;

import com.act.hospitalmanagementsystem.patient.dto.PatientDTO;
import com.act.hospitalmanagementsystem.patient.dto.CreatePatientRequest;
import com.act.hospitalmanagementsystem.patient.dto.UpdatePatientRequest;
import com.act.hospitalmanagementsystem.patient.entity.Patient;
import com.act.hospitalmanagementsystem.patient.enums.Gender;
import com.act.hospitalmanagementsystem.patient.enums.PatientStatus;
import com.act.hospitalmanagementsystem.patient.repository.PatientRepository;
import com.act.hospitalmanagementsystem.patient.mapper.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    private Patient testPatient;
    private PatientDTO patientDTO;
    private CreatePatientRequest createRequest;
    private UpdatePatientRequest updateRequest;
    private UUID patientId;

    @BeforeEach
    void setUp() {
        patientId = UUID.randomUUID();

        testPatient = new Patient();
        testPatient.setId(patientId);
        testPatient.setMedicalRecordNumber("PAT001");
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testPatient.setGender(Gender.MALE);
        testPatient.setPhoneNumber("1234567890");
        testPatient.setEmail("john.doe@example.com");
        testPatient.setAddress("123 Main St");
        testPatient.setCity("New York");
        testPatient.setState("NY");
        testPatient.setPostalCode("10001");
        testPatient.setCountry("USA");
        testPatient.setEmergencyContactName("Jane Doe");
        testPatient.setEmergencyContactPhone("0987654321");
        testPatient.setEmergencyContactRelationship("Spouse");
        testPatient.setRegistrationDate(LocalDateTime.now());
        testPatient.setStatus(PatientStatus.ACTIVE);

        patientDTO = new PatientDTO();
        patientDTO.setId(patientId);
        patientDTO.setMedicalRecordNumber("PAT001");
        patientDTO.setFirstName("John");
        patientDTO.setLastName("Doe");

        createRequest = new CreatePatientRequest();
        createRequest.setFirstName("John");
        createRequest.setLastName("Doe");
        createRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));
        createRequest.setGender(Gender.MALE);
        createRequest.setPhoneNumber("1234567890");
        createRequest.setEmail("john.doe@example.com");

        updateRequest = new UpdatePatientRequest();
        updateRequest.setFirstName("Jane");
        updateRequest.setLastName("Smith");
    }

    @Test
    void testGetPatientById_Success() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(testPatient));
        when(patientMapper.toDTO(testPatient)).thenReturn(patientDTO);

        PatientDTO result = patientService.getPatientById(patientId);

        assertNotNull(result);
        assertEquals("PAT001", result.getMedicalRecordNumber());
        assertEquals("John", result.getFirstName());
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientMapper, times(1)).toDTO(testPatient);
    }

    @Test
    void testGetPatientById_NotFound() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> patientService.getPatientById(patientId));
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientMapper, never()).toDTO(any());
    }

    @Test
    void testGetPatientByMedicalRecordNumber_Success() {
        when(patientRepository.findByMedicalRecordNumber("PAT001")).thenReturn(Optional.of(testPatient));
        when(patientMapper.toDTO(testPatient)).thenReturn(patientDTO);

        PatientDTO result = patientService.getPatientByMedicalRecordNumber("PAT001");

        assertNotNull(result);
        assertEquals("PAT001", result.getMedicalRecordNumber());
        verify(patientRepository, times(1)).findByMedicalRecordNumber("PAT001");
    }

    @Test
    void testCreatePatient_Success() {
        when(patientRepository.existsByMedicalRecordNumber(any())).thenReturn(false);
        when(patientRepository.existsByEmail(any())).thenReturn(false);
        when(patientMapper.toEntity(createRequest)).thenReturn(testPatient);
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);
        when(patientMapper.toDTO(testPatient)).thenReturn(patientDTO);

        PatientDTO result = patientService.createPatient(createRequest);

        assertNotNull(result);
        assertEquals("PAT001", result.getMedicalRecordNumber());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void testCreatePatient_MedicalRecordNumberExists() {
        when(patientRepository.existsByMedicalRecordNumber(any())).thenReturn(true);

        assertThrows(Exception.class, () -> patientService.createPatient(createRequest));
        verify(patientRepository, times(1)).existsByMedicalRecordNumber(any());
        verify(patientRepository, never()).save(any());
    }

    @Test
    void testUpdatePatient_Success() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(testPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);
        when(patientMapper.toDTO(testPatient)).thenReturn(patientDTO);

        PatientDTO result = patientService.updatePatient(patientId, updateRequest);

        assertNotNull(result);
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void testUpdatePatient_NotFound() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> patientService.updatePatient(patientId, updateRequest));
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, never()).save(any());
    }

    @Test
    void testDeletePatient_Success() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(testPatient));
        doNothing().when(patientRepository).delete(testPatient);

        patientService.deletePatient(patientId);

        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, times(1)).delete(testPatient);
    }

    @Test
    void testDeletePatient_NotFound() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> patientService.deletePatient(patientId));
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, never()).delete(any());
    }

    @Test
    void testSearchPatients_Success() {
        when(patientRepository.findByFirstNameContainingIgnoreCase("John"))
                .thenReturn(java.util.List.of(testPatient));
        when(patientMapper.toDTO(testPatient)).thenReturn(patientDTO);

        var results = patientService.searchPatients("John", null);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        verify(patientRepository, times(1)).findByFirstNameContainingIgnoreCase("John");
    }
}
