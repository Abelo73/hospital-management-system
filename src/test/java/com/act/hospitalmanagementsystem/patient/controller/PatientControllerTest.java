package com.act.hospitalmanagementsystem.patient.controller;

import com.act.hospitalmanagementsystem.patient.dto.PatientDTO;
import com.act.hospitalmanagementsystem.patient.dto.CreatePatientRequest;
import com.act.hospitalmanagementsystem.patient.dto.UpdatePatientRequest;
import com.act.hospitalmanagementsystem.patient.service.PatientService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    private PatientDTO patientDTO;
    private CreatePatientRequest createRequest;
    private UpdatePatientRequest updateRequest;
    private UUID patientId;

    @BeforeEach
    void setUp() {
        patientId = UUID.randomUUID();

        patientDTO = new PatientDTO();
        patientDTO.setId(patientId);
        patientDTO.setMedicalRecordNumber("PAT001");
        patientDTO.setFirstName("John");
        patientDTO.setLastName("Doe");
        patientDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));

        createRequest = new CreatePatientRequest();
        createRequest.setFirstName("John");
        createRequest.setLastName("Doe");
        createRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));

        updateRequest = new UpdatePatientRequest();
        updateRequest.setFirstName("Jane");
        updateRequest.setLastName("Smith");
    }

    @Test
    @WithMockUser
    void testGetPatientById_Success() throws Exception {
        when(patientService.getPatientById(patientId)).thenReturn(patientDTO);

        mockMvc.perform(get("/patients/{id}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(patientId.toString()))
                .andExpect(jsonPath("$.data.firstName").value("John"));
    }

    @Test
    @WithMockUser
    void testGetPatientById_NotFound() throws Exception {
        when(patientService.getPatientById(patientId))
                .thenThrow(new RuntimeException("Patient not found"));

        mockMvc.perform(get("/patients/{id}", patientId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    void testCreatePatient_Success() throws Exception {
        when(patientService.createPatient(any(CreatePatientRequest.class))).thenReturn(patientDTO);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.firstName").value("John"));
    }

    @Test
    @WithMockUser
    void testUpdatePatient_Success() throws Exception {
        when(patientService.updatePatient(eq(patientId), any(UpdatePatientRequest.class)))
                .thenReturn(patientDTO);

        mockMvc.perform(put("/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser
    void testDeletePatient_Success() throws Exception {
        mockMvc.perform(delete("/patients/{id}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser
    void testSearchPatients_Success() throws Exception {
        mockMvc.perform(get("/patients/search")
                        .param("firstName", "John"))
                .andExpect(status().isOk());
    }
}
