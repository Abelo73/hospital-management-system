package com.act.hospitalmanagementsystem.notification.repository;

import com.act.hospitalmanagementsystem.notification.entity.NotificationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<NotificationSchedule, UUID> {

    @Query("SELECT s FROM NotificationSchedule s WHERE s.deleted = false AND s.isActive = true AND s.nextRunAt <= :now")
    List<NotificationSchedule> findActiveSchedulesDueForExecution(@Param("now") LocalDateTime now);

    List<NotificationSchedule> findByIsActiveTrue();

    List<NotificationSchedule> findByIsActiveFalse();
}
