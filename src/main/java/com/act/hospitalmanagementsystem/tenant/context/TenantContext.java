package com.act.hospitalmanagementsystem.tenant.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantContext {

    private static final Logger log = LoggerFactory.getLogger(TenantContext.class);
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();
    public static final String DEFAULT_TENANT_ID = "tenant-default-001";

    public static void setCurrentTenant(String tenantId) {
        if (tenantId != null && !tenantId.isBlank()) {
            log.trace("Setting TenantContext to: {}", tenantId);
            CURRENT_TENANT.set(tenantId);
        } else {
            CURRENT_TENANT.set(DEFAULT_TENANT_ID);
        }
    }

    public static String getCurrentTenant() {
        String tenant = CURRENT_TENANT.get();
        return (tenant != null && !tenant.isBlank()) ? tenant : DEFAULT_TENANT_ID;
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
