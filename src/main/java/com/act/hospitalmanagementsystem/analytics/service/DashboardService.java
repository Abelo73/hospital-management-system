package com.act.hospitalmanagementsystem.analytics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> getExecutiveDashboard(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> dashboard = new LinkedHashMap<>();

        dashboard.put("period", Map.of("startDate", startDate, "endDate", endDate));
        dashboard.put("totalPatients", queryCount("SELECT COUNT(*) FROM patients WHERE deleted = false"));
        dashboard.put("newPatients", queryCount(
                "SELECT COUNT(*) FROM patients WHERE deleted = false AND created_at BETWEEN ? AND ?", startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay()));
        dashboard.put("totalAppointments", queryCount(
                "SELECT COUNT(*) FROM appointments WHERE deleted = false AND appointment_date BETWEEN ? AND ?", startDate, endDate));
        dashboard.put("completedAppointments", queryCount(
                "SELECT COUNT(*) FROM appointments WHERE deleted = false AND status = 'COMPLETED' AND appointment_date BETWEEN ? AND ?", startDate, endDate));
        dashboard.put("totalConsultations", queryCount(
                "SELECT COUNT(*) FROM consultations WHERE deleted = false AND DATE(consultation_date) BETWEEN ? AND ?", startDate, endDate));
        dashboard.put("totalLabRequests", queryCount(
                "SELECT COUNT(*) FROM lab_test_requests WHERE deleted = false AND DATE(request_date) BETWEEN ? AND ?", startDate, endDate));

        // Trend: patients per month in range
        List<Map<String, Object>> patientTrend = jdbcTemplate.queryForList(
                "SELECT TO_CHAR(created_at, 'Mon') as label, COUNT(*) as value FROM patients WHERE deleted = false AND created_at BETWEEN ? AND ? GROUP BY DATE_TRUNC('month', created_at), TO_CHAR(created_at, 'Mon') ORDER BY DATE_TRUNC('month', created_at)",
                startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        dashboard.put("patientTrend", patientTrend);

        return dashboard;
    }

    public Map<String, Object> getOperationalDashboard(LocalDate date) {
        Map<String, Object> dashboard = new LinkedHashMap<>();
        dashboard.put("date", date);
        dashboard.put("appointmentsToday", queryCount("SELECT COUNT(*) FROM appointments WHERE deleted = false AND appointment_date = ?", date));
        dashboard.put("completedToday", queryCount("SELECT COUNT(*) FROM appointments WHERE deleted = false AND status = 'COMPLETED' AND appointment_date = ?", date));
        dashboard.put("pendingToday", queryCount("SELECT COUNT(*) FROM appointments WHERE deleted = false AND status = 'SCHEDULED' AND appointment_date = ?", date));
        dashboard.put("activePatients", queryCount("SELECT COUNT(*) FROM patients WHERE deleted = false AND status = 'ACTIVE'"));
        dashboard.put("pendingLabRequests", queryCount("SELECT COUNT(*) FROM lab_test_requests WHERE deleted = false AND status = 'PENDING'"));
        return dashboard;
    }

    public Map<String, Object> getFinancialDashboard(int year) {
        Map<String, Object> dashboard = new LinkedHashMap<>();
        dashboard.put("year", year);
        try {
            Map<String, Object> revenue = jdbcTemplate.queryForMap(
                    "SELECT COALESCE(SUM(amount), 0) as total FROM billing_payments WHERE deleted = false AND EXTRACT(YEAR FROM payment_date) = ? AND status = 'COMPLETED'", year);
            dashboard.put("totalRevenue", revenue.get("total"));
        } catch (Exception e) {
            dashboard.put("totalRevenue", 0);
        }
        try {
            dashboard.put("totalInvoices", queryCount("SELECT COUNT(*) FROM billing_invoices WHERE deleted = false AND EXTRACT(YEAR FROM invoice_date) = ?", year));
            dashboard.put("paidInvoices", queryCount("SELECT COUNT(*) FROM billing_invoices WHERE deleted = false AND status = 'PAID' AND EXTRACT(YEAR FROM invoice_date) = ?", year));
            dashboard.put("pendingInvoices", queryCount("SELECT COUNT(*) FROM billing_invoices WHERE deleted = false AND status = 'PENDING' AND EXTRACT(YEAR FROM invoice_date) = ?", year));
        } catch (Exception e) {
            dashboard.put("totalInvoices", 0);
        }
        return dashboard;
    }

    private long queryCount(String sql, Object... args) {
        try {
            Long count = args.length > 0 ? jdbcTemplate.queryForObject(sql, Long.class, args) : jdbcTemplate.queryForObject(sql, Long.class);
            return count != null ? count : 0L;
        } catch (Exception e) {
            return 0L;
        }
    }
}
