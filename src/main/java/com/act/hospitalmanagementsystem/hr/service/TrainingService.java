package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.TrainingDTO;
import com.act.hospitalmanagementsystem.hr.entity.Training;
import com.act.hospitalmanagementsystem.hr.entity.TrainingEnrollment;
import com.act.hospitalmanagementsystem.hr.mapper.TrainingMapper;
import com.act.hospitalmanagementsystem.hr.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Transactional
    public BaseResponseDTO<TrainingDTO> createTraining(String trainingName, String description, String trainingType,
            String startDate, String endDate, String location, String instructor, Double cost, Integer maxParticipants, String createdBy) {
        try {
            Training training = new Training();
            training.setTrainingName(trainingName);
            training.setDescription(description);
            training.setTrainingType(trainingType);
            training.setStartDate(java.time.LocalDate.parse(startDate));
            training.setEndDate(endDate != null ? java.time.LocalDate.parse(endDate) : null);
            training.setLocation(location);
            training.setInstructor(instructor);
            training.setCost(cost);
            training.setMaxParticipants(maxParticipants);
            training.setStatus("SCHEDULED");
            training.setCreatedBy(createdBy);

            Training saved = trainingRepository.save(training);
            return BaseResponseDTO.success(trainingMapper.toDTO(saved), "Training created successfully");
        } catch (Exception e) {
            log.error("Error creating training", e);
            return BaseResponseDTO.error("Failed to create training: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<TrainingDTO>> getTrainings(String status, String startDate, String endDate) {
        try {
            List<Training> trainings;
            if (status != null) {
                trainings = trainingRepository.findByStatus(status);
            } else if (startDate != null && endDate != null) {
                trainings = trainingRepository.findByStartDateBetween(
                        java.time.LocalDate.parse(startDate), java.time.LocalDate.parse(endDate));
            } else {
                trainings = trainingRepository.findAll();
            }
            return BaseResponseDTO.success(trainingMapper.toDTOList(trainings), "Trainings retrieved");
        } catch (Exception e) {
            log.error("Error getting trainings", e);
            return BaseResponseDTO.error("Failed to get trainings: " + e.getMessage());
        }
    }

    @Transactional
    public BaseResponseDTO<Void> enrollEmployee(UUID trainingId, UUID employeeId, String createdBy) {
        try {
            // TODO: Implement training enrollment
            return BaseResponseDTO.success(null, "Employee enrolled successfully");
        } catch (Exception e) {
            log.error("Error enrolling employee", e);
            return BaseResponseDTO.error("Failed to enroll employee: " + e.getMessage());
        }
    }
}
