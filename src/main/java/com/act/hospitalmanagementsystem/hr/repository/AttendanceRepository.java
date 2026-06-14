package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.Attendance;
import com.act.hospitalmanagementsystem.hr.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    List<Attendance> findByEmployeeId(UUID employeeId);

    List<Attendance> findByEmployeeIdAndDateBetween(UUID employeeId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT a FROM Attendance a WHERE a.deleted = false AND a.date = :date")
    List<Attendance> findByDate(@Param("date") LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.deleted = false AND a.status = :status")
    List<Attendance> findByStatus(@Param("status") AttendanceStatus status);
}
