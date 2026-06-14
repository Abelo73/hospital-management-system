package com.act.hospitalmanagementsystem.hr.mapper;

import com.act.hospitalmanagementsystem.hr.dto.TrainingDTO;
import com.act.hospitalmanagementsystem.hr.entity.Training;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainingMapper {

    public TrainingDTO toDTO(Training training) {
        if (training == null) {
            return null;
        }

        TrainingDTO dto = new TrainingDTO();
        dto.setId(training.getId());
        dto.setTrainingName(training.getTrainingName());
        dto.setDescription(training.getDescription());
        dto.setTrainingType(training.getTrainingType());
        dto.setStartDate(training.getStartDate());
        dto.setEndDate(training.getEndDate());
        dto.setLocation(training.getLocation());
        dto.setInstructor(training.getInstructor());
        dto.setCost(training.getCost());
        dto.setMaxParticipants(training.getMaxParticipants());
        dto.setStatus(training.getStatus());
        dto.setNotes(training.getNotes());
        dto.setCreatedAt(training.getCreatedAt());
        dto.setCreatedBy(training.getCreatedBy());
        return dto;
    }

    public List<TrainingDTO> toDTOList(List<Training> trainings) {
        return trainings.stream().map(this::toDTO).toList();
    }
}
