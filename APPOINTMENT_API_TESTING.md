# Appointment API Testing Guide

## Prerequisites
1. Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
2. The API will be available at `http://localhost:8080/api`

## Authentication
First, obtain an access token by logging in:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

Save the `accessToken` from the response for subsequent requests.

## API Endpoints

### 1. Create Appointment
```bash
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -d '{
    "patientId": "123e4567-e89b-12d3-a456-426614174000",
    "doctorId": "223e4567-e89b-12d3-a456-426614174001",
    "appointmentType": "CONSULTATION",
    "appointmentDate": "2026-06-15",
    "startTime": "09:00",
    "endTime": "09:30",
    "durationMinutes": 30,
    "reason": "Regular checkup",
    "priority": "MEDIUM",
    "isVirtual": false
  }'
```

### 2. Get Appointment by ID
```bash
curl -X GET http://localhost:8080/api/appointments/APPOINTMENT_ID \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 3. Get Appointments by Patient ID
```bash
curl -X GET "http://localhost:8080/api/appointments/patient/PATIENT_ID" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 4. Get Appointments by Patient ID (Paginated)
```bash
curl -X GET "http://localhost:8080/api/appointments/patient/PATIENT_ID/paginated?page=0&size=10" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 5. Get Appointments by Doctor ID
```bash
curl -X GET "http://localhost:8080/api/appointments/doctor/DOCTOR_ID" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 6. Get Appointments by Status
```bash
curl -X GET "http://localhost:8080/api/appointments/status/SCHEDULED?page=0&size=10" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 7. Update Appointment
```bash
curl -X PUT http://localhost:8080/api/appointments/APPOINTMENT_ID \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -d '{
    "reason": "Updated reason",
    "priority": "HIGH"
  }'
```

### 8. Check-in Appointment (Changes status to IN_PROGRESS)
```bash
curl -X POST http://localhost:8080/api/appointments/APPOINTMENT_ID/check-in \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 9. Check-out Appointment (Changes status to COMPLETED)
```bash
curl -X POST http://localhost:8080/api/appointments/APPOINTMENT_ID/check-out \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 10. Cancel Appointment
```bash
curl -X POST http://localhost:8080/api/appointments/APPOINTMENT_ID/cancel \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 11. Delete Appointment
```bash
curl -X DELETE http://localhost:8080/api/appointments/APPOINTMENT_ID \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 12. Search Appointments
```bash
curl -X GET "http://localhost:8080/api/appointments/search?searchTerm=checkup&page=0&size=10" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

## Testing Status Transitions

### Test IN_PROGRESS Status
1. Create a new appointment (status will be SCHEDULED)
2. Check-in the appointment using the check-in endpoint
3. Verify the status changed to IN_PROGRESS
4. Check-out the appointment
5. Verify the status changed to COMPLETED

### Example Workflow
```bash
# 1. Create appointment
CREATE_RESPONSE=$(curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -d '{
    "patientId": "123e4567-e89b-12d3-a456-426614174000",
    "doctorId": "223e4567-e89b-12d3-a456-426614174001",
    "appointmentType": "CONSULTATION",
    "appointmentDate": "2026-06-15",
    "startTime": "09:00",
    "endTime": "09:30",
    "durationMinutes": 30,
    "reason": "Test appointment",
    "priority": "MEDIUM",
    "isVirtual": false
  }')

APPOINTMENT_ID=$(echo $CREATE_RESPONSE | jq -r '.data.id')

# 2. Check-in (status becomes IN_PROGRESS)
curl -X POST http://localhost:8080/api/appointments/$APPOINTMENT_ID/check-in \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"

# 3. Check-out (status becomes COMPLETED)
curl -X POST http://localhost:8080/api/appointments/$APPOINTMENT_ID/check-out \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

## Notes
- Replace `YOUR_ACCESS_TOKEN` with the actual token from login
- Replace `APPOINTMENT_ID`, `PATIENT_ID`, `DOCTOR_ID` with actual UUIDs
- The mock data in the frontend already includes an appointment with IN_PROGRESS status
- To use mock data in the frontend, ensure `.env` has `VITE_USE_MOCK_DATA=true`
