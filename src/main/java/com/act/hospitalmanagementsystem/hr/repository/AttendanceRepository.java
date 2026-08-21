package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.Attendance;
import com.act.hospitalmanagementsystem.hr.enums.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    Page<Attendance> findByEmployeeId(UUID employeeId, Pageable pageable);

    List<Attendance> findByEmployeeId(UUID employeeId);

    Page<Attendance> findByEmployeeIdAndDateBetween(UUID employeeId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    List<Attendance> findByEmployeeIdAndDateBetween(UUID employeeId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT a FROM Attendance a WHERE a.deleted = false AND a.date = :date")
    Page<Attendance> findByDate(@Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT a FROM Attendance a WHERE a.deleted = false AND a.date = :date")
    List<Attendance> findByDate(@Param("date") LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.deleted = false AND a.status = :status")
    Page<Attendance> findByStatus(@Param("status") AttendanceStatus status, Pageable pageable);
}
