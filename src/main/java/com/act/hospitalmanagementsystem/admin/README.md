# Admin Module

## Overview

The Admin module provides administrative capabilities for the Hospital Management System. It handles system-wide configuration, settings management, audit logs, and administrative operations that require elevated privileges.

---

## Features

### 1. System Configuration
- **Application Settings** - Manage application-wide settings and configurations
- **Feature Flags** - Enable/disable features dynamically
- **System Parameters** - Configure system parameters and thresholds
- **Environment Variables** - Manage environment-specific settings

### 2. Audit Logging
- **User Activity Logs** - Track all user actions across the system
- **System Event Logs** - Log system-level events and errors
- **Audit Trail** - Complete audit trail for compliance
- **Log Export** - Export logs for analysis and reporting

### 3. System Monitoring
- **Health Checks** - Monitor system health and performance
- **Metrics Collection** - Collect and display system metrics
- **Alert Management** - Configure and manage system alerts
- **Performance Monitoring** - Track response times and resource usage

### 4. Data Management
- **Database Backup** - Schedule and manage database backups
- **Data Export** - Export data in various formats
- **Data Import** - Import data from external sources
- **Data Archival** - Archive old data for long-term storage

### 5. Security Management
- **Security Policies** - Define and enforce security policies
- **Access Logs** - Monitor access patterns and detect anomalies
- **Session Management** - Manage active user sessions
- **Security Reports** - Generate security compliance reports

### 6. System Maintenance
- **Scheduled Tasks** - Configure and monitor scheduled tasks
- **Cache Management** - Manage system caches
- **Index Management** - Rebuild and optimize database indexes
- **System Cleanup** - Clean up temporary files and logs

---

## Architecture

### Components

```
admin/
├── controller/
│   ├── SystemConfigController.java       # System configuration endpoints
│   ├── AuditLogController.java           # Audit log endpoints
│   ├── SystemHealthController.java       # System health monitoring endpoints
│   ├── DataManagementController.java     # Data management endpoints
│   ├── SecurityController.java           # Security management endpoints
│   └── MaintenanceController.java        # System maintenance endpoints
├── service/
│   ├── SystemConfigService.java          # System configuration business logic
│   ├── AuditLogService.java              # Audit logging business logic
│   ├── SystemHealthService.java          # System health monitoring logic
│   ├── DataManagementService.java        # Data management business logic
│   ├── SecurityService.java              # Security management logic
│   └── MaintenanceService.java           # System maintenance logic
├── repository/
│   ├── SystemConfigRepository.java       # System configuration data access
│   ├── AuditLogRepository.java           # Audit log data access
│   ├── SystemMetricRepository.java       # System metrics data access
│   └── ScheduledTaskRepository.java      # Scheduled task data access
├── entity/
│   ├── SystemConfig.java                 # System configuration entity
│   ├── AuditLog.java                     # Audit log entity
│   ├── SystemMetric.java                 # System metric entity
│   ├── ScheduledTask.java                # Scheduled task entity
│   └── SecurityEvent.java                # Security event entity
├── enums/
│   ├── ConfigType.java                  # Configuration type enum
│   ├── LogLevel.java                    # Log level enum
│   ├── MetricType.java                  # Metric type enum
│   ├── TaskStatus.java                   # Task status enum
│   └── SecurityEventType.java            # Security event type enum
├── dto/
│   ├── SystemConfigDTO.java              # System configuration DTO
│   ├── UpdateConfigRequest.java          # Update configuration request
│   ├── AuditLogDTO.java                  # Audit log DTO
│   ├── SystemHealthDTO.java              # System health DTO
│   ├── BackupRequest.java                # Backup request DTO
│   ├── RestoreRequest.java               # Restore request DTO
│   ├── SecurityPolicyDTO.java            # Security policy DTO
│   └── ScheduledTaskDTO.java             # Scheduled task DTO
└── mapper/
    ├── SystemConfigMapper.java           # System configuration mapper
    ├── AuditLogMapper.java               # Audit log mapper
    └── SystemMetricMapper.java           # System metric mapper
```

### Data Model

#### SystemConfig Entity
- `id` (UUID) - Primary key
- `configKey` (String) - Unique configuration key
- `configValue` (String) - Configuration value
- `configType` (ConfigType) - Type of configuration (STRING, NUMBER, BOOLEAN, JSON)
- `description` (String) - Configuration description
- `category` (String) - Configuration category
- `isEditable` (Boolean) - Whether configuration can be edited
- `requiresRestart` (Boolean) - Whether changes require application restart
- `validationRegex` (String) - Validation regex for value
- `minValue` (String) - Minimum value (for numeric types)
- `maxValue` (String) - Maximum value (for numeric types)
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### AuditLog Entity
- `id` (UUID) - Primary key
- `userId` (UUID) - User who performed the action
- `username` (String) - Username of the user
- `action` (String) - Action performed (CREATE, UPDATE, DELETE, LOGIN, LOGOUT, etc.)
- `entityType` (String) - Type of entity affected
- `entityId` (String) - ID of the entity affected
- `oldValue` (String) - Old value (for updates)
- `newValue` (String) - New value (for updates)
- `ipAddress` (String) - IP address of the user
- `userAgent` (String) - User agent string
- `requestUrl` (String) - Request URL
- `requestMethod` (String) - HTTP method
- `responseStatus` (Integer) - HTTP response status
- `duration` (Long) - Request duration in milliseconds
- `errorMessage` (String) - Error message (if any)
- `timestamp` (LocalDateTime) - When the action occurred
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### SystemMetric Entity
- `id` (UUID) - Primary key
- `metricType` (MetricType) - Type of metric (CPU, MEMORY, DISK, NETWORK, DATABASE)
- `metricName` (String) - Name of the metric
- `metricValue` (Double) - Metric value
- `unit` (String) - Unit of measurement (%, MB, GB, etc.)
- `threshold` (Double) - Alert threshold
- `isAlert` (Boolean) - Whether metric exceeded threshold
- `timestamp` (LocalDateTime) - When the metric was recorded
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### ScheduledTask Entity
- `id` (UUID) - Primary key
- `taskName` (String) - Unique task name
- `taskType` (String) - Type of task (BACKUP, CLEANUP, REPORT, etc.)
- `cronExpression` (String) - Cron expression for scheduling
- `description` (String) - Task description
- `status` (TaskStatus) - Current status (ACTIVE, PAUSED, COMPLETED, FAILED)
- `lastRunAt` (LocalDateTime) - Last execution time
- `nextRunAt` (LocalDateTime) - Next scheduled execution time
- `lastRunStatus` (String) - Status of last run
- `lastRunDuration` (Long) - Duration of last run in milliseconds
- `lastRunMessage` (String) - Message from last run
- `failureCount` (Integer) - Number of consecutive failures
- `enabled` (Boolean) - Whether task is enabled
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

---

## API Endpoints

### System Configuration Endpoints

#### Get All Configurations
```http
GET /api/admin/config
Authorization: Bearer {adminToken}
```

#### Get Configuration by Key
```http
GET /api/admin/config/{configKey}
Authorization: Bearer {adminToken}
```

#### Update Configuration
```http
PUT /api/admin/config/{configKey}
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "configValue": "new_value"
}
```

#### Reset Configuration to Default
```http
POST /api/admin/config/{configKey}/reset
Authorization: Bearer {adminToken}
```

### Audit Log Endpoints

#### Get Audit Logs
```http
GET /api/admin/audit-logs?page=0&size=20&startDate=2026-01-01&endDate=2026-12-31
Authorization: Bearer {adminToken}
```

#### Get Audit Log by ID
```http
GET /api/admin/audit-logs/{id}
Authorization: Bearer {adminToken}
```

#### Export Audit Logs
```http
GET /api/admin/audit-logs/export?format=CSV&startDate=2026-01-01&endDate=2026-12-31
Authorization: Bearer {adminToken}
```

### System Health Endpoints

#### Get System Health
```http
GET /api/admin/health
Authorization: Bearer {adminToken}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "status": "HEALTHY",
    "components": {
      "database": {
        "status": "UP",
        "responseTime": 50
      },
      "redis": {
        "status": "UP",
        "responseTime": 10
      },
      "disk": {
        "status": "UP",
        "usage": 45.5
      },
      "memory": {
        "status": "UP",
        "usage": 62.3
      }
    },
    "timestamp": "2026-06-13T10:00:00"
  }
}
```

#### Get System Metrics
```http
GET /api/admin/metrics?metricType=CPU&period=1h
Authorization: Bearer {adminToken}
```

### Data Management Endpoints

#### Create Database Backup
```http
POST /api/admin/backup
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "backupName": "daily_backup",
  "description": "Daily backup"
}
```

#### Restore Database
```http
POST /api/admin/restore
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "backupId": "uuid",
  "confirm": true
}
```

#### Export Data
```http
POST /api/admin/data/export
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "entityType": "PATIENT",
  "format": "CSV",
  "startDate": "2026-01-01",
  "endDate": "2026-12-31"
}
```

### Security Management Endpoints

#### Get Security Events
```http
GET /api/admin/security/events?page=0&size=20
Authorization: Bearer {adminToken}
```

#### Get Active Sessions
```http
GET /api/admin/security/sessions
Authorization: Bearer {adminToken}
```

#### Terminate Session
```http
DELETE /api/admin/security/sessions/{sessionId}
Authorization: Bearer {adminToken}
```

#### Generate Security Report
```http
POST /api/admin/security/reports
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "reportType": "ACCESS_LOG",
  "startDate": "2026-01-01",
  "endDate": "2026-12-31"
}
```

### Maintenance Endpoints

#### Get Scheduled Tasks
```http
GET /api/admin/maintenance/tasks
Authorization: Bearer {adminToken}
```

#### Create Scheduled Task
```http
POST /api/admin/maintenance/tasks
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "taskName": "daily_cleanup",
  "taskType": "CLEANUP",
  "cronExpression": "0 0 2 * * ?",
  "description": "Daily cleanup task"
}
```

#### Execute Task Manually
```http
POST /api/admin/maintenance/tasks/{taskId}/execute
Authorization: Bearer {adminToken}
```

#### Clear System Cache
```http
POST /api/admin/maintenance/cache/clear
Authorization: Bearer {adminToken}
```

#### Rebuild Indexes
```http
POST /api/admin/maintenance/indexes/rebuild
Authorization: Bearer {adminToken}
```

---

## Testing Flow Scenarios

### Scenario 1: System Configuration Management

**Steps:**
1. Login as admin
2. Get all system configurations
3. Update a configuration value
4. Verify the change is applied
5. Reset configuration to default

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Get all configurations
curl -X GET http://localhost:8080/api/admin/config \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Update configuration
curl -X PUT http://localhost:8080/api/admin/config/MAX_LOGIN_ATTEMPTS \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"configValue": "5"}'

# Expected: 200 OK
```

---

### Scenario 2: Audit Log Review

**Steps:**
1. Login as admin
2. Get audit logs for a specific date range
3. Filter logs by action type
4. Export logs to CSV
5. Review security events

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Get audit logs
curl -X GET "http://localhost:8080/api/admin/audit-logs?page=0&size=20&startDate=2026-06-01&endDate=2026-06-30" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Export logs
curl -X GET "http://localhost:8080/api/admin/audit-logs/export?format=CSV&startDate=2026-06-01&endDate=2026-06-30" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -o audit_logs.csv

# Expected: CSV file downloaded
```

---

### Scenario 3: System Health Monitoring

**Steps:**
1. Login as admin
2. Get system health status
3. Get system metrics for specific period
4. Check for any alerts
5. Take action if needed

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Get system health
curl -X GET http://localhost:8080/api/admin/health \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Get CPU metrics
curl -X GET "http://localhost:8080/api/admin/metrics?metricType=CPU&period=1h" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Expected: 200 OK with health and metrics data
```

---

### Scenario 4: Database Backup and Restore

**Steps:**
1. Login as admin
2. Create a database backup
3. Verify backup was created
4. (Optional) Restore from backup
5. Verify data integrity

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Create backup
curl -X POST http://localhost:8080/api/admin/backup \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "backupName": "manual_backup",
    "description": "Manual backup before changes"
  }'

# Expected: 200 OK with backup details
```

---

### Scenario 5: Security Session Management

**Steps:**
1. Login as admin
2. Get all active sessions
3. Identify suspicious session
4. Terminate suspicious session
5. Generate security report

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Get active sessions
curl -X GET http://localhost:8080/api/admin/security/sessions \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Terminate session
curl -X DELETE http://localhost:8080/api/admin/security/sessions/{sessionId} \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Expected: 200 OK
```

---

### Scenario 6: Scheduled Task Management

**Steps:**
1. Login as admin
2. Get all scheduled tasks
3. Create a new scheduled task
4. Execute task manually
5. Monitor task execution

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Get scheduled tasks
curl -X GET http://localhost:8080/api/admin/maintenance/tasks \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Create task
curl -X POST http://localhost:8080/api/admin/maintenance/tasks \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "taskName": "weekly_report",
    "taskType": "REPORT",
    "cronExpression": "0 0 8 ? * MON",
    "description": "Weekly report generation"
  }'

# Expected: 200 OK with task details
```

---

## Security Considerations

### Access Control
- All admin endpoints require ADMIN role
- Sensitive operations require additional confirmation
- Audit logging for all admin actions
- IP-based access restrictions can be configured

### Data Protection
- Sensitive configuration values are encrypted
- Audit logs are immutable
- Backup files are encrypted at rest
- Export operations are logged

### Rate Limiting
- Admin endpoints have strict rate limiting
- Backup/restore operations are rate limited
- Export operations are rate limited
- Bulk operations are rate limited

---

## Dependencies

### Internal Dependencies
- `auth` - For user authentication and authorization
- `common` - For shared utilities and DTOs
- `config` - For application configuration

### External Dependencies
- Spring Boot Actuator - For health checks and metrics
- Spring Batch - For scheduled task execution
- Spring Security - For security management
- Quartz Scheduler - For advanced scheduling

---

## Future Enhancements

### Planned Features
- Real-time dashboard for system monitoring
- Automated alert notifications
- Advanced analytics and reporting
- Integration with external monitoring tools
- Multi-tenancy support
- Advanced backup strategies (incremental, differential)

### Performance Improvements
- Caching for frequently accessed configurations
- Async processing for long-running tasks
- Database query optimization
- Index optimization for audit logs

---

## Notes

- This module is critical for system administration and should be implemented with high priority
- All admin actions should be audited for compliance
- Consider implementing role-based access within admin module (e.g., SUPER_ADMIN, SYSTEM_ADMIN)
- Implement proper error handling and rollback mechanisms for critical operations
