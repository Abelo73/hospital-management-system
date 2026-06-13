# Notification Module

## Overview

The Notification module provides comprehensive notification and communication capabilities for the Hospital Management System. It handles sending notifications through multiple channels (email, SMS, in-app, push notifications) for various events such as appointments, lab results, prescriptions, reminders, and system alerts.

---

## Features

### 1. Notification Channels
- **Email Notifications** - Send email notifications for various events
- **SMS Notifications** - Send SMS messages for urgent alerts and reminders
- **In-App Notifications** - Display notifications within the application
- **Push Notifications** - Send push notifications to mobile devices
- **Webhook Notifications** - Send notifications to external systems via webhooks

### 2. Notification Types
- **Appointment Reminders** - Remind patients of upcoming appointments
- **Lab Result Notifications** - Notify patients when lab results are ready
- **Prescription Reminders** - Remind patients to take medications
- **System Alerts** - Notify users of system events and issues
- **Approval Notifications** - Notify users of approval requests and decisions
- **Document Notifications** - Notify users of document uploads and verifications
- **Emergency Alerts** - Send urgent notifications for emergencies

### 3. Notification Templates
- **Template Management** - Create and manage notification templates
- **Dynamic Variables** - Use dynamic variables in templates
- **Multi-language Support** - Support multiple languages for notifications
- **Template Versioning** - Track template versions and changes
- **Template Testing** - Test templates before sending

### 4. Notification Preferences
- **User Preferences** - Allow users to set notification preferences
- **Channel Preferences** - Choose preferred notification channels
- **Frequency Control** - Control notification frequency
- **Quiet Hours** - Set quiet hours for non-urgent notifications
- **Category Preferences** - Enable/disable notification categories

### 5. Notification Scheduling
- **Scheduled Notifications** - Schedule notifications for future delivery
- **Recurring Notifications** - Set up recurring notification schedules
- **Time Zone Support** - Handle time zones for scheduled notifications
- **Queue Management** - Manage notification queue and priorities

### 6. Notification Tracking
- **Delivery Tracking** - Track notification delivery status
- **Read Receipts** - Track when notifications are read
- **Click Tracking** - Track clicks on notification links
- **Bounce Handling** - Handle bounced emails and failed SMS
- **Retry Logic** - Retry failed notifications

### 7. Notification Analytics
- **Delivery Reports** - Generate delivery status reports
- **Open Rates** - Track notification open rates
- **Click Rates** - Track click-through rates
- **Response Rates** - Track response rates
- **Performance Metrics** - Analyze notification performance

### 8. Integration
- **Email Service Integration** - Integrate with email service providers
- **SMS Gateway Integration** - Integrate with SMS gateways
- **Push Notification Service** - Integrate with push notification providers
- **Calendar Integration** - Add reminders to user calendars
- **Third-party Integrations** - Integrate with external notification services

---

## Architecture

### Components

```
notification/
├── controller/
│   ├── NotificationController.java      # Notification management endpoints
│   ├── TemplateController.java           # Template management endpoints
│   ├── PreferenceController.java        # Preference management endpoints
│   ├── ScheduleController.java          # Schedule management endpoints
│   └── ReportController.java            # Reporting endpoints
├── service/
│   ├── NotificationService.java         # Notification business logic
│   ├── EmailService.java                # Email sending logic
│   ├── SMSService.java                  # SMS sending logic
│   ├── PushNotificationService.java     # Push notification logic
│   ├── InAppNotificationService.java    # In-app notification logic
│   ├── TemplateService.java             # Template management logic
│   ├── PreferenceService.java           # Preference management logic
│   ├── ScheduleService.java             # Schedule management logic
│   └── TrackingService.java             # Tracking and analytics logic
├── repository/
│   ├── NotificationRepository.java      # Notification data access
│   ├── TemplateRepository.java          # Template data access
│   ├── PreferenceRepository.java       # Preference data access
│   ├── ScheduleRepository.java          # Schedule data access
│   └── TrackingRepository.java          # Tracking data access
├── entity/
│   ├── Notification.java                # Notification entity
│   ├── Template.java                     # Template entity
│   ├── UserPreference.java              # User preference entity
│   ├── NotificationSchedule.java       # Notification schedule entity
│   ├── NotificationLog.java              # Notification log entity
│   └── DeliveryReceipt.java             # Delivery receipt entity
├── enums/
│   ├── NotificationType.java            # Notification type enum
│   ├── NotificationChannel.java          # Notification channel enum
│   ├── NotificationStatus.java          # Notification status enum
│   ├── Priority.java                    # Priority enum
│   └── TemplateType.java                # Template type enum
├── dto/
│   ├── NotificationDTO.java             # Notification DTO
│   ├── SendNotificationRequest.java     # Send notification request
│   ├── TemplateDTO.java                # Template DTO
│   ├── CreateTemplateRequest.java       # Create template request
│   ├── PreferenceDTO.java               # Preference DTO
│   ├── UpdatePreferenceRequest.java     # Update preference request
│   ├── ScheduleDTO.java                # Schedule DTO
│   └── ReportDTO.java                   # Report DTO
└── mapper/
    ├── NotificationMapper.java          # Notification mapper
    ├── TemplateMapper.java              # Template mapper
    └── PreferenceMapper.java            # Preference mapper
```

### Data Model

#### Notification Entity
- `id` (UUID) - Primary key
- `notificationType` (NotificationType) - Type of notification (APPOINTMENT, LAB_RESULT, PRESCRIPTION, SYSTEM_ALERT, etc.)
- `title` (String) - Notification title
- `message` (String) - Notification message
- `channels` (String) - Notification channels (JSON array)
- `priority` (Priority) - Priority level (LOW, MEDIUM, HIGH, URGENT)
- `status` (NotificationStatus) - Current status (PENDING, SENT, DELIVERED, FAILED, CANCELLED)
- `recipientType` (String) - Recipient type (USER, PATIENT, ROLE, GROUP)
- `recipientId` (String) - Recipient ID
- `recipientEmail` (String) - Recipient email
- `recipientPhone` (String) - Recipient phone number
- `template` (Template) - Template used
- `variables` (String) - Template variables (JSON)
- `scheduledFor` (LocalDateTime) - Scheduled delivery time
- `sentAt` (LocalDateTime) - When notification was sent
- `deliveredAt` (LocalDateTime) - When notification was delivered
- `failedAt` (LocalDateTime) - When notification failed
- `failureReason` (String) - Reason for failure
- `retryCount` (Integer) - Number of retry attempts
- `maxRetries` (Integer) - Maximum retry attempts
- `nextRetryAt` (LocalDateTime) - Next retry time
- `metadata` (String) - Additional metadata (JSON)
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### Template Entity
- `id` (UUID) - Primary key
- `templateCode` (String) - Unique template code
- `templateName` (String) - Template name
- `templateType` (TemplateType) - Type of template (EMAIL, SMS, PUSH, IN_APP)
- `notificationType` (NotificationType) - Associated notification type
- `subject` (String) - Subject (for email templates)
- `body` (String) - Template body
- `variables` (String) - Available variables (JSON)
- `language` (String) - Template language (en, sw, etc.)
- `isActive` (Boolean) - Whether template is active
- `version` (Integer) - Template version
- `description` (String) - Template description
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### UserPreference Entity
- `id` (UUID) - Primary key
- `user` (UUID) - Associated user
- `notificationType` (NotificationType) - Notification type
- `emailEnabled` (Boolean) - Email notifications enabled
- `smsEnabled` (Boolean) - SMS notifications enabled
- `pushEnabled` (Boolean) - Push notifications enabled
- `inAppEnabled` (Boolean) - In-app notifications enabled
- `quietHoursStart` (LocalTime) - Quiet hours start time
- `quietHoursEnd` (LocalTime) - Quiet hours end time
- `quietHoursEnabled` (Boolean) - Quiet hours enabled
- `frequency` (String) - Notification frequency (IMMEDIATE, DAILY_DIGEST, WEEKLY_DIGEST)
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### NotificationSchedule Entity
- `id` (UUID) - Primary key
- `scheduleName` (String) - Schedule name
- `notificationType` (NotificationType) - Type of notification
- `template` (Template) - Template to use
- `recipients` (String) - Recipients (JSON)
- `cronExpression` (String) - Cron expression for scheduling
- `timezone` (String) - Timezone
- `isActive` (Boolean) - Whether schedule is active
- `lastRunAt` (LocalDateTime) - Last execution time
- `nextRunAt` (LocalDateTime) - Next scheduled execution time
- `runCount` (Integer) - Number of times run
- `failureCount` (Integer) - Number of failures
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### NotificationLog Entity
- `id` (UUID) - Primary key
- `notification` (Notification) - Associated notification
- `channel` (NotificationChannel) - Channel used (EMAIL, SMS, PUSH, IN_APP)
- `status` (String) - Delivery status (SENT, DELIVERED, FAILED, BOUNCED)
- `sentAt` (LocalDateTime) - When sent
- `deliveredAt` (LocalDateTime) - When delivered
- `readAt` (LocalDateTime) - When read
- `clickedAt` (LocalDateTime) - When clicked
- `errorMessage` (String) - Error message if failed
- `provider` (String) - Notification provider
- `providerMessageId` (String) - Provider message ID
- `metadata` (String) - Additional metadata (JSON)
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### DeliveryReceipt Entity
- `id` (UUID) - Primary key
- `notificationLog` (NotificationLog) - Associated notification log
- `receiptType` (String) - Type of receipt (DELIVERY, READ, CLICK, BOUNCE)
- `receiptData` (String) - Receipt data (JSON)
- `receivedAt` (LocalDateTime) - When receipt was received
- `provider` (String) - Provider that sent the receipt
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

---

## API Endpoints

### Notification Endpoints

#### Send Notification
```http
POST /api/notifications/send
Authorization: Bearer {token}
Content-Type: application/json

{
  "notificationType": "APPOINTMENT_REMINDER",
  "title": "Appointment Reminder",
  "message": "You have an appointment tomorrow at 10:00 AM",
  "channels": ["EMAIL", "SMS"],
  "priority": "MEDIUM",
  "recipientType": "PATIENT",
  "recipientId": "uuid",
  "variables": {
    "patientName": "John Doe",
    "appointmentDate": "2026-06-14",
    "appointmentTime": "10:00",
    "doctorName": "Dr. Smith"
  }
}
```

#### Send Bulk Notification
```http
POST /api/notifications/send-bulk
Authorization: Bearer {token}
Content-Type: application/json

{
  "notificationType": "SYSTEM_ALERT",
  "title": "System Maintenance",
  "message": "System will be under maintenance tonight from 10 PM to 2 AM",
  "channels": ["EMAIL", "IN_APP"],
  "priority": "HIGH",
  "recipientType": "ROLE",
  "recipientId": "DOCTOR",
  "scheduledFor": "2026-06-13T22:00:00"
}
```

#### Get Notification Status
```http
GET /api/notifications/{id}/status
Authorization: Bearer {token}
```

#### Get User Notifications
```http
GET /api/notifications/user?page=0&size=20&status=UNREAD
Authorization: Bearer {token}
```

#### Mark as Read
```http
POST /api/notifications/{id}/read
Authorization: Bearer {token}
```

#### Mark All as Read
```http
POST /api/notifications/read-all
Authorization: Bearer {token}
```

#### Delete Notification
```http
DELETE /api/notifications/{id}
Authorization: Bearer {token}
```

### Template Endpoints

#### Get All Templates
```http
GET /api/notifications/templates?page=0&size=20&type=EMAIL
Authorization: Bearer {token}
```

#### Get Template by Code
```http
GET /api/notifications/templates/code/{templateCode}
Authorization: Bearer {token}
```

#### Create Template
```http
POST /api/notifications/templates
Authorization: Bearer {token}
Content-Type: application/json

{
  "templateCode": "APPOINTMENT_REMINDER_EMAIL",
  "templateName": "Appointment Reminder Email",
  "templateType": "EMAIL",
  "notificationType": "APPOINTMENT_REMINDER",
  "subject": "Appointment Reminder - {{patientName}}",
  "body": "Dear {{patientName}},\n\nThis is a reminder that you have an appointment on {{appointmentDate}} at {{appointmentTime}} with {{doctorName}}.\n\nPlease arrive 15 minutes early.\n\nThank you,\nHospital Management System",
  "variables": ["patientName", "appointmentDate", "appointmentTime", "doctorName"],
  "language": "en"
}
```

#### Update Template
```http
PUT /api/notifications/templates/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "body": "Updated template body"
}
```

#### Test Template
```http
POST /api/notifications/templates/{id}/test
Authorization: Bearer {token}
Content-Type: application/json

{
  "recipientEmail": "test@example.com",
  "variables": {
    "patientName": "Test Patient",
    "appointmentDate": "2026-06-14",
    "appointmentTime": "10:00",
    "doctorName": "Dr. Smith"
  }
}
```

### Preference Endpoints

#### Get User Preferences
```http
GET /api/notifications/preferences?userId={userId}
Authorization: Bearer {token}
```

#### Update User Preferences
```http
PUT /api/notifications/preferences
Authorization: Bearer {token}
Content-Type: application/json

{
  "notificationType": "APPOINTMENT_REMINDER",
  "emailEnabled": true,
  "smsEnabled": false,
  "pushEnabled": true,
  "inAppEnabled": true,
  "quietHoursEnabled": true,
  "quietHoursStart": "22:00",
  "quietHoursEnd": "08:00",
  "frequency": "IMMEDIATE"
}
```

#### Get Default Preferences
```http
GET /api/notifications/preferences/default
Authorization: Bearer {token}
```

### Schedule Endpoints

#### Get Schedules
```http
GET /api/notifications/schedules?status=ACTIVE
Authorization: Bearer {token}
```

#### Create Schedule
```http
POST /api/notifications/schedules
Authorization: Bearer {token}
Content-Type: application/json

{
  "scheduleName": "Daily Appointment Reminders",
  "notificationType": "APPOINTMENT_REMINDER",
  "templateId": "uuid",
  "recipients": {
    "type": "PATIENT",
    "filter": "appointments_tomorrow"
  },
  "cronExpression": "0 0 18 * * ?",
  "timezone": "Africa/Nairobi"
}
```

#### Update Schedule
```http
PUT /api/notifications/schedules/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "isActive": false
}
```

#### Trigger Schedule Manually
```http
POST /api/notifications/schedules/{id}/trigger
Authorization: Bearer {token}
```

### Reporting Endpoints

#### Get Delivery Report
```http
POST /api/notifications/reports/delivery
Authorization: Bearer {token}
Content-Type: application/json

{
  "startDate": "2026-06-01",
  "endDate": "2026-06-30",
  "notificationType": "ALL",
  "channel": "ALL",
  "format": "PDF"
}
```

#### Get Analytics Report
```http
POST /api/notifications/reports/analytics
Authorization: Bearer {token}
Content-Type: application/json

{
  "startDate": "2026-06-01",
  "endDate": "2026-06-30",
  "metrics": ["delivery_rate", "open_rate", "click_rate"]
}
```

---

## Testing Flow Scenarios

### Scenario 1: Send Appointment Reminder

**Steps:**
1. Login as user with NOTIFICATION_WRITE permission
2. Send appointment reminder notification
3. Verify notification is queued
4. Check delivery status
5. Verify recipient receives notification

**Test Commands:**
```bash
# Login
TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Send notification
curl -X POST http://localhost:8080/api/notifications/send \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "notificationType": "APPOINTMENT_REMINDER",
    "title": "Appointment Reminder",
    "message": "You have an appointment tomorrow at 10:00 AM",
    "channels": ["EMAIL", "SMS"],
    "priority": "MEDIUM",
    "recipientType": "PATIENT",
    "recipientId": "uuid",
    "variables": {
      "patientName": "John Doe",
      "appointmentDate": "2026-06-14",
      "appointmentTime": "10:00",
      "doctorName": "Dr. Smith"
    }
  }'

# Expected: 200 OK with notification ID
```

---

### Scenario 2: Template Management

**Steps:**
1. Login as admin
2. Create a new email template
3. Test the template
4. Update template if needed
5. Activate template

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Create template
TEMPLATE_RESPONSE=$(curl -X POST http://localhost:8080/api/notifications/templates \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "templateCode": "LAB_RESULT_EMAIL",
    "templateName": "Lab Result Email",
    "templateType": "EMAIL",
    "notificationType": "LAB_RESULT",
    "subject": "Lab Results Ready - {{patientName}}",
    "body": "Dear {{patientName}},\n\nYour lab results are now available. Please log in to view them.\n\nTest: {{testName}}\nResult: {{result}}",
    "variables": ["patientName", "testName", "result"],
    "language": "en"
  }')

TEMPLATE_ID=$(echo $TEMPLATE_RESPONSE | jq -r '.data.id')

# Test template
curl -X POST http://localhost:8080/api/notifications/templates/$TEMPLATE_ID/test \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "recipientEmail": "test@example.com",
    "variables": {
      "patientName": "Test Patient",
      "testName": "Blood Count",
      "result": "Normal"
    }
  }'

# Expected: 200 OK - test email sent
```

---

### Scenario 3: User Preferences

**Steps:**
1. Login as user
2. Get current notification preferences
3. Update preferences
4. Verify preferences are updated
5. Test notification with new preferences

**Test Commands:**
```bash
# Login as user
USER_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "john_doe", "password": "password"}' \
  | jq -r '.data.accessToken')

# Get preferences
curl -X GET "http://localhost:8080/api/notifications/preferences" \
  -H "Authorization: Bearer $USER_TOKEN"

# Update preferences
curl -X PUT http://localhost:8080/api/notifications/preferences \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "notificationType": "APPOINTMENT_REMINDER",
    "emailEnabled": true,
    "smsEnabled": false,
    "pushEnabled": true,
    "inAppEnabled": true,
    "quietHoursEnabled": true,
    "quietHoursStart": "22:00",
    "quietHoursEnd": "08:00",
    "frequency": "IMMEDIATE"
  }'

# Expected: 200 OK
```

---

### Scenario 4: Scheduled Notifications

**Steps:**
1. Login as admin
2. Create a notification schedule
3. Verify schedule is created
4. Trigger schedule manually for testing
5. Monitor schedule execution

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Create schedule
curl -X POST http://localhost:8080/api/notifications/schedules \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "scheduleName": "Daily Appointment Reminders",
    "notificationType": "APPOINTMENT_REMINDER",
    "templateId": "uuid",
    "recipients": {
      "type": "PATIENT",
      "filter": "appointments_tomorrow"
    },
    "cronExpression": "0 0 18 * * ?",
    "timezone": "Africa/Nairobi"
  }'

# Expected: 200 OK with schedule details
```

---

## Security Considerations

### Access Control
- Sending notifications requires NOTIFICATION_WRITE permission
- Template management requires NOTIFICATION_ADMIN permission
- Schedule management requires NOTIFICATION_ADMIN permission
- User can only view and update their own preferences

### Data Privacy
- Recipient personal data is protected
- Notification content is logged for audit
- Sensitive information in notifications is minimized
- Compliance with data protection regulations

### Rate Limiting
- Bulk notifications are rate limited
- SMS notifications have strict rate limits
- Email notifications have daily limits
- Prevent notification spam

---

## Dependencies

### Internal Dependencies
- `auth` - For user authentication and authorization
- `patient` - For patient notifications
- `appointment` - For appointment reminders
- `medical` - For lab result notifications
- `doctor` - For doctor notifications
- `common` - For shared utilities and DTOs

### External Dependencies
- Spring Mail - For email sending
- Twilio/AfricasTalking - For SMS sending
- Firebase Cloud Messaging - For push notifications
- SendGrid/Mailgun - For email service provider (optional)
- Quartz Scheduler - For scheduled notifications

---

## Future Enhancements

### Planned Features
- Rich media notifications (images, videos)
- Interactive notifications (action buttons)
- Notification threads and conversations
- AI-powered notification personalization
- Integration with WhatsApp Business API
- Voice call notifications for emergencies
- Location-based notifications
- Integration with calendar services

### Performance Improvements
- Implement message queue for async processing
- Use connection pooling for email/SMS
- Implement caching for templates
- Optimize database queries for notification logs
- Use batch processing for bulk notifications

---

## Notes

- This module is critical for patient communication and engagement
- Ensure proper handling of failed notifications
- Implement proper retry logic with exponential backoff
- Consider implementing notification grouping to reduce spam
- Regularly review and optimize notification templates
- Monitor notification delivery rates and performance
- Ensure compliance with SMS and email regulations
