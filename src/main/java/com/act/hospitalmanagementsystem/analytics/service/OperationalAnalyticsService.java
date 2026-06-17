package com.act.hospitalmanagementsystem.analytics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OperationalAnalyticsService {

    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> getAppointmentStats() {
        Long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM appointments WHERE deleted = false", Long.class);
        Long completed = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM appointments WHERE deleted = false AND status = 'COMPLETED'", Long.class);
        Long cancelled = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM appointments WHERE deleted = false AND status = 'CANCELLED'", Long.class);
        Long scheduled = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM appointments WHERE deleted = false AND status = 'SCHEDULED'", Long.class);
        return Map.of(
                "total", total != null ? total : 0,
                "completed", completed != null ? completed : 0,
                "cancelled", cancelled != null ? cancelled : 0,
                "scheduled", scheduled != null ? scheduled : 0,
                "completionRate", total != null && total > 0 ? (completed != null ? completed * 100.0 / total : 0) : 0
        );
    }

    public List<Map<String, Object>> getAppointmentsByType() {
        return jdbcTemplate.queryForList(
                "SELECT appointment_type, COUNT(*) as count FROM appointments WHERE deleted = false GROUP BY appointment_type ORDER BY count DESC");
    }

    public List<Map<String, Object>> getMonthlyAppointmentTrend() {
        return jdbcTemplate.queryForList(
                "SELECT TO_CHAR(appointment_date, 'YYYY-MM') as month, COUNT(*) as count FROM appointments WHERE deleted = false GROUP BY TO_CHAR(appointment_date, 'YYYY-MM') ORDER BY month DESC LIMIT 12");
    }

    public List<Map<String, Object>> getBusyDoctors(int limit) {
        return jdbcTemplate.queryForList(
                "SELECT a.doctor_id, u.first_name || ' ' || u.last_name as doctor_name, COUNT(*) as appointment_count FROM appointments a JOIN users u ON a.doctor_id = u.id WHERE a.deleted = false GROUP BY a.doctor_id, doctor_name ORDER BY appointment_count DESC LIMIT ?", limit);
    }
}
