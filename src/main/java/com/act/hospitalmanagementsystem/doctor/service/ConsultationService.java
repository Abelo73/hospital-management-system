package com.act.hospitalmanagementsystem.doctor.service;

import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.auth.repository.UserRepository;
import com.act.hospitalmanagementsystem.doctor.dto.ConsultationDTO;
import com.act.hospitalmanagementsystem.doctor.dto.CreateConsultationRequest;
import com.act.hospitalmanagementsystem.doctor.dto.DiagnosisDTO;
import com.act.hospitalmanagementsystem.doctor.dto.PrescriptionDTO;
import com.act.hospitalmanagementsystem.doctor.entity.Consultation;
import com.act.hospitalmanagementsystem.doctor.entity.Diagnosis;
import com.act.hospitalmanagementsystem.doctor.entity.Prescription;
import com.act.hospitalmanagementsystem.doctor.mapper.ConsultationMapper;
import com.act.hospitalmanagementsystem.doctor.repository.ConsultationRepository;
import com.act.hospitalmanagementsystem.patient.entity.Patient;
import com.act.hospitalmanagementsystem.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final ConsultationMapper consultationMapper;

    @Transactional
    public ConsultationDTO createConsultation(CreateConsultationRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setDoctor(doctor);
        consultation.setConsultationDate(request.getConsultationDate() != null ? request.getConsultationDate() : LocalDateTime.now());
        consultation.setChiefComplaint(request.getChiefComplaint());
        consultation.setHistoryOfPresentIllness(request.getHistoryOfPresentIllness());
        consultation.setPhysicalExamination(request.getPhysicalExamination());
        consultation.setPlan(request.getPlan());
        consultation.setNotes(request.getNotes());

        if (request.getDiagnoses() != null) {
            for (DiagnosisDTO dReq : request.getDiagnoses()) {
                Diagnosis diagnosis = new Diagnosis();
                diagnosis.setConsultation(consultation);
                diagnosis.setIcd10Code(dReq.getIcd10Code());
                diagnosis.setDescription(dReq.getDescription());
                diagnosis.setIsPrimary(dReq.getIsPrimary());
                diagnosis.setNotes(dReq.getNotes());
                consultation.getDiagnoses().add(diagnosis);
            }
        }

        if (request.getPrescriptions() != null) {
            for (PrescriptionDTO pReq : request.getPrescriptions()) {
                Prescription prescription = new Prescription();
                prescription.setConsultation(consultation);
                prescription.setMedicationName(pReq.getMedicationName());
                prescription.setDosage(pReq.getDosage());
                prescription.setFrequency(pReq.getFrequency());
                prescription.setDuration(pReq.getDuration());
                prescription.setRoute(pReq.getRoute());
                prescription.setInstructions(pReq.getInstructions());
                prescription.setNotes(pReq.getNotes());
                consultation.getPrescriptions().add(prescription);
            }
        }

        Consultation savedConsultation = consultationRepository.save(consultation);
        return consultationMapper.toDTO(savedConsultation);
    }

    public ConsultationDTO getConsultation(UUID id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultation not found"));
        return consultationMapper.toDTO(consultation);
    }

    public List<ConsultationDTO> getPatientConsultations(UUID patientId) {
        List<Consultation> consultations = consultationRepository.findByPatientId(patientId);
        return consultationMapper.toDTOList(consultations);
    }

    @Transactional
    public void finalizeConsultation(UUID id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultation not found"));
        consultation.setIsFinalized(true);
        consultation.setFinalizedAt(LocalDateTime.now());
        consultationRepository.save(consultation);
    }
}
