package com.act.hospitalmanagementsystem.analytics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PatientAnalyticsService {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getDemographicsByGender() {
        return jdbcTemplate.queryForList(
                "SELECT gender, COUNT(*) as count FROM patients WHERE deleted = false GROUP BY gender ORDER BY count DESC");
    }

    public List<Map<String, Object>> getDemographicsByStatus() {
        return jdbcTemplate.queryForList(
                "SELECT status, COUNT(*) as count FROM patients WHERE deleted = false GROUP BY status ORDER BY count DESC");
    }

    public Map<String, Object> getPatientGrowth() {
        List<Map<String, Object>> monthly = jdbcTemplate.queryForList(
                "SELECT TO_CHAR(created_at, 'YYYY-MM') as month, COUNT(*) as count FROM patients WHERE deleted = false GROUP BY TO_CHAR(created_at, 'YYYY-MM') ORDER BY month DESC LIMIT 12");
        return Map.of("monthlyGrowth", monthly);
    }

    public Map<String, Object> getRegistrationStats() {
        Long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM patients WHERE deleted = false", Long.class);
        Long active = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM patients WHERE deleted = false AND status = 'ACTIVE'", Long.class);
        Long admitted = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM patients WHERE deleted = false AND status = 'ADMITTED'", Long.class);
        return Map.of(
                "totalPatients", total != null ? total : 0,
                "activePatients", active != null ? active : 0,
                "admittedPatients", admitted != null ? admitted : 0
        );
    }
}
