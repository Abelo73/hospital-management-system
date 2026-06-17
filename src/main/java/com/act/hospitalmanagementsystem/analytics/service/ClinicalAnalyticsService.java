package com.act.hospitalmanagementsystem.analytics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClinicalAnalyticsService {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getTopDiagnoses(int limit) {
        return jdbcTemplate.queryForList(
                "SELECT cd.description as diagnosis, COUNT(*) as count FROM consultation_diagnoses cd JOIN consultations c ON cd.consultation_id = c.id WHERE c.deleted = false AND cd.deleted = false GROUP BY cd.description ORDER BY count DESC LIMIT ?", limit);
    }

    public List<Map<String, Object>> getConsultationsByDoctor(int limit) {
        return jdbcTemplate.queryForList(
                "SELECT c.doctor_id, u.first_name || ' ' || u.last_name as doctor_name, COUNT(*) as count FROM consultations c JOIN users u ON c.doctor_id = u.id WHERE c.deleted = false GROUP BY c.doctor_id, doctor_name ORDER BY count DESC LIMIT ?", limit);
    }

    public Map<String, Object> getLabTestStats() {
        Long totalRequests = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM lab_test_requests WHERE deleted = false", Long.class);
        Long pending = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM lab_test_requests WHERE deleted = false AND status = 'PENDING'", Long.class);
        Long completed = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM lab_test_requests WHERE deleted = false AND status = 'COMPLETED'", Long.class);
        return Map.of(
                "totalRequests", totalRequests != null ? totalRequests : 0,
                "pending", pending != null ? pending : 0,
                "completed", completed != null ? completed : 0
        );
    }

    public List<Map<String, Object>> getTopLabTests(int limit) {
        return jdbcTemplate.queryForList(
                "SELECT lt.test_name, COUNT(*) as count FROM lab_test_request_items i JOIN lab_tests lt ON i.test_id = lt.id WHERE i.deleted = false GROUP BY lt.test_name ORDER BY count DESC LIMIT ?", limit);
    }
}
