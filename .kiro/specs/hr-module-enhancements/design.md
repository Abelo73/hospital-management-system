# Design Document — HR Module Enhancements

## Overview

This document describes the technical design for the HR Module Enhancements spec. It covers new and modified database tables (Flyway migrations V10+), new backend entities/services/controllers, new and updated frontend pages and components, the configurability architecture, and API contracts.

The design follows existing conventions:
- All API responses wrapped in `BaseResponseDTO`
- Soft deletes via `deleted = false` flag
- UUID primary keys via `gen_random_uuid()`
- Audit fields: `created_at`, `updated_at`, `created_by`, `updated_by`, `version`
- Frontend: React + TypeScript, shadcn/ui components, zinc colour palette, table + dialog pattern

---

## Architecture

### Backend Stack
- Spring Boot 3, Spring Data JPA, PostgreSQL
- Flyway for schema migrations
- MapStruct for DTO mapping
- Spring Security with JWT

### Frontend Stack
- React 18 + TypeScript, Vite
- TanStack Query for server state
- shadcn/ui + Tailwind CSS (zinc palette)
- React Router v6

---

## Database Design (Flyway Migrations)

### V10__hr_org_structure.sql — Branches, Departments, Positions, Grades

```sql
-- hr_branches
CREATE TABLE IF NOT EXISTS hr_branches (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    address TEXT,
    city VARCHAR(100),
    country VARCHAR(100),
    phone VARCHAR(30),
    email VARCHAR(255),
    branch_type VARCHAR(50) NOT NULL DEFAULT 'HOSPITAL',
    parent_branch_id UUID REFERENCES hr_branches(id),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_salary_grades
CREATE TABLE IF NOT EXISTS hr_salary_grades (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    min_salary DECIMAL(15,2) NOT NULL,
    max_salary DECIMAL(15,2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_positions
CREATE TABLE IF NOT EXISTS hr_positions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(200) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    department_id UUID REFERENCES hr_departments(id),
    grade_id UUID REFERENCES hr_salary_grades(id),
    min_salary DECIMAL(15,2),
    max_salary DECIMAL(15,2),
    responsibilities TEXT,
    required_skills TEXT,
    required_qualifications TEXT,
    reporting_position_id UUID REFERENCES hr_positions(id),
    is_active BOOLEAN DEFAULT TRUE,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);
```

### V11__hr_shifts_attendance_enhanced.sql

```sql
-- hr_shifts
CREATE TABLE IF NOT EXISTS hr_shifts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_overnight BOOLEAN DEFAULT FALSE,
    break_duration_minutes INTEGER DEFAULT 0,
    shift_type VARCHAR(50) NOT NULL,
    grace_period_minutes INTEGER DEFAULT 0,
    max_overtime_hours DECIMAL(5,2) DEFAULT 4.0,
    applicable_days JSONB,
    branch_id UUID REFERENCES hr_branches(id),
    is_active BOOLEAN DEFAULT TRUE,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_shift_assignments
CREATE TABLE IF NOT EXISTS hr_shift_assignments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL REFERENCES hr_employees(id),
    shift_id UUID NOT NULL REFERENCES hr_shifts(id),
    effective_date DATE NOT NULL,
    end_date DATE,
    assigned_by UUID,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- Add columns to hr_attendance
ALTER TABLE hr_attendance
    ADD COLUMN IF NOT EXISTS shift_id UUID REFERENCES hr_shifts(id),
    ADD COLUMN IF NOT EXISTS check_in_method VARCHAR(30) DEFAULT 'MANUAL',
    ADD COLUMN IF NOT EXISTS check_in_latitude DECIMAL(10,7),
    ADD COLUMN IF NOT EXISTS check_in_longitude DECIMAL(10,7),
    ADD COLUMN IF NOT EXISTS break_start_time TIME,
    ADD COLUMN IF NOT EXISTS break_end_time TIME,
    ADD COLUMN IF NOT EXISTS late_minutes INTEGER DEFAULT 0,
    ADD COLUMN IF NOT EXISTS early_departure_minutes INTEGER DEFAULT 0;
```

### V12__hr_leave_enhanced.sql

```sql
-- hr_leave_types (configurable)
CREATE TABLE IF NOT EXISTS hr_leave_types (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    annual_days INTEGER NOT NULL,
    is_paid BOOLEAN DEFAULT TRUE,
    requires_approval BOOLEAN DEFAULT TRUE,
    requires_attachment BOOLEAN DEFAULT FALSE,
    is_gender_specific BOOLEAN DEFAULT FALSE,
    applicable_gender VARCHAR(10),
    max_carryover_days INTEGER DEFAULT 0,
    min_service_months_required INTEGER DEFAULT 0,
    accrual_frequency VARCHAR(20) DEFAULT 'ANNUAL',
    is_active BOOLEAN DEFAULT TRUE,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_leave_balances
CREATE TABLE IF NOT EXISTS hr_leave_balances (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL REFERENCES hr_employees(id),
    leave_type_id UUID NOT NULL REFERENCES hr_leave_types(id),
    leave_cycle_year INTEGER NOT NULL,
    entitled_days INTEGER NOT NULL,
    used_days INTEGER DEFAULT 0,
    carried_forward_days INTEGER DEFAULT 0,
    remaining_days INTEGER GENERATED ALWAYS AS (entitled_days + carried_forward_days - used_days) STORED,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0,
    UNIQUE(employee_id, leave_type_id, leave_cycle_year)
);

-- hr_holiday_calendars
CREATE TABLE IF NOT EXISTS hr_holiday_calendars (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200) NOT NULL,
    calendar_year INTEGER NOT NULL,
    branch_id UUID REFERENCES hr_branches(id),
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS hr_holiday_entries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    calendar_id UUID NOT NULL REFERENCES hr_holiday_calendars(id) ON DELETE CASCADE,
    holiday_date DATE NOT NULL,
    name VARCHAR(200) NOT NULL,
    holiday_type VARCHAR(30) NOT NULL,
    is_half_day BOOLEAN DEFAULT FALSE
);
```

### V13__hr_payroll_components.sql

```sql
-- hr_salary_components
CREATE TABLE IF NOT EXISTS hr_salary_components (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    component_type VARCHAR(20) NOT NULL,  -- EARNING | DEDUCTION
    calculation_method VARCHAR(30) NOT NULL,  -- FIXED | PERCENTAGE_OF_BASIC | FORMULA
    value DECIMAL(10,4) NOT NULL,
    is_taxable BOOLEAN DEFAULT TRUE,
    is_mandatory BOOLEAN DEFAULT TRUE,
    applies_to_employee_types JSONB,
    is_active BOOLEAN DEFAULT TRUE,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_tax_brackets
CREATE TABLE IF NOT EXISTS hr_tax_brackets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    min_income DECIMAL(15,2) NOT NULL,
    max_income DECIMAL(15,2),
    rate_percentage DECIMAL(5,2) NOT NULL,
    currency VARCHAR(10) DEFAULT 'USD',
    effective_date DATE NOT NULL,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_payroll_line_items
CREATE TABLE IF NOT EXISTS hr_payroll_line_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    payroll_id UUID NOT NULL REFERENCES hr_payroll(id) ON DELETE CASCADE,
    component_id UUID REFERENCES hr_salary_components(id),
    component_name VARCHAR(100) NOT NULL,
    component_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    is_taxable BOOLEAN DEFAULT TRUE
);

-- hr_employee_loans
CREATE TABLE IF NOT EXISTS hr_employee_loans (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL REFERENCES hr_employees(id),
    loan_type VARCHAR(50) NOT NULL,
    principal_amount DECIMAL(15,2) NOT NULL,
    approved_amount DECIMAL(15,2),
    interest_rate DECIMAL(5,2) DEFAULT 0,
    repayment_months INTEGER NOT NULL,
    monthly_installment DECIMAL(15,2),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    start_date DATE,
    remaining_balance DECIMAL(15,2),
    notes TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);
```

### V14__hr_recruitment_onboarding.sql

```sql
-- hr_job_vacancies
CREATE TABLE IF NOT EXISTS hr_job_vacancies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(200) NOT NULL,
    position_id UUID REFERENCES hr_positions(id),
    department_id UUID REFERENCES hr_departments(id),
    branch_id UUID REFERENCES hr_branches(id),
    number_of_positions INTEGER NOT NULL DEFAULT 1,
    description TEXT,
    requirements TEXT,
    responsibilities TEXT,
    salary_range VARCHAR(100),
    employment_type VARCHAR(50),
    posting_date DATE,
    closing_date DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
    published_by UUID,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_applicants
CREATE TABLE IF NOT EXISTS hr_applicants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    vacancy_id UUID NOT NULL REFERENCES hr_job_vacancies(id),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(30),
    nationality VARCHAR(100),
    cv_url VARCHAR(500),
    cover_letter_url VARCHAR(500),
    application_date DATE NOT NULL,
    current_stage VARCHAR(50) NOT NULL DEFAULT 'REGISTERED',
    overall_status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    hired_employee_id UUID REFERENCES hr_employees(id),
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_applicant_stage_history
CREATE TABLE IF NOT EXISTS hr_applicant_stage_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    applicant_id UUID NOT NULL REFERENCES hr_applicants(id) ON DELETE CASCADE,
    from_stage VARCHAR(50),
    to_stage VARCHAR(50) NOT NULL,
    changed_by UUID,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes TEXT
);

-- hr_interviews
CREATE TABLE IF NOT EXISTS hr_interviews (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    applicant_id UUID NOT NULL REFERENCES hr_applicants(id),
    interviewer_employee_id UUID REFERENCES hr_employees(id),
    scheduled_date_time TIMESTAMP NOT NULL,
    location VARCHAR(200),
    interview_type VARCHAR(20) NOT NULL DEFAULT 'IN_PERSON',
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    score INTEGER,
    feedback TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_offer_letters
CREATE TABLE IF NOT EXISTS hr_offer_letters (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    applicant_id UUID NOT NULL REFERENCES hr_applicants(id),
    vacancy_id UUID NOT NULL REFERENCES hr_job_vacancies(id),
    proposed_salary DECIMAL(15,2),
    proposed_start_date DATE,
    offer_expiry_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    offer_document_url VARCHAR(500),
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_onboarding_templates
CREATE TABLE IF NOT EXISTS hr_onboarding_templates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200) NOT NULL,
    description TEXT,
    department_id UUID REFERENCES hr_departments(id),
    employee_type VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS hr_onboarding_template_tasks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    template_id UUID NOT NULL REFERENCES hr_onboarding_templates(id) ON DELETE CASCADE,
    task_name VARCHAR(200) NOT NULL,
    description TEXT,
    assigned_to_role VARCHAR(100),
    due_days_from_hire INTEGER NOT NULL DEFAULT 1,
    is_mandatory BOOLEAN DEFAULT TRUE,
    task_category VARCHAR(50) NOT NULL
);

-- hr_onboarding_checklists
CREATE TABLE IF NOT EXISTS hr_onboarding_checklists (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL REFERENCES hr_employees(id),
    template_id UUID REFERENCES hr_onboarding_templates(id),
    status VARCHAR(20) NOT NULL DEFAULT 'IN_PROGRESS',
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS hr_onboarding_checklist_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    checklist_id UUID NOT NULL REFERENCES hr_onboarding_checklists(id) ON DELETE CASCADE,
    task_name VARCHAR(200) NOT NULL,
    description TEXT,
    assigned_to_role VARCHAR(100),
    due_date DATE,
    is_mandatory BOOLEAN DEFAULT TRUE,
    task_category VARCHAR(50) NOT NULL,
    is_complete BOOLEAN DEFAULT FALSE,
    completed_by UUID,
    completed_at TIMESTAMP,
    notes TEXT
);
```

### V15__hr_assets_contracts_workflow.sql

```sql
-- hr_employee_assets
CREATE TABLE IF NOT EXISTS hr_employee_assets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    asset_type VARCHAR(50) NOT NULL,
    asset_code VARCHAR(100),
    employee_id UUID NOT NULL REFERENCES hr_employees(id),
    assigned_date DATE NOT NULL,
    return_date DATE,
    condition VARCHAR(20) NOT NULL DEFAULT 'GOOD',
    status VARCHAR(20) NOT NULL DEFAULT 'ASSIGNED',
    notes TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_employment_contracts
CREATE TABLE IF NOT EXISTS hr_employment_contracts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL REFERENCES hr_employees(id),
    contract_type VARCHAR(30) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    notice_period_days INTEGER DEFAULT 30,
    file_url VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    renewal_reminder_days INTEGER DEFAULT 30,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

-- hr_workflow_configs
CREATE TABLE IF NOT EXISTS hr_workflow_configs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workflow_type VARCHAR(50) NOT NULL,
    branch_id UUID REFERENCES hr_branches(id),
    department_id UUID REFERENCES hr_departments(id),
    is_active BOOLEAN DEFAULT TRUE,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS hr_workflow_steps (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    config_id UUID NOT NULL REFERENCES hr_workflow_configs(id) ON DELETE CASCADE,
    step_order INTEGER NOT NULL,
    approver_role VARCHAR(100) NOT NULL,
    escalation_hours INTEGER DEFAULT 48,
    is_parallel BOOLEAN DEFAULT FALSE
);

-- hr_workflow_instances
CREATE TABLE IF NOT EXISTS hr_workflow_instances (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workflow_type VARCHAR(50) NOT NULL,
    entity_id UUID NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    current_step INTEGER DEFAULT 1,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    config_snapshot JSONB,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, version INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS hr_workflow_approvals (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    instance_id UUID NOT NULL REFERENCES hr_workflow_instances(id) ON DELETE CASCADE,
    step_order INTEGER NOT NULL,
    approver_id UUID,
    approver_role VARCHAR(100),
    action VARCHAR(20),
    comments TEXT,
    acted_at TIMESTAMP
);

-- hr_roster_schedules
CREATE TABLE IF NOT EXISTS hr_roster_schedules (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    roster_name VARCHAR(200) NOT NULL,
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    department_id UUID REFERENCES hr_departments(id),
    branch_id UUID REFERENCES hr_branches(id),
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS hr_roster_entries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    roster_id UUID NOT NULL REFERENCES hr_roster_schedules(id) ON DELETE CASCADE,
    employee_id UUID NOT NULL REFERENCES hr_employees(id),
    entry_date DATE NOT NULL,
    shift_id UUID NOT NULL REFERENCES hr_shifts(id),
    role_override VARCHAR(100),
    is_conflict BOOLEAN DEFAULT FALSE
);
```

---

## Backend Component Design

### New Services (hr/service/)

| Service | Responsibility |
|---|---|
| `BranchService` | Branch/hospital CRUD, hierarchy, status management |
| `DepartmentService` | Department CRUD, head assignment, employee count |
| `PositionService` | Position/grade CRUD, salary range validation |
| `ShiftService` | Shift definitions, shift assignments, conflict detection |
| `RosterService` | Roster scheduling, conflict detection, shift swap workflow |
| `HolidayCalendarService` | Holiday calendar CRUD, year copy, working-day calculation |
| `LeaveTypeService` | Configurable leave type CRUD |
| `LeaveBalanceService` | Balance initialisation, deduction, restoration, year-end carryover |
| `SalaryComponentService` | Component + tax bracket CRUD |
| `PayrollEnhancedService` | Component-aware payroll generation, payslip creation, line items |
| `LoanService` | Loan lifecycle, installment scheduling, payroll deduction |
| `VacancyService` | Job vacancy CRUD, publish/close |
| `ApplicantService` | Applicant pipeline, stage transitions, interview records, offers |
| `OnboardingService` | Template management, checklist generation, task completion |
| `AssetService` | Asset assignment, return, exit checklist |
| `ContractService` | Contract CRUD, renewal, expiry reminders |
| `WorkflowService` | Workflow config CRUD, instance creation, approval routing |
| `HrDashboardService` | KPI aggregation, chart data endpoints |
| `HrAnalyticsService` | Headcount, attrition, recruitment, payroll analytics |

### New Controllers (hr/controller/)

| Controller | Base Path |
|---|---|
| `BranchController` | `POST/GET/PUT/DELETE /api/hr/branches` |
| `DepartmentController` | `POST/GET/PUT/DELETE /api/hr/departments` |
| `PositionController` | `POST/GET/PUT/DELETE /api/hr/positions` |
| `SalaryGradeController` | `POST/GET/PUT/DELETE /api/hr/salary-grades` |
| `ShiftController` | `POST/GET/PUT/DELETE /api/hr/shifts`, `POST /api/hr/shifts/assign` |
| `RosterController` | `POST/GET/PUT /api/hr/rosters`, `POST /api/hr/rosters/{id}/publish` |
| `HolidayCalendarController` | `POST/GET/PUT /api/hr/holiday-calendars` |
| `LeaveTypeController` | `POST/GET/PUT /api/hr/leave-types` |
| `LeaveBalanceController` | `GET /api/hr/leave-balances` |
| `SalaryComponentController` | `POST/GET/PUT /api/hr/salary-components` |
| `TaxBracketController` | `POST/GET/PUT /api/hr/tax-brackets` |
| `PayrollEnhancedController` | `POST /api/hr/payroll/generate`, `GET /api/hr/payroll/{id}/payslip` |
| `LoanController` | `POST/GET/PUT /api/hr/loans`, `POST /api/hr/loans/{id}/approve` |
| `VacancyController` | `POST/GET/PUT /api/hr/vacancies`, `POST /api/hr/vacancies/{id}/publish` |
| `ApplicantController` | `POST/GET/PUT /api/hr/applicants`, `POST /api/hr/applicants/{id}/stage` |
| `InterviewController` | `POST/GET/PUT /api/hr/interviews` |
| `OfferLetterController` | `POST/GET/PUT /api/hr/offers` |
| `OnboardingController` | `POST/GET /api/hr/onboarding/templates`, `GET /api/hr/onboarding/checklists` |
| `AssetController` | `POST/GET/PUT /api/hr/assets` |
| `ContractController` | `POST/GET/PUT /api/hr/contracts` |
| `WorkflowConfigController` | `POST/GET/PUT /api/hr/workflow-configs` |
| `HrDashboardController` | `GET /api/hr/dashboard/kpis`, `GET /api/hr/dashboard/charts/{type}` |
| `HrAnalyticsController` | `GET /api/hr/analytics/{metric}` |

### Key API Contracts

#### HR Dashboard KPIs
```
GET /api/hr/dashboard/kpis
Response: {
  totalEmployees, activeEmployees, inactiveEmployees,
  newHiresThisMonth, onLeaveToday, onDutyToday, onNightShiftToday,
  permanentCount, contractCount,
  upcomingBirthdays: [{employeeId, name, date}],
  upcomingAnniversaries: [{employeeId, name, years, date}],
  openVacancies, pendingApprovals,
  monthlyPayrollTotal, attendanceSummary: {present, absent, late}
}
```

#### Leave Balance
```
GET /api/hr/leave-balances?employeeId={id}&year={year}
Response: [{leaveTypeId, leaveTypeName, entitledDays, usedDays, 
            carriedForwardDays, remainingDays}]
```

#### Payroll Generate
```
POST /api/hr/payroll/generate
Body: {payPeriodStart, payPeriodEnd, departmentId?, branchId?}
Response: {generatedCount, totalGrossPay, totalNetPay, payrollIds:[]}
```

#### Applicant Stage Transition
```
POST /api/hr/applicants/{id}/stage
Body: {toStage, notes}
Response: ApplicantDTO with updated currentStage + stageHistory
```

#### Workflow Approval Action
```
POST /api/hr/workflow/{instanceId}/action
Body: {action: "APPROVE"|"REJECT", comments}
Response: WorkflowInstanceDTO with updated status + currentStep
```

---

## Frontend Component Design

### New Pages (hms-ui/src/pages/hr/)

| Page | Route | Description |
|---|---|---|
| `HrDashboardPage` | `/hr/dashboard` | KPI cards + recharts charts |
| `BranchesPage` | `/hr/branches` | Branch hierarchy tree + CRUD dialogs |
| `DepartmentsPage` | `/hr/departments` | Department table + form dialog |
| `PositionsPage` | `/hr/positions` | Position table + grade assignment |
| `ShiftsPage` | `/hr/shifts` | Shift definitions table + form |
| `RosterPage` | `/hr/roster` | Weekly/monthly calendar grid |
| `HolidayCalendarPage` | `/hr/holidays` | Calendar year view + holiday entries |
| `LeaveTypesPage` | `/hr/leave-types` | Configurable leave types table + form |
| `LeaveBalancePage` | `/hr/leave-balances` | Per-employee leave balance view |
| `SalaryComponentsPage` | `/hr/salary-components` | Components + tax brackets config |
| `VacanciesPage` | `/hr/vacancies` | Job vacancy board |
| `ApplicantsPage` | `/hr/vacancies/:id/applicants` | Kanban pipeline by stage |
| `OnboardingPage` | `/hr/onboarding` | Checklist management |
| `AssetsPage` | `/hr/assets` | Asset assignment table |
| `ContractsPage` | `/hr/contracts` | Contract list + expiry alerts |
| `LoansPage` | `/hr/loans` | Loan management |
| `AnalyticsPage` | `/hr/analytics` | Charts and metric explorer |

### New Shared Components (hms-ui/src/pages/hr/components/)

- `BranchFormDialog` — create/edit branch
- `DepartmentFormDialog` — create/edit department
- `PositionFormDialog` — create/edit position + grade picker
- `ShiftFormDialog` — create/edit shift with time pickers
- `RosterGrid` — weekly/monthly drag-assign grid
- `LeaveTypeFormDialog` — configurable leave type form
- `LeaveBalanceCard` — per-type balance display with progress bar
- `PayrollGenerateDialog` — period + scope selection
- `PayslipViewer` — line-item breakdown modal
- `ApplicantKanban` — drag-and-drop stage pipeline
- `InterviewFormDialog` — schedule + feedback form
- `OfferLetterDialog` — offer details + status actions
- `OnboardingChecklistPanel` — task list with completion toggles
- `AssetFormDialog` — assign/return asset
- `ContractFormDialog` — contract CRUD + renewal action
- `LoanFormDialog` — loan application + approval
- `HrKpiCard` — reusable metric card with trend indicator
- `HrChart` — recharts wrapper for bar/pie/line charts

### hrService.ts Additions

```typescript
// Branches
getBranches(), createBranch(), updateBranch()
// Departments  
getDepartments(), createDepartment(), updateDepartment()
// Positions + Grades
getPositions(), createPosition(), getSalaryGrades(), createSalaryGrade()
// Shifts
getShifts(), createShift(), assignShift()
// Rosters
getRosters(), createRoster(), publishRoster(), getRosterEntries()
// Holiday Calendars
getHolidayCalendars(), createHolidayCalendar(), addHolidayEntry()
// Leave Types + Balances
getLeaveTypes(), createLeaveType(), getLeaveBalances()
// Salary Components + Tax
getSalaryComponents(), createSalaryComponent(), getTaxBrackets(), createTaxBracket()
// Enhanced Payroll
generatePayroll(), getPayslip()
// Loans
getLoans(), createLoan(), approveLoan()
// Vacancies + Applicants
getVacancies(), createVacancy(), publishVacancy()
getApplicants(), createApplicant(), transitionApplicantStage()
// Interviews + Offers
createInterview(), updateInterview(), createOffer(), updateOffer()
// Onboarding
getOnboardingTemplates(), getOnboardingChecklists(), completeChecklistItem()
// Assets
getAssets(), assignAsset(), returnAsset()
// Contracts
getContracts(), createContract(), renewContract()
// Loans
getLoans(), createLoan(), approveLoan()
// Dashboard + Analytics
getHrDashboardKpis(), getHrChartData(type), getHrAnalytics(metric)
```

---

## Configurability Architecture

All HR policies live in the database and are managed through admin settings screens:

| Config Area | Table | Admin Screen |
|---|---|---|
| Leave types & days | `hr_leave_types` | Leave Types page |
| Shift definitions | `hr_shifts` | Shifts page |
| Holiday calendars | `hr_holiday_calendars` | Holidays page |
| Salary grades | `hr_salary_grades` | Salary Grades page |
| Salary components | `hr_salary_components` | Salary Components page |
| Tax brackets | `hr_tax_brackets` | Tax Brackets page |
| Approval workflows | `hr_workflow_configs` + `hr_workflow_steps` | Workflow Config page |
| Onboarding templates | `hr_onboarding_templates` | Onboarding Templates page |

No leave types, shift names, tax rates, or approval chains are hardcoded in Java or TypeScript.

---

## Employee Profile Enhancement

The existing `hr_employees` table will be extended via `ALTER TABLE` in V10:

```sql
ALTER TABLE hr_employees
  ADD COLUMN IF NOT EXISTS photo_url VARCHAR(500),
  ADD COLUMN IF NOT EXISTS marital_status VARCHAR(30),
  ADD COLUMN IF NOT EXISTS nationality VARCHAR(100),
  ADD COLUMN IF NOT EXISTS religion VARCHAR(100),
  ADD COLUMN IF NOT EXISTS blood_group VARCHAR(10),
  ADD COLUMN IF NOT EXISTS disability_flag BOOLEAN DEFAULT FALSE,
  ADD COLUMN IF NOT EXISTS work_location VARCHAR(100),
  ADD COLUMN IF NOT EXISTS branch_id UUID REFERENCES hr_branches(id),
  ADD COLUMN IF NOT EXISTS shift_id UUID REFERENCES hr_shifts(id),
  ADD COLUMN IF NOT EXISTS supervisor_employee_id UUID REFERENCES hr_employees(id),
  ADD COLUMN IF NOT EXISTS employment_category VARCHAR(30),
  ADD COLUMN IF NOT EXISTS position_id UUID REFERENCES hr_positions(id),
  ADD COLUMN IF NOT EXISTS passport_number VARCHAR(50),
  ADD COLUMN IF NOT EXISTS national_id VARCHAR(50),
  ADD COLUMN IF NOT EXISTS driver_licence_number VARCHAR(50),
  ADD COLUMN IF NOT EXISTS bank_account_holder VARCHAR(100),
  ADD COLUMN IF NOT EXISTS bank_branch VARCHAR(100),
  ADD COLUMN IF NOT EXISTS housing_allowance DECIMAL(15,2),
  ADD COLUMN IF NOT EXISTS transport_allowance DECIMAL(15,2),
  ADD COLUMN IF NOT EXISTS medical_allowance DECIMAL(15,2),
  ADD COLUMN IF NOT EXISTS meal_allowance DECIMAL(15,2),
  ADD COLUMN IF NOT EXISTS tax_group_id UUID;
```

### Employee Documents Table
```sql
CREATE TABLE IF NOT EXISTS hr_employee_documents (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  employee_id UUID NOT NULL REFERENCES hr_employees(id),
  document_type VARCHAR(50) NOT NULL,
  file_url VARCHAR(500) NOT NULL,
  file_name VARCHAR(200),
  upload_date DATE NOT NULL DEFAULT CURRENT_DATE,
  expiry_date DATE,
  notes TEXT,
  deleted BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by UUID, updated_by UUID, version INTEGER DEFAULT 0
);
```

### Employee History Tables
```sql
-- Transfer history
CREATE TABLE IF NOT EXISTS hr_employee_transfers (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  employee_id UUID NOT NULL REFERENCES hr_employees(id),
  from_branch_id UUID, to_branch_id UUID,
  from_department_id UUID, to_department_id UUID,
  effective_date DATE NOT NULL,
  reason TEXT, approved_by UUID,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by UUID
);

-- Promotion history
CREATE TABLE IF NOT EXISTS hr_employee_promotions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  employee_id UUID NOT NULL REFERENCES hr_employees(id),
  from_position_id UUID, to_position_id UUID,
  old_salary DECIMAL(15,2), new_salary DECIMAL(15,2),
  effective_date DATE NOT NULL,
  justification TEXT, approved_by UUID,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by UUID
);
```

---

## Security Design

### New Permissions
```
HR_PAYROLL_READ, HR_PAYROLL_WRITE
HR_RECRUITMENT_READ, HR_RECRUITMENT_WRITE  
HR_REPORTS_READ
HR_SETTINGS_WRITE
HR_COMPLIANCE_READ, HR_COMPLIANCE_WRITE
```

All new controllers use `@PreAuthorize` annotations consistent with the existing auth pattern. Branch-scoped data filtering is applied at the service layer by checking the authenticated user's assigned branches.

---

## Correctness Properties

### Property 1: Leave Balance Integrity
For any employee and leave type in a given year:
`remaining_days = entitled_days + carried_forward_days - used_days`
This must hold after every leave approval, rejection, and cancellation.

**Validates: Requirement 10**

### Property 2: Payroll Net Pay Accuracy
For any generated payroll record:
`net_pay = sum(EARNING line items) - sum(DEDUCTION line items)`
This must hold regardless of the number of salary components.

**Validates: Requirement 12**

### Property 3: Shift Assignment Non-Overlap
For any employee, no two active shift assignments may have overlapping effective date ranges.

**Validates: Requirement 6**

### Property 4: Workflow Step Monotonicity
A workflow instance's `current_step` must only ever increase; it may never go backwards once an approval is recorded.

**Validates: Requirement 25**
