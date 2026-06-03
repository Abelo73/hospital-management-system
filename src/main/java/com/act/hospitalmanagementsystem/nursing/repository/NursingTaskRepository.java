package com.act.hospitalmanagementsystem.nursing.repository;

import com.act.hospitalmanagementsystem.nursing.entity.NursingTask;
import com.act.hospitalmanagementsystem.nursing.enums.TaskCategory;
import com.act.hospitalmanagementsystem.nursing.enums.TaskPriority;
import com.act.hospitalmanagementsystem.nursing.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NursingTaskRepository extends JpaRepository<NursingTask, UUID> {

    Optional<NursingTask> findByIdAndDeletedFalse(UUID id);

    Page<NursingTask> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    Page<NursingTask> findByAssignedToAndDeletedFalse(UUID assignedTo, Pageable pageable);

    Page<NursingTask> findByStatusAndDeletedFalse(TaskStatus status, Pageable pageable);

    Page<NursingTask> findByPriorityAndDeletedFalse(TaskPriority priority, Pageable pageable);

    Page<NursingTask> findByTaskCategoryAndDeletedFalse(TaskCategory taskCategory, Pageable pageable);

    @Query("SELECT t FROM NursingTask t WHERE t.deleted = false AND t.scheduledDate = :date")
    List<NursingTask> findByScheduledDate(@Param("date") LocalDate date);

    @Query("SELECT t FROM NursingTask t WHERE t.deleted = false AND t.scheduledDate < :date AND t.status = 'PENDING'")
    Page<NursingTask> findOverdueTasks(@Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT t FROM NursingTask t WHERE t.deleted = false AND " +
           "(LOWER(t.taskTitle) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(t.taskDescription) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<NursingTask> searchTasks(@Param("searchTerm") String searchTerm, Pageable pageable);
}
