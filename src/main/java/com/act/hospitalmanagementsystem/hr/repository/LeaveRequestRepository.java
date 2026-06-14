package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.LeaveRequest;
import com.act.hospitalmanagementsystem.hr.enums.LeaveStatus;
import com.act.hospitalmanagementsystem.hr.enums.LeaveType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, UUID> {

    List<LeaveRequest> findByEmployeeId(UUID employeeId);

    Page<LeaveRequest> findByStatus(LeaveStatus status, Pageable pageable);

    Page<LeaveRequest> findByLeaveType(LeaveType leaveType, Pageable pageable);

    List<LeaveRequest> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
}
