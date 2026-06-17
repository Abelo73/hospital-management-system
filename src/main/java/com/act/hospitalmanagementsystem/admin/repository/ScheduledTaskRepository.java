package com.act.hospitalmanagementsystem.admin.repository;

import com.act.hospitalmanagementsystem.admin.entity.ScheduledTask;
import com.act.hospitalmanagementsystem.admin.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScheduledTaskRepository extends JpaRepository<ScheduledTask, UUID> {

    Optional<ScheduledTask> findByTaskNameAndDeletedFalse(String taskName);

    List<ScheduledTask> findByStatusAndDeletedFalse(TaskStatus status);

    List<ScheduledTask> findByEnabledTrueAndDeletedFalse();

    boolean existsByTaskNameAndDeletedFalse(String taskName);
}
