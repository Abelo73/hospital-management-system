package com.act.hospitalmanagementsystem.laboratory.service;

import com.act.hospitalmanagementsystem.auth.entity.User;
import com.act.hospitalmanagementsystem.auth.repository.UserRepository;
import com.act.hospitalmanagementsystem.laboratory.dto.CreateLabTestRequest;
import com.act.hospitalmanagementsystem.laboratory.dto.LabTestDTO;
import com.act.hospitalmanagementsystem.laboratory.dto.LabTestRequestDTO;
import com.act.hospitalmanagementsystem.laboratory.entity.LabTest;
import com.act.hospitalmanagementsystem.laboratory.entity.LabTestRequest;
import com.act.hospitalmanagementsystem.laboratory.entity.LabTestRequestItem;
import com.act.hospitalmanagementsystem.laboratory.enums.RequestStatus;
import com.act.hospitalmanagementsystem.laboratory.mapper.LaboratoryMapper;
import com.act.hospitalmanagementsystem.laboratory.repository.LabTestRepository;
import com.act.hospitalmanagementsystem.laboratory.repository.LabTestRequestItemRepository;
import com.act.hospitalmanagementsystem.laboratory.repository.LabTestRequestRepository;
import com.act.hospitalmanagementsystem.patient.entity.Patient;
import com.act.hospitalmanagementsystem.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LaboratoryService {

    private final LabTestRepository labTestRepository;
    private final LabTestRequestRepository requestRepository;
    private final LabTestRequestItemRepository requestItemRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final LaboratoryMapper laboratoryMapper;

    public List<LabTestDTO> getAllTests() {
        return laboratoryMapper.toDTOList(labTestRepository.findByIsActiveTrue());
    }

    @Transactional
    public LabTestRequestDTO createRequest(CreateLabTestRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User provider = userRepository.findById(request.getOrderingProviderId())
                .orElseThrow(() -> new RuntimeException("Ordering provider not found"));

        LabTestRequest labRequest = new LabTestRequest();
        labRequest.setRequestNumber("LR-" + System.currentTimeMillis());
        labRequest.setPatient(patient);
        labRequest.setOrderingProvider(provider);
        labRequest.setRequestDate(LocalDate.now());
        labRequest.setRequestTime(LocalTime.now());
        labRequest.setPriority(request.getPriority());
        labRequest.setStatus(RequestStatus.PENDING);
        labRequest.setClinicalInformation(request.getClinicalInformation());

        LabTestRequest savedRequest = requestRepository.save(labRequest);

        if (request.getTestIds() != null) {
            for (UUID testId : request.getTestIds()) {
                LabTest test = labTestRepository.findById(testId)
                        .orElseThrow(() -> new RuntimeException("Test not found: " + testId));
                
                LabTestRequestItem item = new LabTestRequestItem();
                item.setRequest(savedRequest);
                item.setTest(test);
                item.setStatus(RequestStatus.PENDING);
                item.setUnit(test.getUnit());
                item.setReferenceRange(test.getReferenceRange());
                requestItemRepository.save(item);
                savedRequest.getItems().add(item);
            }
        }

        return laboratoryMapper.toDTO(savedRequest);
    }

    public LabTestRequestDTO getRequest(UUID id) {
        LabTestRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lab request not found"));
        return laboratoryMapper.toDTO(request);
    }

    public List<LabTestRequestDTO> getPatientRequests(UUID patientId) {
        List<LabTestRequest> requests = requestRepository.findByPatientId(patientId);
        return laboratoryMapper.toRequestDTOList(requests);
    }

    @Transactional
    public void updateItemResult(UUID itemId, String resultValue, com.act.hospitalmanagementsystem.laboratory.enums.ResultFlag flag, User performer) {
        LabTestRequestItem item = requestItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Request item not found"));
        
        item.setResultValue(resultValue);
        item.setResultFlag(flag);
        item.setStatus(RequestStatus.COMPLETED);
        item.setPerformedBy(performer);
        item.setPerformedDate(LocalDate.now());
        item.setPerformedTime(LocalTime.now());
        
        requestItemRepository.save(item);

        // Check if all items in request are completed
        LabTestRequest request = item.getRequest();
        boolean allCompleted = request.getItems().stream()
                .allMatch(i -> i.getStatus() == RequestStatus.COMPLETED);
        
        if (allCompleted) {
            request.setStatus(RequestStatus.COMPLETED);
            requestRepository.save(request);
        }
    }
}
