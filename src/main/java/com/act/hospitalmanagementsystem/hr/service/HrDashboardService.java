package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.HrDashboardDTO;
import com.act.hospitalmanagementsystem.hr.entity.Attendance;
import com.act.hospitalmanagementsystem.hr.entity.Employee;
import com.act.hospitalmanagementsystem.hr.enums.AttendanceStatus;
import com.act.hospitalmanagementsystem.hr.enums.EmployeeStatus;
import com.act.hospitalmanagementsystem.hr.enums.LeaveStatus;
import com.act.hospitalmanagementsystem.hr.repository.AttendanceRepository;
import com.act.hospitalmanagementsystem.hr.repository.EmployeeRepository;
import com.act.hospitalmanagementsystem.hr.repository.LeaveRequestRepository;
import com.act.hospitalmanagementsystem.hr.repository.PayrollRepository;
import com.act.hospitalmanagementsystem.hr.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HrDashboardService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final AttendanceRepository attendanceRepository;
    private final PayrollRepository payrollRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Transactional(readOnly = true)
    public BaseResponseDTO<HrDashboardDTO> getDashboard() {
        try {
            LocalDate today = LocalDate.now();

            // ── Employee counts ─────────────────────────────
            long total = employeeRepository.countByDeletedFalse();
            long active = employeeRepository.countByStatusAndDeletedFalse(EmployeeStatus.ACTIVE);
            long inactive = employeeRepository.countByStatusAndDeletedFalse(EmployeeStatus.INACTIVE)
                    + employeeRepository.countByStatusAndDeletedFalse(EmployeeStatus.TERMINATED);

            // New hires this month
            long newHires = employeeRepository.findNewHiresThisMonth().size();

            // ── Leave ───────────────────────────────────────
            long pendingLeaves = leaveRequestRepository.countByStatus(LeaveStatus.PENDING);
            long onLeaveToday = leaveRequestRepository.findCurrentlyOnLeave().size();

            // ── Attendance today ────────────────────────────
            List<Attendance> todayAttendance = attendanceRepository.findByDate(today);
            long presentToday = todayAttendance.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
            long absentToday = todayAttendance.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
            long lateToday = todayAttendance.stream().filter(a -> a.getStatus() == AttendanceStatus.LATE).count();

            // ── Payroll this month ──────────────────────────
            LocalDate monthStart = today.withDayOfMonth(1);
            LocalDate monthEnd = today.withDayOfMonth(today.lengthOfMonth());
            double totalPayroll = payrollRepository.findByPayPeriodStartBetween(monthStart, monthEnd)
                    .stream().mapToDouble(p -> p.getNetPay() != null ? p.getNetPay() : 0).sum();

            // ── Open vacancies ──────────────────────────────
            long openVacancies = recruitmentRepository.findByClosingDateAfter(today).stream()
                    .filter(r -> "OPEN".equals(r.getStatus())).count();

            // ── Upcoming birthdays (next 7 days) ────────────
            List<HrDashboardDTO.BirthdayDTO> birthdays = employeeRepository.findUpcomingBirthdays()
                    .stream()
                    .map(e -> HrDashboardDTO.BirthdayDTO.builder()
                            .employeeId(e.getId() != null ? e.getId().toString() : "")
                            .name(e.getFirstName() + " " + e.getLastName())
                            .dateOfBirth(e.getDateOfBirth() != null ? e.getDateOfBirth().toString() : "")
                            .department(e.getDepartment() != null ? e.getDepartment() : "")
                            .build())
                    .collect(Collectors.toList());

            // ── Department headcount ─────────────────────────
            List<Employee> allActive = employeeRepository.findByStatus(EmployeeStatus.ACTIVE, org.springframework.data.domain.Pageable.unpaged()).getContent();
            Map<String, Long> byDept = allActive.stream()
                    .filter(e -> e.getDepartment() != null)
                    .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
            List<Map<String, Object>> deptHeadcount = byDept.entrySet().stream()
                    .map(entry -> {
                        Map<String, Object> m = new LinkedHashMap<>();
                        m.put("name", entry.getKey());
                        m.put("count", entry.getValue());
                        return m;
                    })
                    .sorted((a, b) -> Long.compare((Long) b.get("count"), (Long) a.get("count")))
                    .collect(Collectors.toList());

            // ── Employee type distribution ───────────────────
            Map<String, Long> byType = allActive.stream()
                    .filter(e -> e.getEmployeeType() != null)
                    .collect(Collectors.groupingBy(e -> e.getEmployeeType().name(), Collectors.counting()));
            List<Map<String, Object>> typeDistribution = byType.entrySet().stream()
                    .map(entry -> {
                        Map<String, Object> m = new LinkedHashMap<>();
                        m.put("name", entry.getKey());
                        m.put("value", entry.getValue());
                        return m;
                    }).collect(Collectors.toList());

            // ── Monthly leaves trend (last 6 months) ────────
            List<Map<String, Object>> leavesTrend = new ArrayList<>();
            for (int i = 5; i >= 0; i--) {
                LocalDate monthDate = today.minusMonths(i);
                LocalDate start = monthDate.withDayOfMonth(1);
                LocalDate end = monthDate.withDayOfMonth(monthDate.lengthOfMonth());
                long count = leaveRequestRepository.findByStartDateBetween(start, end).size();
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("month", monthDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
                m.put("leaves", count);
                leavesTrend.add(m);
            }

            HrDashboardDTO dto = HrDashboardDTO.builder()
                    .totalEmployees(total)
                    .activeEmployees(active)
                    .inactiveEmployees(inactive)
                    .onLeaveToday(onLeaveToday)
                    .newHiresThisMonth(newHires)
                    .pendingLeaveRequests(pendingLeaves)
                    .openVacancies(openVacancies)
                    .totalPayrollThisMonth(totalPayroll)
                    .presentToday(presentToday)
                    .absentToday(absentToday)
                    .lateToday(lateToday)
                    .upcomingBirthdays(birthdays)
                    .departmentHeadcount(deptHeadcount)
                    .employeeTypeDistribution(typeDistribution)
                    .monthlyLeavesTrend(leavesTrend)
                    .build();

            return BaseResponseDTO.success("HR dashboard data retrieved", dto);
        } catch (Exception e) {
            log.error("Error building HR dashboard", e);
            return BaseResponseDTO.error("Failed to load HR dashboard: " + e.getMessage());
        }
    }
}
