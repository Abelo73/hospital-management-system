package com.act.hospitalmanagementsystem.config;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    @Autowired
    private RateLimitConfig rateLimitConfig;

    @Value("${rate.limit.enabled:true}")
    private boolean rateLimitEnabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!rateLimitEnabled) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = getClientIp(request);
        String requestUri = request.getRequestURI();
        String key = clientIp + ":" + requestUri;

        // Select appropriate bucket based on endpoint
        Supplier<Bucket> bucketSupplier = selectBucketSupplier(requestUri);
        Bucket bucket = rateLimitConfig.resolveBucket(key, bucketSupplier);

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            // Request allowed
            long remainingTokens = probe.getRemainingTokens();
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(remainingTokens));
            response.addHeader("X-Rate-Limit-Limit", String.valueOf(probe.getLimit().getCapacity()));
            filterChain.doFilter(request, response);
        } else {
            // Rate limit exceeded
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            log.warn("Rate limit exceeded for IP: {}, URI: {}, Wait time: {}s", clientIp, requestUri, waitForRefill);

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.addHeader("X-Rate-Limit-Retry-After", String.valueOf(waitForRefill));
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Rate limit exceeded. Please try again later.\"}");
        }
    }

    private Supplier<Bucket> selectBucketSupplier(String requestUri) {
        // Strict rate limiting for authentication endpoints
        if (requestUri.contains("/auth/login") || 
            requestUri.contains("/auth/register") ||
            requestUri.contains("/auth/refresh")) {
            return rateLimitConfig.strictBucketSupplier();
        }

        // Lenient rate limiting for read operations
        if (requestUri.contains("/patients") && requestUri.startsWith("GET") ||
            requestUri.contains("/appointments") && requestUri.startsWith("GET") ||
            requestUri.contains("/medical-records") && requestUri.startsWith("GET")) {
            return rateLimitConfig.lenientBucketSupplier();
        }

        // Default rate limiting
        return rateLimitConfig.bucketSupplier();
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // Handle multiple IPs in X-Forwarded-For
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Skip rate limiting for health checks, actuator endpoints, and static resources
        return path.contains("/actuator/health") ||
               path.contains("/actuator/info") ||
               path.contains("/actuator/metrics") ||
               path.contains("/actuator/prometheus") ||
               path.contains("/swagger-ui") ||
               path.contains("/v3/api-docs") ||
               path.contains("/webjars") ||
               path.endsWith(".css") ||
               path.endsWith(".js") ||
               path.endsWith(".html") ||
               path.endsWith(".ico");
    }
}
