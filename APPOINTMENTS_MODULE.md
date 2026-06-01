# Appointments Module

## Overview
The Appointments Module manages all scheduling and appointment-related functionality for the hospital management system. This module handles patient appointments, consultations, follow-ups, and the complete scheduling workflow from booking to completion.

## Core Responsibilities

### 1. Appointment Scheduling
- **Appointment Booking**: Schedule new patient appointments
- **Availability Management**: Manage provider availability and time slots
- **Calendar View**: Visual calendar for scheduling
- **Conflict Detection**: Detect and prevent scheduling conflicts
- **Recurring Appointments**: Schedule recurring follow-up appointments
- **Waitlist Management**: Manage waitlists for fully booked slots

### 2. Appointment Types
- **Initial Consultation**: First-time patient consultations
- **Follow-up Visits**: Follow-up appointments for ongoing care
- **Routine Check-ups**: Regular health check-ups
- **Specialist Consultations**: Appointments with specialists
- **Procedures**: Scheduled medical procedures
- **Emergency Appointments**: Urgent care appointments
- **Telemedicine**: Virtual/online consultations
- **Lab Tests**: Scheduled lab test appointments
- **Imaging**: Scheduled imaging appointments (X-ray, MRI, CT, etc.)

### 3. Appointment Status & Workflow
- **Status Tracking**: Scheduled, Confirmed, In Progress, Completed, Cancelled, No-Show
- **Confirmation System**: Automated appointment confirmations
- **Reminder System**: Appointment reminders via SMS/email
- **Check-in Process**: Patient check-in workflow
- **Room Assignment**: Assign examination rooms to appointments
- **Provider Assignment**: Assign healthcare providers to appointments

### 4. Time Management
- **Duration Management**: Set appointment durations by type
- **Buffer Times**: Buffer times between appointments
- **Time Slots**: Define available time slots for scheduling
- **Working Hours**: Provider working hours and availability
- **Break Times**: Provider break times and unavailability
- **Holiday Management**: Manage holidays and clinic closures

### 5. Patient Communication
- **Appointment Confirmations**: Send confirmation messages
- **Reminders**: Automated reminders (SMS, email, push notifications)
- **Cancellation Handling**: Handle appointment cancellations
- **Rescheduling**: Process appointment rescheduling requests
- **No-Show Tracking**: Track patient no-shows
- **Follow-up Scheduling**: Schedule follow-up appointments

### 6. Provider Management
- **Provider Availability**: Manage provider schedules
- **Provider Specialties**: Match appointments to appropriate specialists
- **Workload Balancing**: Balance provider workload
- **On-Call Scheduling**: Manage on-call schedules
- **Multi-provider Appointments**: Appointments with multiple providers

### 7. Clinic/Department Management
- **Department Scheduling**: Schedule by department
- **Room Management**: Manage examination rooms and facilities
- **Equipment Scheduling**: Schedule equipment usage
- **Location Management**: Multiple clinic locations
- **Department Hours**: Department-specific operating hours

### 8. Reporting & Analytics
- **Appointment Statistics**: Track appointment metrics
- **No-Show Rates**: Monitor patient no-show rates
- **Provider Utilization**: Track provider utilization rates
- **Wait Time Analysis**: Analyze patient wait times
- **Revenue Tracking**: Track revenue by appointment type
- **Peak Hours Analysis**: Identify peak appointment times

## Data Structure

### Appointment Entity
```java
- id: UUID
- patientId: UUID (FK to Patient)
- providerId: UUID (FK to User)
- departmentId: UUID (FK to Department)
- roomId: UUID (FK to Room)
- appointmentType: enum (CONSULTATION, FOLLOW_UP, PROCEDURE, EMERGENCY, TELEMEDICINE, LAB_TEST, IMAGING)
- appointmentStatus: enum (SCHEDULED, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED, NO_SHOW)
- scheduledDate: LocalDate
- scheduledTime: LocalTime
- duration: Integer (minutes)
- reason: String
- notes: Text
- isRecurring: Boolean
- recurringPattern: String (daily, weekly, monthly)
- recurringEndDate: LocalDate
- checkInTime: LocalDateTime
- startTime: LocalDateTime
- endTime: LocalDateTime
- cancellationReason: String
- cancelledBy: UUID (FK to User)
- cancelledAt: LocalDateTime
- createdBy: UUID (FK to User)
- createdAt: LocalDateTime
- updatedBy: UUID (FK to User)
- updatedAt: LocalDateTime
- deleted: Boolean
```

### AppointmentSlot Entity
```java
- id: UUID
- providerId: UUID (FK to User)
- departmentId: UUID (FK to Department)
- roomId: UUID (FK to Room)
- date: LocalDate
- startTime: LocalTime
- endTime: LocalTime
- isAvailable: Boolean
- slotType: enum (REGULAR, EMERGENCY, TELEMEDICINE)
- maxPatients: Integer
- bookedCount: Integer
- createdBy: UUID (FK to User)
- createdAt: LocalDateTime
- updatedBy: UUID (FK to User)
- updatedAt: LocalDateTime
```

### ProviderSchedule Entity
```java
- id: UUID
- providerId: UUID (FK to User)
- dayOfWeek: enum (MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
- startTime: LocalTime
- endTime: LocalTime
- breakStartTime: LocalTime
- breakEndTime: LocalTime
- isAvailable: Boolean
- effectiveFromDate: LocalDate
- effectiveToDate: LocalDate
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

### AppointmentReminder Entity
```java
- id: UUID
- appointmentId: UUID (FK to Appointment)
- reminderType: enum (SMS, EMAIL, PUSH_NOTIFICATION)
- scheduledTime: LocalDateTime
- sentTime: LocalDateTime
- status: enum (PENDING, SENT, FAILED)
- message: String
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

### WaitlistEntry Entity
```java
- id: UUID
- patientId: UUID (FK to Patient)
- providerId: UUID (FK to User)
- departmentId: UUID (FK to Department)
- appointmentType: enum
- preferredDate: LocalDate
- preferredTime: LocalTime
- duration: Integer
- reason: String
- priority: enum (LOW, MEDIUM, HIGH, URGENT)
- status: enum (PENDING, SCHEDULED, CANCELLED, EXPIRED)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

## API Endpoints

### Appointments
- `GET /api/appointments` - Get all appointments (with pagination)
- `GET /api/appointments/{id}` - Get specific appointment
- `GET /api/appointments/patient/{patientId}` - Get patient appointments
- `GET /api/appointments/provider/{providerId}` - Get provider appointments
- `GET /api/appointments/department/{departmentId}` - Get department appointments
- `GET /api/appointments/date/{date}` - Get appointments by date
- `POST /api/appointments` - Create new appointment
- `PUT /api/appointments/{id}` - Update appointment
- `DELETE /api/appointments/{id}` - Delete appointment
- `POST /api/appointments/{id}/cancel` - Cancel appointment
- `POST /api/appointments/{id}/confirm` - Confirm appointment
- `POST /api/appointments/{id}/check-in` - Patient check-in
- `POST /api/appointments/{id}/start` - Start appointment
- `POST /api/appointments/{id}/complete` - Complete appointment
- `GET /api/appointments/available-slots` - Get available time slots
- `POST /api/appointments/search` - Search appointments

### Provider Schedules
- `GET /api/provider-schedules/provider/{providerId}` - Get provider schedule
- `POST /api/provider-schedules` - Create provider schedule
- `PUT /api/provider-schedules/{id}` - Update provider schedule
- `DELETE /api/provider-schedules/{id}` - Delete provider schedule
- `GET /api/provider-schedules/availability` - Get provider availability

### Appointment Slots
- `GET /api/appointment-slots` - Get available slots
- `POST /api/appointment-slots` - Create appointment slot
- `PUT /api/appointment-slots/{id}` - Update appointment slot
- `DELETE /api/appointment-slots/{id}` - Delete appointment slot

### Waitlist
- `GET /api/waitlist` - Get all waitlist entries
- `POST /api/waitlist` - Add to waitlist
- `PUT /api/waitlist/{id}` - Update waitlist entry
- `DELETE /api/waitlist/{id}` - Remove from waitlist
- `POST /api/waitlist/{id}/schedule` - Schedule from waitlist

### Reminders
- `GET /api/appointment-reminders` - Get reminders
- `POST /api/appointment-reminders` - Create reminder
- `PUT /api/appointment-reminders/{id}` - Update reminder
- `DELETE /api/appointment-reminders/{id}` - Delete reminder

## UI Components

### Appointments Dashboard
- **Calendar View**: Monthly/weekly/daily calendar views
- **Today's Schedule**: Today's appointments at a glance
- **Upcoming Appointments**: List of upcoming appointments
- **Quick Actions**: Quick book, reschedule, cancel buttons
- **Statistics**: Key metrics (no-show rate, utilization)

### Appointment Booking
- **Patient Selection**: Search and select patient
- **Provider Selection**: Select healthcare provider
- **Date/Time Selection**: Calendar and time slot picker
- **Appointment Type**: Select appointment type
- **Duration**: Set appointment duration
- **Reason**: Enter appointment reason
- **Notes**: Additional notes
- **Conflict Detection**: Show scheduling conflicts

### Appointment Calendar
- **Multi-view Calendar**: Day, week, month views
- **Filter Options**: Filter by provider, department, type
- **Drag & Drop**: Drag to reschedule appointments
- **Color Coding**: Color code by appointment type/status
- **Appointment Details**: Click to view details
- **Quick Actions**: Quick edit, cancel, reschedule

### Provider Schedule Management
- **Weekly Schedule**: Set weekly working hours
- **Break Times**: Configure break times
- **Availability**: Mark available/unavailable times
- **Bulk Updates**: Bulk update schedules
- **Template Management**: Schedule templates for reuse

### Patient Appointment History
- **Appointment List**: Patient's appointment history
- **Status Indicators**: Visual status indicators
- **Filter Options**: Filter by status, type, date
- **Upcoming vs Past**: Separate upcoming and past appointments
- **Book New**: Button to book new appointment

### Appointment Detail View
- **Complete Information**: All appointment details
- **Patient Information**: Linked patient profile
- **Provider Information**: Assigned provider details
- **Timeline**: Appointment timeline (scheduled → completed)
- **Actions**: Reschedule, cancel, complete actions
- **Related Records**: Link to medical records, billing

### Check-in Interface
- **Patient Lookup**: Search patient by name/ID
- **Appointment Selection**: Select today's appointment
- **Check-in Button**: Process patient check-in
- **Wait Time Display**: Current wait time
- **Room Assignment**: Assign examination room
- **Status Updates**: Update appointment status

### Waitlist Management
- **Waitlist View**: All waitlist entries
- **Priority Sorting**: Sort by priority
- **Auto-schedule**: Auto-schedule when slots open
- **Notifications**: Notify patients when slot available
- **Expiration**: Auto-expire old entries

## Integration with Other Modules

### Patient Module
- **Patient Information**: Access patient demographics
- **Contact Details**: Use for reminders/confirmations
- **Medical History**: View patient history during appointment
- **Patient Preferences**: Respect patient preferences

### Medical History Module
- **Link Medical Records**: Link appointments to medical records
- **Consultation Notes**: Save consultation notes as medical records
- **Diagnosis Recording**: Record diagnoses during appointment
- **Prescription Writing**: Write prescriptions during appointment

### Billing Module
- **Invoice Generation**: Generate invoices for appointments
- **Payment Processing**: Process payments
- **Insurance Claims**: Submit insurance claims
- **Cost Calculation**: Calculate appointment costs

### User/Staff Module
- **Provider Information**: Access provider profiles
- **Role-based Access**: Different access for different roles
- **Audit Trail**: Track who scheduled/modified appointments

### Notification Module
- **SMS Reminders**: Send SMS reminders
- **Email Notifications**: Send email confirmations
- **Push Notifications**: Send mobile app notifications

## Security & Access Control

### Role-Based Access
- **Admin**: Full access to all appointment functions
- **Doctor**: View/manage own appointments
- **Nurse**: View department appointments, check-in patients
- **Receptionist**: Schedule appointments, check-in patients
- **Patient**: View own appointments (patient portal)

### Privacy Considerations
- **Patient Privacy**: Protect patient appointment information
- **Provider Privacy**: Protect provider schedules
- **Audit Logging**: Log all appointment access/modifications

## Search & Filtering

### Advanced Search
- **Patient Search**: Search by patient name/ID
- **Provider Search**: Search by provider name
- **Date Range**: Filter by date range
- **Status Filter**: Filter by appointment status
- **Type Filter**: Filter by appointment type
- **Department Filter**: Filter by department

### Saved Views
- **Custom Filters**: Save frequently used filters
- **Quick Access**: Quick access to common views
- **Share Views**: Share saved views with team

## Reporting & Analytics

### Appointment Reports
- **Daily Schedule**: Daily appointment schedule report
- **Provider Utilization**: Provider utilization reports
- **No-Show Analysis**: No-show rate analysis
- **Revenue Reports**: Revenue by appointment type
- **Wait Time Reports**: Patient wait time analysis

### Key Metrics
- **Appointment Volume**: Total appointments over time
- **No-Show Rate**: Percentage of no-shows
- **Cancellation Rate**: Percentage of cancellations
- **Average Wait Time**: Average patient wait time
- **Provider Utilization**: Percentage of provider time utilized
- **On-Time Performance**: Percentage of on-time appointments

## Future Enhancements

### Planned Features
- **AI Scheduling**: AI-powered appointment scheduling optimization
- **Predictive Analytics**: Predict no-shows and optimize scheduling
- **Video Conferencing**: Integrated video conferencing for telemedicine
- **Patient Self-Scheduling**: Patient portal for self-scheduling
- **Mobile App**: Mobile app for providers and patients
- **Voice Scheduling**: Voice-activated appointment scheduling
- **Smart Reminders**: Intelligent reminder timing based on patient behavior
- **Multi-location Support**: Enhanced multi-location scheduling

## Implementation Notes

### Technology Stack
- **Backend**: Spring Boot, JPA/Hibernate, PostgreSQL
- **Frontend**: React, TypeScript, Tailwind CSS
- **Calendar Library**: FullCalendar or similar
- **API**: RESTful API with JWT authentication
- **Database**: PostgreSQL with proper indexing

### Performance Considerations
- **Database Indexing**: Index on date, provider, patient fields
- **Caching**: Cache provider schedules and availability
- **Pagination**: All list endpoints support pagination
- **Optimized Queries**: Efficient queries for calendar views

### Testing
- **Unit Tests**: Test business logic
- **Integration Tests**: Test API endpoints
- **E2E Tests**: Test complete booking workflow
- **Performance Tests**: Test with large datasets

## Documentation

### API Documentation
- Swagger/OpenAPI documentation
- Request/response examples
- Error codes and handling

### User Documentation
- User guides for receptionists
- Provider scheduling guides
- Patient portal documentation

### Developer Documentation
- Architecture documentation
- Database schema documentation
- Code documentation
