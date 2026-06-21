package com.act.hospitalmanagementsystem.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HrDashboardDTO {

    // KPI counts
    private long totalEmployees;
    private long activeEmployees;
    private long inactiveEmployees;
    private long onLeaveToday;
    private long newHiresThisMonth;
    private long pendingLeaveRequests;
    private long openVacancies;
    private double totalPayrollThisMonth;

    // Today's attendance summary
    private long presentToday;
    private long absentToday;
    private long lateToday;

    // Upcoming events
    private List<BirthdayDTO> upcomingBirthdays;
    private List<Map<String, Object>> departmentHeadcount;
    private List<Map<String, Object>> employeeTypeDistribution;
    private List<Map<String, Object>> monthlyLeavesTrend;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BirthdayDTO {
        private String employeeId;
        private String name;
        private String dateOfBirth;
        private String department;
    }
}
