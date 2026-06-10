package com.act.hospitalmanagementsystem.doctor.service;

import com.act.hospitalmanagementsystem.doctor.dto.ConsultationDTO;
import com.act.hospitalmanagementsystem.doctor.dto.CreateConsultationRequest;
import com.act.hospitalmanagementsystem.doctor.dto.UpdateConsultationRequest;
import com.act.hospitalmanagementsystem.doctor.entity.Consultation;
import com.act.hospitalmanagementsystem.doctor.repository.ConsultationRepository;
import com.act.hospitalmanagementsystem.doctor.mapper.ConsultationMapper;
import com.act.hospitalmanagementsystem.patient.entity.Patient;
import com.act.hospitalmanagementsystem.auth.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultationServiceTest {

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private ConsultationMapper consultationMapper;

    @InjectMocks
    private ConsultationService consultationService;

    private Consultation testConsultation;
    private ConsultationDTO consultationDTO;
    private CreateConsultationRequest createRequest;
    private UpdateConsultationRequest updateRequest;
    private UUID consultationId;
    private UUID patientId;
    private UUID doctorId;

    @BeforeEach
    void setUp() {
        consultationId = UUID.randomUUID();
        patientId = UUID.randomUUID();
        doctorId = UUID.randomUUID();

        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setFirstName("John");
        patient.setLastName("Doe");

        User doctor = new User();
        doctor.setId(doctorId);
        doctor.setUsername("drsmith");
        doctor.setFirstName("Dr.");
        doctor.setLastName("Smith");

        testConsultation = new Consultation();
        testConsultation.setId(consultationId);
        testConsultation.setPatient(patient);
        testConsultation.setDoctor(doctor);
        testConsultation.setConsultationDate(LocalDateTime.now());
        testConsultation.setChiefComplaint("Headache");
        testConsultation.setIsFinalized(false);

        consultationDTO = new ConsultationDTO();
        consultationDTO.setId(consultationId);
        consultationDTO.setPatientId(patientId);
        consultationDTO.setDoctorId(doctorId);
        consultationDTO.setChiefComplaint("Headache");

        createRequest = new CreateConsultationRequest();
        createRequest.setPatientId(patientId);
        createRequest.setDoctorId(doctorId);
        createRequest.setChiefComplaint("Headache");
        createRequest.setConsultationDate(LocalDateTime.now());

        updateRequest = new UpdateConsultationRequest();
        updateRequest.setChiefComplaint("Severe headache");
        updateRequest.setIsFinalized(true);
    }

    @Test
    void testGetConsultationById_Success() {
        when(consultationRepository.findById(consultationId)).thenReturn(Optional.of(testConsultation));
        when(consultationMapper.toDTO(testConsultation)).thenReturn(consultationDTO);

        ConsultationDTO result = consultationService.getConsultationById(consultationId);

        assertNotNull(result);
        assertEquals(consultationId, result.getId());
        assertEquals("Headache", result.getChiefComplaint());
        verify(consultationRepository, times(1)).findById(consultationId);
        verify(consultationMapper, times(1)).toDTO(testConsultation);
    }

    @Test
    void testGetConsultationById_NotFound() {
        when(consultationRepository.findById(consultationId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> consultationService.getConsultationById(consultationId));
        verify(consultationRepository, times(1)).findById(consultationId);
        verify(consultationMapper, never()).toDTO(any());
    }

    @Test
    void testCreateConsultation_Success() {
        when(consultationMapper.toEntity(createRequest)).thenReturn(testConsultation);
        when(consultationRepository.save(any(Consultation.class))).thenReturn(testConsultation);
        when(consultationMapper.toDTO(testConsultation)).thenReturn(consultationDTO);

        ConsultationDTO result = consultationService.createConsultation(createRequest);

        assertNotNull(result);
        assertEquals(consultationId, result.getId());
        verify(consultationRepository, times(1)).save(any(Consultation.class));
    }

    @Test
    void testUpdateConsultation_Success() {
        when(consultationRepository.findById(consultationId)).thenReturn(Optional.of(testConsultation));
        when(consultationRepository.save(any(Consultation.class))).thenReturn(testConsultation);
        when(consultationMapper.toDTO(testConsultation)).thenReturn(consultationDTO);

        ConsultationDTO result = consultationService.updateConsultation(consultationId, updateRequest);

        assertNotNull(result);
        verify(consultationRepository, times(1)).findById(consultationId);
        verify(consultationRepository, times(1)).save(any(Consultation.class));
    }

    @Test
    void testUpdateConsultation_NotFound() {
        when(consultationRepository.findById(consultationId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> consultationService.updateConsultation(consultationId, updateRequest));
        verify(consultationRepository, times(1)).findById(consultationId);
        verify(consultationRepository, never()).save(any());
    }

    @Test
    void testFinalizeConsultation_Success() {
        when(consultationRepository.findById(consultationId)).thenReturn(Optional.of(testConsultation));
        when(consultationRepository.save(any(Consultation.class))).thenReturn(testConsultation);

        consultationService.finalizeConsultation(consultationId);

        verify(consultationRepository, times(1)).findById(consultationId);
        verify(consultationRepository, times(1)).save(any(Consultation.class));
    }

    @Test
    void testGetConsultationsByPatientId_Success() {
        when(consultationRepository.findByPatientId(patientId))
                .thenReturn(java.util.List.of(testConsultation));
        when(consultationMapper.toDTO(testConsultation)).thenReturn(consultationDTO);

        var results = consultationService.getConsultationsByPatientId(patientId);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        verify(consultationRepository, times(1)).findByPatientId(patientId);
    }

    @Test
    void testGetConsultationsByDoctorId_Success() {
        when(consultationRepository.findByDoctorId(doctorId))
                .thenReturn(java.util.List.of(testConsultation));
        when(consultationMapper.toDTO(testConsultation)).thenReturn(consultationDTO);

        var results = consultationService.getConsultationsByDoctorId(doctorId);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        verify(consultationRepository, times(1)).findByDoctorId(doctorId);
    }

    @Test
    void testDeleteConsultation_Success() {
        when(consultationRepository.findById(consultationId)).thenReturn(Optional.of(testConsultation));
        doNothing().when(consultationRepository).delete(testConsultation);

        consultationService.deleteConsultation(consultationId);

        verify(consultationRepository, times(1)).findById(consultationId);
        verify(consultationRepository, times(1)).delete(testConsultation);
    }
}
