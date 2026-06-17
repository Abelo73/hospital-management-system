package com.act.hospitalmanagementsystem.admin.service;

import com.act.hospitalmanagementsystem.admin.dto.SystemHealthDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemHealthService {

    private final JdbcTemplate jdbcTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    public SystemHealthDTO getSystemHealth() {
        Map<String, Object> components = new LinkedHashMap<>();
        String overallStatus = "HEALTHY";

        // Database check
        Map<String, Object> dbInfo = new LinkedHashMap<>();
        try {
            long start = System.currentTimeMillis();
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            long duration = System.currentTimeMillis() - start;
            dbInfo.put("status", "UP");
            dbInfo.put("responseTimeMs", duration);
        } catch (Exception e) {
            dbInfo.put("status", "DOWN");
            dbInfo.put("error", e.getMessage());
            overallStatus = "UNHEALTHY";
        }
        components.put("database", dbInfo);

        // Redis check
        Map<String, Object> redisInfo = new LinkedHashMap<>();
        try {
            long start = System.currentTimeMillis();
            redisTemplate.getConnectionFactory().getConnection().ping();
            long duration = System.currentTimeMillis() - start;
            redisInfo.put("status", "UP");
            redisInfo.put("responseTimeMs", duration);
        } catch (Exception e) {
            redisInfo.put("status", "DOWN");
            redisInfo.put("error", e.getMessage());
            if ("HEALTHY".equals(overallStatus)) overallStatus = "DEGRADED";
        }
        components.put("redis", redisInfo);

        // Memory info
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        double memoryUsagePct = (double) usedMemory / totalMemory * 100;

        Map<String, Object> memInfo = new LinkedHashMap<>();
        memInfo.put("status", memoryUsagePct < 90 ? "UP" : "WARNING");
        memInfo.put("usedMB", usedMemory / (1024 * 1024));
        memInfo.put("totalMB", totalMemory / (1024 * 1024));
        memInfo.put("usagePercent", Math.round(memoryUsagePct * 10.0) / 10.0);
        components.put("memory", memInfo);

        return SystemHealthDTO.builder()
                .status(overallStatus)
                .components(components)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public Map<String, Object> getSystemStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        Runtime runtime = Runtime.getRuntime();
        stats.put("availableProcessors", runtime.availableProcessors());
        stats.put("totalMemoryMB", runtime.totalMemory() / (1024 * 1024));
        stats.put("freeMemoryMB", runtime.freeMemory() / (1024 * 1024));
        stats.put("maxMemoryMB", runtime.maxMemory() / (1024 * 1024));
        stats.put("javaVersion", System.getProperty("java.version"));
        stats.put("osName", System.getProperty("os.name"));
        stats.put("timestamp", LocalDateTime.now());
        return stats;
    }
}
