package com.act.hospitalmanagementsystem.nursing.service;

import com.act.hospitalmanagementsystem.common.exception.ResourceNotFoundException;
import com.act.hospitalmanagementsystem.nursing.dto.CreateNursingTaskRequest;
import com.act.hospitalmanagementsystem.nursing.dto.NursingTaskDTO;
import com.act.hospitalmanagementsystem.nursing.dto.UpdateNursingTaskRequest;
import com.act.hospitalmanagementsystem.nursing.entity.NursingTask;
import com.act.hospitalmanagementsystem.nursing.enums.TaskCategory;
import com.act.hospitalmanagementsystem.nursing.enums.TaskPriority;
import com.act.hospitalmanagementsystem.nursing.enums.TaskStatus;
import com.act.hospitalmanagementsystem.nursing.mapper.NursingTaskMapper;
import com.act.hospitalmanagementsystem.nursing.repository.NursingTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NursingTaskService {

    private final NursingTaskRepository nursingTaskRepository;
    private final NursingTaskMapper nursingTaskMapper;

    public NursingTaskDTO createNursingTask(CreateNursingTaskRequest request) {
        NursingTask task = nursingTaskMapper.toEntity(request);
        task = nursingTaskRepository.save(task);
        return nursingTaskMapper.toDTO(task);
    }

    public NursingTaskDTO getNursingTaskById(UUID id) {
        NursingTask task = nursingTaskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Task", "id", id));
        return nursingTaskMapper.toDTO(task);
    }

    public Page<NursingTaskDTO> getNursingTasksByPatientId(UUID patientId, Pageable pageable) {
        Page<NursingTask> tasks = nursingTaskRepository.findByPatientIdAndDeletedFalse(patientId, pageable);
        return tasks.map(nursingTaskMapper::toDTO);
    }

    public Page<NursingTaskDTO> getNursingTasksByAssignedTo(UUID assignedTo, Pageable pageable) {
        Page<NursingTask> tasks = nursingTaskRepository.findByAssignedToAndDeletedFalse(assignedTo, pageable);
        return tasks.map(nursingTaskMapper::toDTO);
    }

    public Page<NursingTaskDTO> getNursingTasksByStatus(TaskStatus status, Pageable pageable) {
        Page<NursingTask> tasks = nursingTaskRepository.findByStatusAndDeletedFalse(status, pageable);
        return tasks.map(nursingTaskMapper::toDTO);
    }

    public Page<NursingTaskDTO> getNursingTasksByPriority(TaskPriority priority, Pageable pageable) {
        Page<NursingTask> tasks = nursingTaskRepository.findByPriorityAndDeletedFalse(priority, pageable);
        return tasks.map(nursingTaskMapper::toDTO);
    }

    public Page<NursingTaskDTO> getNursingTasksByCategory(TaskCategory taskCategory, Pageable pageable) {
        Page<NursingTask> tasks = nursingTaskRepository.findByTaskCategoryAndDeletedFalse(taskCategory, pageable);
        return tasks.map(nursingTaskMapper::toDTO);
    }

    public List<NursingTaskDTO> getNursingTasksByScheduledDate(LocalDate date) {
        List<NursingTask> tasks = nursingTaskRepository.findByScheduledDate(date);
        return nursingTaskMapper.toDTOList(tasks);
    }

    public Page<NursingTaskDTO> getOverdueTasks(LocalDate date, Pageable pageable) {
        Page<NursingTask> tasks = nursingTaskRepository.findOverdueTasks(date, pageable);
        return tasks.map(nursingTaskMapper::toDTO);
    }

    public Page<NursingTaskDTO> searchNursingTasks(String searchTerm, Pageable pageable) {
        Page<NursingTask> tasks = nursingTaskRepository.searchTasks(searchTerm, pageable);
        return tasks.map(nursingTaskMapper::toDTO);
    }

    public NursingTaskDTO updateNursingTask(UUID id, UpdateNursingTaskRequest request) {
        NursingTask task = nursingTaskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Task", "id", id));
        nursingTaskMapper.updateEntityFromDTO(request, task);
        task = nursingTaskRepository.save(task);
        return nursingTaskMapper.toDTO(task);
    }

    public void deleteNursingTask(UUID id) {
        NursingTask task = nursingTaskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nursing Task", "id", id));
        task.setDeleted(true);
        nursingTaskRepository.save(task);
    }
}
