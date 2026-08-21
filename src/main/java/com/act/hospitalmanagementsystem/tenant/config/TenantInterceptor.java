package com.act.hospitalmanagementsystem.tenant.config;

import com.act.hospitalmanagementsystem.tenant.context.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(TenantInterceptor.class);
    private static final String TENANT_HEADER = "X-Tenant-ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = request.getHeader(TENANT_HEADER);

        if (tenantId == null || tenantId.isBlank()) {
            // Check subdomain (e.g. saintmarys.hms-cloud.com -> saintmarys)
            String serverName = request.getServerName();
            if (serverName != null && serverName.contains(".")) {
                String[] parts = serverName.split("\\.");
                if (parts.length > 2 && !parts[0].equalsIgnoreCase("www") && !parts[0].equalsIgnoreCase("localhost")) {
                    tenantId = parts[0];
                }
            }
        }

        if (tenantId == null || tenantId.isBlank()) {
            tenantId = TenantContext.DEFAULT_TENANT_ID;
        }

        TenantContext.setCurrentTenant(tenantId);
        log.debug("Request [{}] {} resolved Tenant ID: {}", request.getMethod(), request.getRequestURI(), tenantId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContext.clear();
    }
}
