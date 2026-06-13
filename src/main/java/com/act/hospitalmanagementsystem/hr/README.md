# HR Module

## Overview

The HR module provides comprehensive Human Resources management capabilities for the Hospital Management System. It handles employee lifecycle management, payroll, attendance, performance evaluation, training, and compliance tracking for hospital staff.

---

## Features

### 1. Employee Management
- **Employee Registration** - Register new employees with complete profiles
- **Employee Profiles** - Manage employee information, qualifications, and certifications
- **Employment Contracts** - Manage contract types, terms, and renewals
- **Employee Transfers** - Handle department and role transfers
- **Employee Termination** - Manage resignations, retirements, and terminations
- **Employee Directory** - Searchable directory of all employees

### 2. Attendance Management
- **Clock In/Out** - Track employee attendance with biometric or manual entry
- **Shift Management** - Define and assign work shifts
- **Leave Management** - Request, approve, and manage leave requests
- **Overtime Tracking** - Track and approve overtime hours
- **Attendance Reports** - Generate attendance and absence reports
- **Late Arrival Tracking** - Monitor and manage late arrivals

### 3. Payroll Management
- **Salary Structure** - Define salary grades and scales
- **Payroll Processing** - Calculate and process monthly payroll
- **Deductions** - Manage tax, insurance, and other deductions
- **Allowances** - Manage housing, transport, and other allowances
- **Payslip Generation** - Generate and distribute payslips
- **Payroll Reports** - Generate payroll summary and tax reports

### 4. Performance Management
- **Goal Setting** - Set and track employee goals and objectives
- **Performance Reviews** - Conduct periodic performance evaluations
- **360-Degree Feedback** - Collect feedback from peers, subordinates, and supervisors
- **KPI Tracking** - Track key performance indicators
- **Performance Improvement Plans** - Create and monitor PIPs
- **Promotion Management** - Manage employee promotions and career progression

### 5. Training Management
- **Training Programs** - Create and manage training programs
- **Training Schedules** - Schedule and assign training sessions
- **Training Enrollment** - Enroll employees in training programs
- **Training Completion** - Track training completion and certificates
- **Training Budget** - Manage training budget and expenses
- **Training Reports** - Generate training effectiveness reports

### 6. Recruitment Management
- **Job Postings** - Create and publish job openings
- **Application Management** - Receive and manage job applications
- **Candidate Screening** - Screen and shortlist candidates
- **Interview Scheduling** - Schedule and conduct interviews
- **Offer Management** - Generate and manage job offers
- **Onboarding** - Manage new employee onboarding process

### 7. Compliance Management
- **License Tracking** - Track professional licenses and certifications
- **Background Checks** - Manage background check processes
- **Compliance Training** - Track mandatory compliance training
- **Audit Trail** - Complete audit trail for HR actions
- **Regulatory Reporting** - Generate required regulatory reports

### 8. Benefits Management
- **Health Insurance** - Manage health insurance enrollment
- **Retirement Plans** - Manage pension and retirement contributions
- **Leave Balances** - Track and manage leave balances
- **Employee Benefits** - Manage additional benefits and perks
- **Benefits Enrollment** - Handle benefits enrollment periods

---

## Architecture

### Components

```
hr/
├── controller/
│   ├── EmployeeController.java            # Employee management endpoints
│   ├── AttendanceController.java         # Attendance management endpoints
│   ├── PayrollController.java            # Payroll management endpoints
│   ├── PerformanceController.java        # Performance management endpoints
│   ├── TrainingController.java           # Training management endpoints
│   ├── RecruitmentController.java        # Recruitment management endpoints
│   ├── ComplianceController.java         # Compliance management endpoints
│   └── BenefitsController.java           # Benefits management endpoints
├── service/
│   ├── EmployeeService.java              # Employee management business logic
│   ├── AttendanceService.java            # Attendance management business logic
│   ├── PayrollService.java               # Payroll management business logic
│   ├── PerformanceService.java          # Performance management business logic
│   ├── TrainingService.java              # Training management business logic
│   ├── RecruitmentService.java          # Recruitment management business logic
│   ├── ComplianceService.java           # Compliance management business logic
│   └── BenefitsService.java              # Benefits management business logic
├── repository/
│   ├── EmployeeRepository.java           # Employee data access
│   ├── AttendanceRepository.java         # Attendance data access
│   ├── PayrollRepository.java            # Payroll data access
│   ├── PerformanceRepository.java        # Performance data access
│   ├── TrainingRepository.java           # Training data access
│   ├── RecruitmentRepository.java        # Recruitment data access
│   ├── ComplianceRepository.java         # Compliance data access
│   └── BenefitsRepository.java           # Benefits data access
├── entity/
│   ├── Employee.java                     # Employee entity
│   ├── Attendance.java                   # Attendance entity
│   ├── LeaveRequest.java                 # Leave request entity
│   ├── Shift.java                        # Shift entity
│   ├── Payroll.java                      # Payroll entity
│   ├── SalaryStructure.java              # Salary structure entity
│   ├── PerformanceReview.java            # Performance review entity
│   ├── Goal.java                         # Goal entity
│   ├── TrainingProgram.java              # Training program entity
│   ├── TrainingEnrollment.java          # Training enrollment entity
│   ├── JobPosting.java                   # Job posting entity
│   ├── Application.java                  # Job application entity
│   ├── License.java                      # Professional license entity
│   └── Benefit.java                     # Benefit entity
├── enums/
│   ├── EmploymentStatus.java             # Employment status enum
│   ├── EmployeeType.java                 # Employee type enum
│   ├── LeaveType.java                    # Leave type enum
│   ├── LeaveStatus.java                  # Leave status enum
│   ├── PayrollStatus.java                # Payroll status enum
│   ├── PerformanceRating.java            # Performance rating enum
│   ├── TrainingStatus.java               # Training status enum
│   ├── ApplicationStatus.java           # Application status enum
│   └── ComplianceStatus.java             # Compliance status enum
├── dto/
│   ├── EmployeeDTO.java                 # Employee DTO
│   ├── CreateEmployeeRequest.java        # Create employee request
│   ├── UpdateEmployeeRequest.java        # Update employee request
│   ├── AttendanceDTO.java               # Attendance DTO
│   ├── LeaveRequestDTO.java              # Leave request DTO
│   ├── PayrollDTO.java                   # Payroll DTO
│   ├── PerformanceReviewDTO.java        # Performance review DTO
│   ├── TrainingProgramDTO.java           # Training program DTO
│   ├── JobPostingDTO.java                # Job posting DTO
│   ├── ApplicationDTO.java              # Application DTO
│   └── LicenseDTO.java                   # License DTO
└── mapper/
    ├── EmployeeMapper.java              # Employee mapper
    ├── AttendanceMapper.java            # Attendance mapper
    ├── PayrollMapper.java               # Payroll mapper
    └── PerformanceMapper.java           # Performance mapper
```

### Data Model

#### Employee Entity
- `id` (UUID) - Primary key
- `employeeId` (String) - Unique employee ID
- `userId` (UUID) - Associated user account
- `firstName` (String) - First name
- `lastName` (String) - Last name
- `middleName` (String) - Middle name
- `email` (String) - Email address
- `phoneNumber` (String) - Phone number
- `dateOfBirth` (LocalDate) - Date of birth
- `gender` (String) - Gender
- `address` (String) - Residential address
- `department` (String) - Department
- `designation` (String) - Job title/designation
- `employeeType` (EmployeeType) - Type of employee (FULL_TIME, PART_TIME, CONTRACT, INTERN)
- `employmentStatus` (EmploymentStatus) - Current status (ACTIVE, ON_LEAVE, RESIGNED, TERMINATED, RETIRED)
- `joinDate` (LocalDate) - Date of joining
- `probationEndDate` (LocalDate) - Probation end date
- `contractEndDate` (LocalDate) - Contract end date
- `salaryStructure` (SalaryStructure) - Salary structure
- `reportingManager` (UUID) - ID of reporting manager
- `education` (String) - Educational qualifications (JSON)
- `experience` (String) - Work experience (JSON)
- `skills` (String) - Skills (JSON)
- `emergencyContact` (String) - Emergency contact details (JSON)
- `bankDetails` (String) - Bank account details (JSON)
- `documents` (String) - Employee documents (JSON)
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### Attendance Entity
- `id` (UUID) - Primary key
- `employee` (Employee) - Associated employee
- `date` (LocalDate) - Attendance date
- `checkInTime` (LocalDateTime) - Check-in time
- `checkOutTime` (LocalDateTime) - Check-out time
- `shift` (Shift) - Assigned shift
- `workHours` (Double) - Total work hours
- `overtimeHours` (Double) - Overtime hours
- `status` (String) - Attendance status (PRESENT, ABSENT, LATE, HALF_DAY)
- `lateReason` (String) - Reason for late arrival
- `location` (String) - Check-in location
- `device` (String) - Device used for check-in
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### LeaveRequest Entity
- `id` (UUID) - Primary key
- `employee` (Employee) - Employee requesting leave
- `leaveType` (LeaveType) - Type of leave (ANNUAL, SICK, MATERNITY, PATERNITY, UNPAID, COMPENSATORY)
- `startDate` (LocalDate) - Leave start date
- `endDate` (LocalDate) - Leave end date
- `totalDays` (Integer) - Total leave days
- `reason` (String) - Reason for leave
- `status` (LeaveStatus) - Current status (PENDING, APPROVED, REJECTED, CANCELLED)
- `appliedOn` (LocalDate) - Date when leave was applied
- `approvedBy` (UUID) - User who approved/rejected
- `approvedOn` (LocalDate) - Date of approval/rejection
- `rejectionReason` (String) - Reason for rejection
- `attachments` (String) - Supporting documents (JSON)
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### Payroll Entity
- `id` (UUID) - Primary key
- `employee` (Employee) - Associated employee
- `payrollPeriod` (String) - Payroll period (e.g., "2026-06")
- `basicSalary` (Double) - Basic salary
- `allowances` (Double) - Total allowances
- `deductions` (Double) - Total deductions
- `overtimePay` (Double) - Overtime payment
- `bonus` (Double) - Bonus amount
- `netSalary` (Double) - Net salary after deductions
- `taxDeduction` (Double) - Tax deduction
- `insuranceDeduction` (Double) - Insurance deduction
- `otherDeductions` (String) - Other deductions (JSON)
- `otherAllowances` (String) - Other allowances (JSON)
- `status` (PayrollStatus) - Current status (DRAFT, PROCESSED, PAID, CANCELLED)
- `processedOn` (LocalDate) - Date when payroll was processed
- `paidOn` (LocalDate) - Date when salary was paid
- `payslipUrl` (String) - URL to payslip document
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### PerformanceReview Entity
- `id` (UUID) - Primary key
- `employee` (Employee) - Employee being reviewed
- `reviewer` (Employee) - Person conducting the review
- `reviewPeriod` (String) - Review period (e.g., "Q2-2026")
- `reviewDate` (LocalDate) - Date of review
- `overallRating` (PerformanceRating) - Overall performance rating
- `goalsAchieved` (Integer) - Number of goals achieved
- `goalsTotal` (Integer) - Total goals set
- `strengths` (String) - Strengths (JSON)
- `weaknesses` (String) - Areas for improvement (JSON)
- `comments` (String) - Reviewer comments
- `employeeComments` (String) - Employee's response
- `actionItems` (String) - Action items (JSON)
- `nextReviewDate` (LocalDate) - Date of next review
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### TrainingProgram Entity
- `id` (UUID) - Primary key
- `programName` (String) - Training program name
- `programType` (String) - Type of training (INTERNAL, EXTERNAL, ONLINE, CLASSROOM)
- `description` (String) - Program description
- `category` (String) - Training category
- `startDate` (LocalDate) - Start date
- `endDate` (LocalDate) - End date
- `duration` (Integer) - Duration in hours
- `instructor` (String) - Instructor name
- `location` (String) - Training location
- `maxParticipants` (Integer) - Maximum participants
- `currentParticipants` (Integer) - Current enrolled participants
- `status` (TrainingStatus) - Current status (PLANNED, ONGOING, COMPLETED, CANCELLED)
- `budget` (Double) - Training budget
- `actualCost` (Double) - Actual cost incurred
- `objectives` (String) - Training objectives (JSON)
- `curriculum` (String) - Training curriculum (JSON)
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### JobPosting Entity
- `id` (UUID) - Primary key
- `jobTitle` (String) - Job title
- `department` (String) - Department
- `description` (String) - Job description
- `requirements` (String) - Job requirements (JSON)
- `responsibilities` (String) - Job responsibilities (JSON)
- `qualifications` (String) - Required qualifications (JSON)
- `experience` (String) - Required experience
- `salaryRange` (String) - Salary range
- `employmentType` (EmployeeType) - Type of employment
- `location` (String) - Job location
- `vacancies` (Integer) - Number of vacancies
- `postedDate` (LocalDate) - Date when job was posted
- `closingDate` (LocalDate) - Application closing date
- `status` (ApplicationStatus) - Current status (OPEN, CLOSED, ON_HOLD)
- `applicationsReceived` (Integer) - Number of applications received
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### License Entity
- `id` (UUID) - Primary key
- `employee` (Employee) - Associated employee
- `licenseType` (String) - Type of license (MEDICAL, NURSING, PHARMACY, etc.)
- `licenseNumber` (String) - License number
- `issuingAuthority` (String) - Issuing authority
- `issueDate` (LocalDate) - Date of issue
- `expiryDate` (LocalDate) - Expiry date
- `status` (ComplianceStatus) - Current status (ACTIVE, EXPIRED, SUSPENDED, REVOKED)
- `documentUrl` (String) - URL to license document
- `reminderDays` (Integer) - Days before expiry to send reminder
- `lastVerifiedDate` (LocalDate) - Last verification date
- `verifiedBy` (UUID) - User who verified the license
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

---

## API Endpoints

### Employee Management Endpoints

#### Get All Employees
```http
GET /api/hr/employees?page=0&size=20&department=ALL&status=ACTIVE
Authorization: Bearer {token}
```

#### Get Employee by ID
```http
GET /api/hr/employees/{id}
Authorization: Bearer {token}
```

#### Get Employee by Employee ID
```http
GET /api/hr/employees/employee-id/{employeeId}
Authorization: Bearer {token}
```

#### Create Employee
```http
POST /api/hr/employees
Authorization: Bearer {token}
Content-Type: application/json

{
  "employeeId": "EMP001",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@hospital.com",
  "phoneNumber": "+1234567890",
  "dateOfBirth": "1985-05-15",
  "gender": "MALE",
  "department": "CARDIOLOGY",
  "designation": "Senior Cardiologist",
  "employeeType": "FULL_TIME",
  "joinDate": "2020-01-15",
  "salaryStructureId": "uuid"
}
```

#### Update Employee
```http
PUT /api/hr/employees/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "designation": "Head of Cardiology",
  "department": "CARDIOLOGY"
}
```

#### Terminate Employee
```http
POST /api/hr/employees/{id}/terminate
Authorization: Bearer {token}
Content-Type: application/json

{
  "terminationDate": "2026-12-31",
  "terminationReason": "Resignation",
  "noticePeriod": 30
}
```

### Attendance Management Endpoints

#### Clock In
```http
POST /api/hr/attendance/clock-in
Authorization: Bearer {token}
Content-Type: application/json

{
  "location": "Main Hospital",
  "device": "Mobile App"
}
```

#### Clock Out
```http
POST /api/hr/attendance/clock-out
Authorization: Bearer {token}
```

#### Get Attendance Records
```http
GET /api/hr/attendance?employeeId={employeeId}&startDate=2026-06-01&endDate=2026-06-30
Authorization: Bearer {token}
```

#### Request Leave
```http
POST /api/hr/leave-requests
Authorization: Bearer {token}
Content-Type: application/json

{
  "leaveType": "ANNUAL",
  "startDate": "2026-07-01",
  "endDate": "2026-07-05",
  "reason": "Family vacation"
}
```

#### Approve Leave Request
```http
POST /api/hr/leave-requests/{id}/approve
Authorization: Bearer {managerToken}
Content-Type: application/json

{
  "approval": true,
  "comments": "Approved"
}
```

### Payroll Management Endpoints

#### Get Payroll for Period
```http
GET /api/hr/payroll?period=2026-06&department=ALL
Authorization: Bearer {token}
```

#### Process Payroll
```http
POST /api/hr/payroll/process
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "period": "2026-06",
  "department": "ALL"
}
```

#### Get Employee Payslip
```http
GET /api/hr/payroll/payslip/{payrollId}
Authorization: Bearer {token}
```

#### Generate Payroll Report
```http
POST /api/hr/payroll/reports
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "period": "2026-06",
  "reportType": "SUMMARY",
  "format": "PDF"
}
```

### Performance Management Endpoints

#### Get Performance Reviews
```http
GET /api/hr/performance?employeeId={employeeId}&period=2026-Q2
Authorization: Bearer {token}
```

#### Create Performance Review
```http
POST /api/hr/performance
Authorization: Bearer {managerToken}
Content-Type: application/json

{
  "employeeId": "uuid",
  "reviewPeriod": "2026-Q2",
  "overallRating": "EXCELLENT",
  "goalsAchieved": 8,
  "goalsTotal": 10,
  "comments": "Excellent performance"
}
```

#### Set Employee Goals
```http
POST /api/hr/performance/goals
Authorization: Bearer {managerToken}
Content-Type: application/json

{
  "employeeId": "uuid",
  "period": "2026-Q3",
  "goals": [
    {
      "title": "Complete certification",
      "description": "Obtain advanced cardiology certification",
      "targetDate": "2026-09-30",
      "weight": 30
    }
  ]
}
```

### Training Management Endpoints

#### Get Training Programs
```http
GET /api/hr/training?status=ONGOING&category=CLINICAL
Authorization: Bearer {token}
```

#### Create Training Program
```http
POST /api/hr/training
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "programName": "Advanced Cardiology Techniques",
  "programType": "EXTERNAL",
  "description": "Advanced training in interventional cardiology",
  "category": "CLINICAL",
  "startDate": "2026-07-01",
  "endDate": "2026-07-15",
  "duration": 40,
  "instructor": "Dr. Smith",
  "location": "Conference Room A",
  "maxParticipants": 20,
  "budget": 5000.00
}
```

#### Enroll in Training
```http
POST /api/hr/training/{programId}/enroll
Authorization: Bearer {token}
```

#### Get Training History
```http
GET /api/hr/training/history?employeeId={employeeId}
Authorization: Bearer {token}
```

### Recruitment Endpoints

#### Get Job Postings
```http
GET /api/hr/recruitment/jobs?status=OPEN&department=CARDIOLOGY
Authorization: Bearer {token}
```

#### Create Job Posting
```http
POST /api/hr/recruitment/jobs
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "jobTitle": "Cardiologist",
  "department": "CARDIOLOGY",
  "description": "We are looking for an experienced cardiologist",
  "requirements": ["MD degree", "Board certification"],
  "experience": "5+ years",
  "salaryRange": "$150,000 - $200,000",
  "employmentType": "FULL_TIME",
  "vacancies": 2,
  "closingDate": "2026-07-31"
}
```

#### Submit Application
```http
POST /api/hr/recruitment/jobs/{jobId}/apply
Authorization: Bearer {token}
Content-Type: multipart/form-data

resume: [file]
coverLetter: [file]
additionalInfo: {
  "experience": "5 years",
  "qualifications": "MD, Board Certified"
}
```

#### Get Applications for Job
```http
GET /api/hr/recruitment/jobs/{jobId}/applications
Authorization: Bearer {adminToken}
```

### Compliance Endpoints

#### Get Licenses
```http
GET /api/hr/compliance/licenses?employeeId={employeeId}&status=ACTIVE
Authorization: Bearer {token}
```

#### Add License
```http
POST /api/hr/compliance/licenses
Authorization: Bearer {token}
Content-Type: application/json

{
  "employeeId": "uuid",
  "licenseType": "MEDICAL",
  "licenseNumber": "LIC-12345",
  "issuingAuthority": "Medical Board",
  "issueDate": "2020-01-01",
  "expiryDate": "2030-01-01"
}
```

#### Get Expiring Licenses
```http
GET /api/hr/compliance/licenses/expiring?days=30
Authorization: Bearer {adminToken}
```

#### Verify License
```http
POST /api/hr/compliance/licenses/{licenseId}/verify
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "verified": true,
  "verificationNotes": "Verified with issuing authority"
}
```

---

## Testing Flow Scenarios

### Scenario 1: Employee Registration

**Steps:**
1. Login as HR manager
2. Create a new employee profile
3. Assign salary structure
4. Verify employee is created
5. Check employee directory

**Test Commands:**
```bash
# Login as HR manager
HR_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "hr_manager", "password": "password"}' \
  | jq -r '.data.accessToken')

# Create employee
curl -X POST http://localhost:8080/api/hr/employees \
  -H "Authorization: Bearer $HR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": "EMP001",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@hospital.com",
    "phoneNumber": "+1234567890",
    "dateOfBirth": "1985-05-15",
    "gender": "MALE",
    "department": "CARDIOLOGY",
    "designation": "Senior Cardiologist",
    "employeeType": "FULL_TIME",
    "joinDate": "2020-01-15"
  }'

# Expected: 200 OK with employee details
```

---

### Scenario 2: Attendance Tracking

**Steps:**
1. Login as employee
2. Clock in for the day
3. Perform work activities
4. Clock out at end of day
5. Verify attendance record

**Test Commands:**
```bash
# Login as employee
EMPLOYEE_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "john_doe", "password": "password"}' \
  | jq -r '.data.accessToken')

# Clock in
curl -X POST http://localhost:8080/api/hr/attendance/clock-in \
  -H "Authorization: Bearer $EMPLOYEE_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "location": "Main Hospital",
    "device": "Mobile App"
  }'

# Clock out
curl -X POST http://localhost:8080/api/hr/attendance/clock-out \
  -H "Authorization: Bearer $EMPLOYEE_TOKEN"

# Expected: 200 OK with attendance details
```

---

### Scenario 3: Leave Request and Approval

**Steps:**
1. Login as employee
2. Submit leave request
3. Login as manager
4. Approve leave request
5. Verify leave balance

**Test Commands:**
```bash
# Login as employee
EMPLOYEE_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "john_doe", "password": "password"}' \
  | jq -r '.data.accessToken')

# Submit leave request
curl -X POST http://localhost:8080/api/hr/leave-requests \
  -H "Authorization: Bearer $EMPLOYEE_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "leaveType": "ANNUAL",
    "startDate": "2026-07-01",
    "endDate": "2026-07-05",
    "reason": "Family vacation"
  }'

# Login as manager
MANAGER_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "manager", "password": "password"}' \
  | jq -r '.data.accessToken')

# Approve leave
curl -X POST http://localhost:8080/api/hr/leave-requests/{id}/approve \
  -H "Authorization: Bearer $MANAGER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "approval": true,
    "comments": "Approved"
  }'

# Expected: 200 OK
```

---

### Scenario 4: Payroll Processing

**Steps:**
1. Login as HR admin
2. Process payroll for the month
3. Verify payroll calculations
4. Generate payslips
5. Send payslips to employees

**Test Commands:**
```bash
# Login as HR admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Process payroll
curl -X POST http://localhost:8080/api/hr/payroll/process \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "period": "2026-06",
    "department": "ALL"
  }'

# Expected: 200 OK with payroll summary
```

---

### Scenario 5: Training Program Management

**Steps:**
1. Login as HR admin
2. Create a training program
3. Enroll employees
4. Track training progress
5. Complete training and issue certificates

**Test Commands:**
```bash
# Login as HR admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Create training program
curl -X POST http://localhost:8080/api/hr/training \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "programName": "Advanced Cardiology Techniques",
    "programType": "EXTERNAL",
    "description": "Advanced training in interventional cardiology",
    "category": "CLINICAL",
    "startDate": "2026-07-01",
    "endDate": "2026-07-15",
    "duration": 40,
    "instructor": "Dr. Smith",
    "location": "Conference Room A",
    "maxParticipants": 20,
    "budget": 5000.00
  }'

# Expected: 200 OK with training program details
```

---

## Security Considerations

### Access Control
- Employee management requires HR_WRITE permission
- Payroll processing requires HR_ADMIN permission
- Performance reviews require MANAGER permission
- Sensitive employee data is restricted to authorized users

### Data Privacy
- Employee personal data is protected
- Salary information is restricted
- Performance reviews are confidential
- Compliance with data protection regulations

### Audit Trail
- All HR actions are logged
- Payroll changes are audited
- Access to sensitive data is tracked
- Compliance actions are recorded

---

## Dependencies

### Internal Dependencies
- `auth` - For user authentication and authorization
- `common` - For shared utilities and DTOs
- `notification` - For sending notifications (when implemented)
- `admin` - For system configuration

### External Dependencies
- Spring Batch - For payroll processing
- Apache POI - For Excel report generation
- iText - For PDF report generation
- Quartz Scheduler - For scheduled HR tasks
- Biometric SDK - For attendance integration (optional)

---

## Future Enhancements

### Planned Features
- Self-service HR portal for employees
- Mobile app for attendance tracking
- AI-powered recruitment screening
- Predictive analytics for attrition
- Integration with payroll systems
- Employee engagement surveys
- Succession planning tools
- Skills gap analysis

### Performance Improvements
- Implement caching for frequently accessed employee data
- Optimize payroll processing with batch operations
- Use database indexes for faster queries
- Implement async processing for large reports

---

## Notes

- This module is critical for hospital operations and staff management
- Ensure compliance with labor laws and regulations
- Implement proper data validation for payroll calculations
- Consider integrating with external payroll systems
- Regularly review and update salary structures
- Maintain proper audit trails for compliance
