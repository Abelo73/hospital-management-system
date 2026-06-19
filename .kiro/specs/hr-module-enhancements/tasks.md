# Implementation Tasks — HR Module Enhancements

## Phase 1 — Organisational Structure

- [x] 1. Create Flyway migration V10__hr_org_structure.sql
  - [x] 1.1 Add `hr_branches` table with hierarchy support
  - [x] 1.2 Add `hr_salary_grades` table
  - [x] 1.3 Add `hr_positions` table referencing departments and grades
  - [x] 1.4 Add ALTER TABLE statements to extend `hr_employees` with new fields (photo, marital_status, nationality, branch_id, position_id, shift_id, supervisor_id, employment_category, identification fields, allowances, tax_group_id)
  - [x] 1.5 Add `hr_employee_documents` table
  - [x] 1.6 Add `hr_employee_transfers` and `hr_employee_promotions` history tables
  - [x] 1.7 Add performance indexes for all new foreign keys

- [x] 2. Backend — Branch management
  - [x] 2.1 Create `Branch` entity, `BranchRepository`, `BranchDTO`, `BranchMapper`
  - [x] 2.2 Create `BranchService` with CRUD, status management, and deletion guard
  - [x] 2.3 Create `BranchController` at `POST/GET/PUT/DELETE /api/hr/branches`

- [ ] 3. Backend — Department management (enhanced)
  - [x] 3.1 Create `SalaryGrade` entity, `SalaryGradeRepository`, `SalaryGradeDTO`, `SalaryGradeMapper`
  - [x] 3.2 Create `SalaryGradeService` and `SalaryGradeController`
  - [x] 3.3 Create `Position` entity, `PositionRepository`, `PositionDTO`, `PositionMapper`
  - [ ] 3.4 Create `PositionService` with salary-range validation and `PositionController`

- [ ] 4. Frontend — Organisation structure pages
  - [x] 4.1 Create `BranchesPage` with table, create/edit dialog, status toggle
  - [x] 4.2 Create `DepartmentsPage` — update existing page to include branch selector and employee count
  - [x] 4.3 Create `PositionsPage` with grade picker, salary range inputs
  - [x] 4.4 Add hrService methods: `getBranches`, `createBranch`, `updateBranch`, `getPositions`, `createPosition`, `getSalaryGrades`, `createSalaryGrade`
  - [x] 4.5 Register new routes `/hr/branches`, `/hr/positions`, `/hr/salary-grades` in `App.tsx`


## Phase 2 — Enhanced Employee Management

- [ ] 5. Backend — Enhanced employee profile
  - [ ] 5.1 Update `Employee` entity to add all new fields from V10 migration
  - [ ] 5.2 Update `EmployeeDTO` and `EmployeeMapper` to expose new fields
  - [ ] 5.3 Add transfer action to `EmployeeService`: record in `hr_employee_transfers`, update employee's branch/department
  - [ ] 5.4 Add promote action to `EmployeeService`: record in `hr_employee_promotions`, update position and salary
  - [ ] 5.5 Add rehire action to `EmployeeService`: reactivate and create new employment period
  - [ ] 5.6 Add document upload endpoint to `EmployeeController`: `POST /api/hr/employees/{id}/documents`
  - [ ] 5.7 Add endpoints for transfer history and promotion history

- [ ] 6. Frontend — Enhanced employee form and detail
  - [ ] 6.1 Update `EmployeeFormDialog` to include tabs: Personal, Employment, Identification, Bank, Salary, Documents
  - [ ] 6.2 Add Transfer dialog with branch/department selectors and effective date
  - [ ] 6.3 Add Promote dialog with position selector, new salary, and justification
  - [ ] 6.4 Add document upload panel to employee form (file type + upload)
  - [ ] 6.5 Update `EmployeesTable` to show branch, position, and supervisor columns

- [ ] 7. Backend — HR Dashboard
  - [ ] 7.1 Create `HrDashboardService` with all KPI aggregation queries
  - [ ] 7.2 Create `HrDashboardController` at `GET /api/hr/dashboard/kpis`
  - [ ] 7.3 Add chart-data endpoints: `GET /api/hr/dashboard/charts/{type}` supporting types: headcount-growth, gender-distribution, department-distribution, age-distribution, leave-trends, turnover-rate, attendance-analytics

- [ ] 8. Frontend — HR Dashboard page
  - [ ] 8.1 Create `HrDashboardPage` at `/hr/dashboard`
  - [ ] 8.2 Implement KPI cards row (total, active, inactive, new hires, on leave, on duty, night shift, permanent vs contract)
  - [ ] 8.3 Implement upcoming birthdays and anniversaries panels
  - [ ] 8.4 Implement charts section using recharts: headcount growth line chart, gender pie, department bar, leave trends stacked bar
  - [ ] 8.5 Add `getHrDashboardKpis` and `getHrChartData` to hrService
  - [ ] 8.6 Register route `/hr/dashboard` in `App.tsx`


## Phase 3 — Attendance and Shift Management

- [ ] 9. Create Flyway migration V11__hr_shifts_attendance.sql
  - [ ] 9.1 Add `hr_shifts` table
  - [ ] 9.2 Add `hr_shift_assignments` table
  - [ ] 9.3 Add new columns to `hr_attendance` (shift_id, check_in_method, GPS coords, break times, late_minutes, early_departure_minutes)
  - [ ] 9.4 Add `hr_holiday_calendars` and `hr_holiday_entries` tables
  - [ ] 9.5 Add `hr_roster_schedules` and `hr_roster_entries` tables

- [ ] 10. Backend — Shift management
  - [ ] 10.1 Create `Shift` entity, `ShiftRepository`, `ShiftDTO`, `ShiftMapper`
  - [ ] 10.2 Create `ShiftAssignment` entity, `ShiftAssignmentRepository`
  - [ ] 10.3 Create `ShiftService` with CRUD, assignment, overlap conflict detection
  - [ ] 10.4 Create `ShiftController` at `/api/hr/shifts` and `/api/hr/shifts/assign`

- [ ] 11. Backend — Enhanced attendance
  - [ ] 11.1 Update `Attendance` entity with new fields
  - [ ] 11.2 Update `AttendanceService` to calculate late_minutes, early_departure_minutes, overtime_hours using assigned shift
  - [ ] 11.3 Update `AttendanceController` to accept check_in_method and GPS coordinates
  - [ ] 11.4 Add attendance summary report endpoint with department/branch/date filters

- [ ] 12. Backend — Holiday calendar
  - [ ] 12.1 Create `HolidayCalendar` and `HolidayEntry` entities, repositories, DTOs, mapper
  - [ ] 12.2 Create `HolidayCalendarService` with CRUD, year-copy, working-day calculation
  - [ ] 12.3 Create `HolidayCalendarController` at `/api/hr/holiday-calendars`

- [ ] 13. Backend — Roster scheduling
  - [ ] 13.1 Create `RosterSchedule` and `RosterEntry` entities, repositories, DTOs, mapper
  - [ ] 13.2 Create `RosterService` with scheduling, conflict detection, publish validation, shift swap workflow
  - [ ] 13.3 Create `RosterController` at `/api/hr/rosters`

- [ ] 14. Frontend — Shifts and roster pages
  - [ ] 14.1 Create `ShiftsPage` with shifts table and `ShiftFormDialog` (time pickers, shift type, applicable days)
  - [ ] 14.2 Create `RosterPage` with weekly calendar grid, employee row per department, shift assignment cells
  - [ ] 14.3 Create `HolidayCalendarPage` with year view and holiday entry management
  - [ ] 14.4 Update `AttendancePage` to show shift name, check-in method, late minutes; add manual record entry form
  - [ ] 14.5 Add hrService methods: shifts CRUD, roster CRUD/publish, holiday calendar CRUD
  - [ ] 14.6 Register routes `/hr/shifts`, `/hr/roster`, `/hr/holidays` in `App.tsx`


## Phase 4 — Enhanced Leave Management

- [ ] 15. Create Flyway migration V12__hr_leave_enhanced.sql
  - [ ] 15.1 Add `hr_leave_types` configurable table
  - [ ] 15.2 Add `hr_leave_balances` table with generated `remaining_days` column
  - [ ] 15.3 Add `hr_workflow_configs` and `hr_workflow_steps` tables
  - [ ] 15.4 Add `hr_workflow_instances` and `hr_workflow_approvals` tables

- [ ] 16. Backend — Configurable leave types
  - [ ] 16.1 Create `LeaveType` entity, `LeaveTypeRepository`, `LeaveTypeDTO`, `LeaveTypeMapper`
  - [ ] 16.2 Create `LeaveTypeService` with CRUD and gender-specific validation
  - [ ] 16.3 Create `LeaveTypeController` at `/api/hr/leave-types`

- [ ] 17. Backend — Leave balances
  - [ ] 17.1 Create `LeaveBalance` entity, `LeaveBalanceRepository`, `LeaveBalanceDTO`
  - [ ] 17.2 Create `LeaveBalanceService` with: balance init, deduction on approval, restoration on cancellation, year-end carryover
  - [ ] 17.3 Create `LeaveBalanceController` at `GET /api/hr/leave-balances`
  - [ ] 17.4 Update `LeaveRequestService` to validate balance sufficiency on submission and call LeaveBalanceService on approval/rejection/cancellation

- [ ] 18. Backend — Workflow engine
  - [ ] 18.1 Create `WorkflowConfig`, `WorkflowStep`, `WorkflowInstance`, `WorkflowApproval` entities and repositories
  - [ ] 18.2 Create `WorkflowService` with config CRUD, instance creation (with config snapshot), approval routing, escalation detection
  - [ ] 18.3 Create `WorkflowConfigController` at `/api/hr/workflow-configs`
  - [ ] 18.4 Create `WorkflowController` at `POST /api/hr/workflow/{instanceId}/action`
  - [ ] 18.5 Integrate workflow engine into `LeaveRequestService`: create instance on leave submission, route through approval chain

- [ ] 19. Frontend — Leave enhancements
  - [ ] 19.1 Create `LeaveTypesPage` with configurable leave type table and `LeaveTypeFormDialog` (all policy fields)
  - [ ] 19.2 Add leave balance summary bar to `LeaveRequestsPage` showing per-type remaining days
  - [ ] 19.3 Create leave calendar view on `LeaveRequestsPage` showing approved leaves by month
  - [ ] 19.4 Add hrService methods: `getLeaveTypes`, `createLeaveType`, `updateLeaveType`, `getLeaveBalances`
  - [ ] 19.5 Register route `/hr/leave-types` in `App.tsx`


## Phase 5 — Enhanced Payroll

- [ ] 20. Create Flyway migration V13__hr_payroll_enhanced.sql
  - [ ] 20.1 Add `hr_salary_components` table
  - [ ] 20.2 Add `hr_tax_brackets` table
  - [ ] 20.3 Add `hr_payroll_line_items` table
  - [ ] 20.4 Add `hr_employee_loans` and `hr_loan_installments` tables

- [ ] 21. Backend — Salary components and tax brackets
  - [ ] 21.1 Create `SalaryComponent` entity, repository, DTO, mapper, service, controller at `/api/hr/salary-components`
  - [ ] 21.2 Create `TaxBracket` entity, repository, DTO, mapper, service, controller at `/api/hr/tax-brackets`

- [ ] 22. Backend — Enhanced payroll processing
  - [ ] 22.1 Create `PayrollLineItem` entity and repository
  - [ ] 22.2 Update `PayrollService` (or create `PayrollEnhancedService`) to: resolve salary components per employee grade/type, compute line items, apply tax brackets, incorporate attendance deductions and overtime pay
  - [ ] 22.3 Add payroll generate endpoint: `POST /api/hr/payroll/generate` (bulk for a period)
  - [ ] 22.4 Add payslip endpoint: `GET /api/hr/payroll/{id}/payslip` returning employee details + all line items
  - [ ] 22.5 Add payroll summary report: `GET /api/hr/payroll/summary?period=&departmentId=`

- [ ] 23. Backend — Loan management
  - [ ] 23.1 Create `EmployeeLoan` entity, repository, DTO, mapper
  - [ ] 23.2 Create `LoanService` with: create loan, approve (schedule installments), integrate installments as DEDUCTION line items in payroll, update remaining balance
  - [ ] 23.3 Create `LoanController` at `/api/hr/loans`

- [ ] 24. Frontend — Payroll and loans pages
  - [ ] 24.1 Update `PayrollPage` to show line items breakdown on row expand and add `PayrollGenerateDialog`
  - [ ] 24.2 Add `PayslipViewer` modal with all earnings, deductions, and net pay breakdown
  - [ ] 24.3 Create `SalaryComponentsPage` with component table and `SalaryComponentFormDialog`; include tax brackets sub-section
  - [ ] 24.4 Create `LoansPage` with loan table, `LoanFormDialog`, and approval action
  - [ ] 24.5 Add hrService methods: salary components CRUD, tax brackets CRUD, `generatePayroll`, `getPayslip`, loans CRUD + approve
  - [ ] 24.6 Register routes `/hr/salary-components`, `/hr/loans` in `App.tsx`


## Phase 6 — Recruitment and Onboarding

- [ ] 25. Create Flyway migration V14__hr_recruitment_onboarding.sql
  - [ ] 25.1 Add `hr_job_vacancies` table
  - [ ] 25.2 Add `hr_applicants`, `hr_applicant_stage_history` tables
  - [ ] 25.3 Add `hr_interviews` and `hr_offer_letters` tables
  - [ ] 25.4 Add `hr_onboarding_templates`, `hr_onboarding_template_tasks` tables
  - [ ] 25.5 Add `hr_onboarding_checklists`, `hr_onboarding_checklist_items` tables

- [ ] 26. Backend — Job vacancies
  - [ ] 26.1 Create `JobVacancy` entity, repository, DTO, mapper
  - [ ] 26.2 Create `VacancyService` with CRUD, publish (validate closing_date), close, fill
  - [ ] 26.3 Create `VacancyController` at `/api/hr/vacancies`

- [ ] 27. Backend — Applicant pipeline
  - [ ] 27.1 Create `Applicant`, `ApplicantStageHistory` entities and repositories
  - [ ] 27.2 Create `ApplicantService` with CRUD, stage transition (record history), auto-create Employee on HIRE
  - [ ] 27.3 Create `ApplicantController` at `/api/hr/applicants` with stage transition endpoint

- [ ] 28. Backend — Interviews and offers
  - [ ] 28.1 Create `Interview` entity, repository, DTO, service, controller at `/api/hr/interviews`
  - [ ] 28.2 Create `OfferLetter` entity, repository, DTO, service, controller at `/api/hr/offers`
  - [ ] 28.3 Add recruitment funnel statistics endpoint: `GET /api/hr/vacancies/{id}/funnel-stats`

- [ ] 29. Backend — Onboarding
  - [ ] 29.1 Create `OnboardingTemplate`, `OnboardingTemplateTask` entities and repositories
  - [ ] 29.2 Create `OnboardingChecklist`, `OnboardingChecklistItem` entities and repositories
  - [ ] 29.3 Create `OnboardingService` with template CRUD, auto-checklist generation on employee create, task completion, progress summary
  - [ ] 29.4 Create `OnboardingController` at `/api/hr/onboarding`

- [ ] 30. Frontend — Recruitment pages
  - [ ] 30.1 Update `RecruitmentPage` to be a vacancy list/board with create vacancy form dialog
  - [ ] 30.2 Create `ApplicantsPage` at `/hr/vacancies/:id/applicants` with Kanban pipeline (columns per stage)
  - [ ] 30.3 Add `InterviewFormDialog` for scheduling and recording feedback
  - [ ] 30.4 Add `OfferLetterDialog` for creating and accepting/rejecting offers
  - [ ] 30.5 Create `OnboardingPage` showing checklists for recent hires with task completion toggles
  - [ ] 30.6 Add hrService methods: vacancies CRUD + publish, applicants CRUD + stage, interviews CRUD, offers CRUD, onboarding templates + checklists
  - [ ] 30.7 Register routes `/hr/vacancies`, `/hr/vacancies/:id/applicants`, `/hr/onboarding` in `App.tsx`


## Phase 7 — Asset, Document, and Contract Management

- [ ] 31. Create Flyway migration V15__hr_assets_contracts.sql
  - [ ] 31.1 Add `hr_employee_assets` table
  - [ ] 31.2 Add `hr_employment_contracts` table

- [ ] 32. Backend — Asset management
  - [ ] 32.1 Create `EmployeeAsset` entity, repository, DTO, mapper
  - [ ] 32.2 Create `AssetService` with assign, return, flag lost/damaged, exit checklist integration
  - [ ] 32.3 Create `AssetController` at `/api/hr/assets`

- [ ] 33. Backend — Contract management
  - [ ] 33.1 Create `EmploymentContract` entity, repository, DTO, mapper
  - [ ] 33.2 Create `ContractService` with CRUD, renewal (creates new + marks old as RENEWED), expiry alert query
  - [ ] 33.3 Create `ContractController` at `/api/hr/contracts`

- [ ] 34. Frontend — Assets and contracts pages
  - [ ] 34.1 Create `AssetsPage` with asset table, `AssetFormDialog`, return/flag actions
  - [ ] 34.2 Create `ContractsPage` with contract list, expiry alert badges, `ContractFormDialog`, and renew action
  - [ ] 34.3 Add hrService methods: assets CRUD + return/flag, contracts CRUD + renew
  - [ ] 34.4 Register routes `/hr/assets`, `/hr/contracts` in `App.tsx`

## Phase 8 — Performance and Training Enhancements

- [ ] 35. Backend — Enhanced performance reviews
  - [ ] 35.1 Update `PerformanceReviewService` to support 360-degree feedback collection
  - [ ] 35.2 Add `PerformanceReviewFormDialog` to frontend replacing the read-only table

- [ ] 36. Backend — Enhanced training
  - [ ] 36.1 Extend `Training` entity with: trainer_name, assessment_passing_score, certificate_validity_months, cpd_hours, is_mandatory, mandatory_for_roles
  - [ ] 36.2 Update `TrainingService` to validate assessment score before completing enrollment and create/renew compliance records
  - [ ] 36.3 Add CPD hours summary endpoint: `GET /api/hr/training/cpd-summary?employeeId=&year=`

- [ ] 37. Frontend — Training enhancements
  - [ ] 37.1 Update `TrainingPage` with create/edit `TrainingFormDialog` (all new fields)
  - [ ] 37.2 Add enrollment management panel showing enrolled employees and completion status
  - [ ] 37.3 Add CPD progress bar per employee on the compliance page
  - [ ] 37.4 Update `PerformanceReviewPage` with create review form dialog

## Phase 9 — Analytics and Compliance

- [ ] 38. Backend — HR analytics
  - [ ] 38.1 Create `HrAnalyticsService` with metrics: headcount by dept/branch/month, attrition rate, retention rate, recruitment funnel, time-to-hire, overtime costs, leave utilisation, payroll trend, training completion, diversity metrics
  - [ ] 38.2 Create `HrAnalyticsController` at `GET /api/hr/analytics/{metric}` with date/branch/department filters
  - [ ] 38.3 Add CSV/JSON export endpoint: `GET /api/hr/analytics/{metric}/export?format=csv`

- [ ] 39. Backend — Medical compliance enhancements
  - [ ] 39.1 Extend `hr_compliance` table: add licence_number, professional_body, specialty, subspecialty, registration_type, last_verified_date
  - [ ] 39.2 Add `hr_employee_vaccinations` table (vaccine_name, dose, administered_date, next_due_date)
  - [ ] 39.3 Add `hr_exposure_incident_reports` table
  - [ ] 39.4 Update `HrComplianceService` with expiry alert queries (90/30/7 day thresholds)
  - [ ] 39.5 Add minimum staffing monitor endpoint: `GET /api/hr/compliance/staffing-alerts`

- [ ] 40. Frontend — Analytics page
  - [ ] 40.1 Create `AnalyticsPage` at `/hr/analytics` with metric selector and chart display
  - [ ] 40.2 Implement headcount trend, attrition, turnover, leave utilisation, payroll trend charts
  - [ ] 40.3 Add export to CSV button
  - [ ] 40.4 Update `CompliancePage` with create/edit form dialog, expiry countdown badges, vaccination sub-tab

- [ ] 41. Frontend — Benefits page update
  - [ ] 41.1 Update `BenefitsPage` with create/edit `BenefitFormDialog` (type, plan, provider, contributions, dates)

## Phase 10 — Notifications and Workflow Config UI

- [ ] 42. Backend — Notification integration
  - [ ] 42.1 Create HR notification event triggers: leave approved/rejected, payslip available, contract expiring, onboarding task due, licence expiring
  - [ ] 42.2 Integrate with existing notification service via internal events or direct service calls

- [ ] 43. Frontend — Workflow configuration UI
  - [ ] 43.1 Create `WorkflowConfigPage` at `/hr/settings/workflows`
  - [ ] 43.2 Allow HR_Admin to define approval chains per workflow type with ordered steps and escalation hours
  - [ ] 43.3 Add hrService methods: workflow config CRUD, workflow instance action

## Phase 11 — README and Permissions Update

- [ ] 44. Update HR README
  - [ ] 44.1 Rewrite `hospital-management-system/src/main/java/com/act/hospitalmanagementsystem/hr/README.md` to reflect all new features, API endpoints, and architecture

- [ ] 45. Add new HR permissions
  - [ ] 45.1 Add new permission constants: `HR_PAYROLL_READ`, `HR_PAYROLL_WRITE`, `HR_RECRUITMENT_READ`, `HR_RECRUITMENT_WRITE`, `HR_REPORTS_READ`, `HR_SETTINGS_WRITE`, `HR_COMPLIANCE_READ`, `HR_COMPLIANCE_WRITE`
  - [ ] 45.2 Create Flyway migration V16__hr_extended_permissions.sql to seed new permissions
  - [ ] 45.3 Add `@PreAuthorize` annotations to all new controllers using appropriate permission checks

- [ ] 46. Navigation sidebar update
  - [ ] 46.1 Add new HR sub-menu items to the sidebar component for all new pages: Dashboard, Branches, Positions, Shifts, Roster, Holidays, Leave Types, Salary Components, Loans, Vacancies, Onboarding, Assets, Contracts, Analytics
