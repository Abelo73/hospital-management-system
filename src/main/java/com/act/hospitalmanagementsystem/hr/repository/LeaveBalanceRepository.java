package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, UUID> {

    List<LeaveBalance> findByEmployeeId(UUID employeeId);

    List<LeaveBalance> findByEmployeeIdAndLeaveCycleYear(UUID employeeId, Integer year);

    Optional<LeaveBalance> findByEmployeeIdAndLeaveTypeIdAndLeaveCycleYear(
            UUID employeeId, UUID leaveTypeId, Integer year);

    @Query("SELECT lb FROM LeaveBalance lb WHERE lb.leaveCycleYear = :year")
    List<LeaveBalance> findByLeaveCycleYear(@Param("year") Integer year);
}
