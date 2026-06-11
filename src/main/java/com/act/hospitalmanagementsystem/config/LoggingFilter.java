package com.act.hospitalmanagementsystem.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Value("${logging.filter.enabled:true}")
    private boolean loggingFilterEnabled;

    @Value("${logging.filter.include-payload:true}")
    private boolean includePayload;

    @Value("${logging.filter.max-payload-length:1000}")
    private int maxPayloadLength;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!loggingFilterEnabled) {
            filterChain.doFilter(request, response);
            return;
        }

        // Skip logging for health checks and actuator endpoints
        String path = request.getRequestURI();
        if (shouldSkipLogging(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        long startTime = System.currentTimeMillis();

        // Wrap request and response to cache content
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logRequest(wrappedRequest, duration);
            logResponse(wrappedResponse, duration);
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request, long duration) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("HTTP Request: ")
                .append(request.getMethod())
                .append(" ")
                .append(request.getRequestURI());

        if (request.getQueryString() != null) {
            logMessage.append("?").append(request.getQueryString());
        }

        logMessage.append(" | Headers: ").append(getHeadersAsString(request));

        if (includePayload) {
            String payload = getPayload(request.getContentAsByteArray());
            if (payload != null && !payload.isEmpty()) {
                logMessage.append(" | Payload: ").append(truncatePayload(payload));
            }
        }

        logMessage.append(" | Client IP: ").append(getClientIp(request));

        log.info(logMessage.toString());
    }

    private void logResponse(ContentCachingResponseWrapper response, long duration) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("HTTP Response: ")
                .append(response.getStatus())
                .append(" | Duration: ").append(duration).append("ms");

        if (includePayload) {
            String payload = getPayload(response.getContentAsByteArray());
            if (payload != null && !payload.isEmpty()) {
                logMessage.append(" | Payload: ").append(truncatePayload(payload));
            }
        }

        log.info(logMessage.toString());
    }

    private String getHeadersAsString(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            // Skip sensitive headers
            if (!headerName.equalsIgnoreCase("Authorization") &&
                !headerName.equalsIgnoreCase("Cookie") &&
                !headerName.equalsIgnoreCase("Set-Cookie")) {
                headers.put(headerName, request.getHeader(headerName));
            }
        }
        return headers.toString();
    }

    private String getPayload(byte[] content) {
        if (content == null || content.length == 0) {
            return null;
        }
        return new String(content, StandardCharsets.UTF_8);
    }

    private String truncatePayload(String payload) {
        if (payload.length() > maxPayloadLength) {
            return payload.substring(0, maxPayloadLength) + "... [truncated]";
        }
        return payload;
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    private boolean shouldSkipLogging(String path) {
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
               path.endsWith(".ico") ||
               path.endsWith(".png") ||
               path.endsWith(".jpg") ||
               path.endsWith(".jpeg") ||
               path.endsWith(".gif") ||
               path.endsWith(".svg");
    }
}
