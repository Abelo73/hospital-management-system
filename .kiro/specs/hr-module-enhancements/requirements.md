# Requirements Document — HR Module Enhancements

## Introduction

This document specifies the full requirements for enhancing the existing HR module of the Hospital Management System (HMS). The current module provides basic CRUD for employees, attendance, leave requests, payroll, performance reviews, recruitment job postings, training, benefits, and compliance tracking. This enhancement expands it into a production-grade, configurable, hospital-specific HR platform covering organizational structure, enriched employee profiles, shift/roster management, full recruitment pipelines, onboarding, ESS/MSS portals, asset and document management, analytics, audit logging, notifications, and workflow automation.

All HR policies (leave types, shift definitions, tax brackets, salary grades, holiday calendars, approval chains) are stored in the database and are fully configurable through admin settings — nothing is hardcoded. The system is multi-branch/multi-hospital aware, using soft deletes consistently and wrapping all API responses in the existing `BaseResponseDTO` pattern.

Requirements are grouped by implementation phase. Phases 1–6 are the primary scope. Phases 7–11 are defined but represent subsequent work.

---

## Glossary

- **System**: The Hospital Management System HR module backend and frontend.
- **HR_Admin**: An HR administrator with full system access.
- **HR_Manager**: An HR manager with HR_WRITE and HR_ADMIN permissions.
- **HR_Officer**: An HR officer with HR_WRITE permission.
- **Manager**: A department head or supervisor with team management access.
- **Employee**: Any registered staff member with an employee record.
- **Branch**: A physical hospital location or organisational unit.
- **Department**: A clinical or administrative grouping within a branch.
- **Position**: A named role within a department with a defined grade and salary range.
- **Shift**: A named time-block defining working hours.
- **Roster**: A scheduled assignment of employees to shifts over a period.
- **Leave_Balance**: The computed remaining days of a leave type for an employee in a leave cycle.
- **Payslip**: A generated document summarising an employee's earnings and deductions for a pay period.
- **Approval_Chain**: A configurable, ordered sequence of approver roles for a workflow type.
- **Workflow**: A multi-step approval process for HR actions (leave, promotion, etc.).
- **Audit_Log**: An immutable record of every data-changing action including actor, timestamp, old value, and new value.
- **ESS**: Employee Self-Service portal.
- **MSS**: Manager Self-Service portal.
- **KPI**: Key Performance Indicator.
- **CPD**: Continuing Professional Development.
- **CME**: Continuing Medical Education.
- **GDPR**: General Data Protection Regulation.

---

## Phase 1 — Organisational Structure

### Requirement 1: Branch and Hospital Management

**User Story:** As an HR_Admin, I want to manage branches and hospital locations, so that all employee and resource data is correctly scoped to the right physical site.

#### Acceptance Criteria

1. THE System SHALL store branches with name, code, address, city, country, phone, email, branch type (HOSPITAL, CLINIC, ADMIN_OFFICE), status (ACTIVE, INACTIVE), and a parent branch reference for hierarchy.
2. WHEN an HR_Admin creates a branch, THE System SHALL auto-generate a unique branch code if one is not supplied.
3. WHEN an HR_Admin deactivates a branch, THE System SHALL set the branch status to INACTIVE and prevent new employees from being assigned to it.
4. IF a branch deletion is attempted and active employees are assigned to it, THEN THE System SHALL reject the deletion and return a descriptive error listing the count of assigned employees.
5. THE System SHALL support a branch hierarchy of at most five levels deep (e.g. Group → Hospital → Wing → Department → Unit).

---

### Requirement 2: Department Management

**User Story:** As an HR_Admin, I want to create and manage hospital departments, so that employees, positions, and costs can be grouped by clinical or administrative function.

#### Acceptance Criteria

1. THE System SHALL store departments with name, code, branch_id, department_head_employee_id, budget, description, status, and parent_department_id for sub-departments.
2. WHEN a department head is assigned, THE System SHALL verify that the referenced employee record exists and is ACTIVE.
3. WHEN an HR_Admin updates a department budget, THE System SHALL record the old and new budget values in the Audit_Log.
4. THE System SHALL return the count of active employees in a department whenever department details are retrieved.
5. IF a department is deleted while it has active employees or open positions, THEN THE System SHALL reject the deletion.

---

### Requirement 3: Position and Grade Management

**User Story:** As an HR_Admin, I want to define positions with grades and salary ranges, so that employee compensation is standardised and transparent.

#### Acceptance Criteria

1. THE System SHALL store positions with title, code, department_id, grade_id, min_salary, max_salary, responsibilities, required_skills, required_qualifications, and reporting_position_id.
2. THE System SHALL store salary grades with name, code, min_salary, max_salary, and currency.
3. WHEN an employee is assigned to a position, THE System SHALL verify that their salary falls within the position's min/max salary range for their grade.
4. IF a position is assigned a salary outside the grade's defined range, THEN THE System SHALL return a validation error.
5. THE System SHALL allow HR_Admin to deactivate a position without deleting it (soft delete pattern).


---

## Phase 2 — Enhanced Employee Management

### Requirement 4: Full Employee Profile

**User Story:** As an HR_Officer, I want to maintain a complete employee profile including personal, employment, identification, bank, and salary details, so that all HR processes are driven from a single source of truth.

#### Acceptance Criteria

1. THE System SHALL extend the existing hr_employees table to store: photo URL, marital status, nationality, religion, blood group, disability flag, work location, branch_id, shift_id, supervisor_employee_id, employment_category (PERMANENT, CONTRACT, INTERN, CONSULTANT), passport number, national ID, driver licence number, bank account holder name, bank branch, basic_salary, housing_allowance, transport_allowance, medical_allowance, meal_allowance, and tax_group_id.
2. WHEN an employee profile is created or updated, THE System SHALL validate that the email address is unique across all non-deleted employees.
3. WHEN an employee is assigned to a branch, THE System SHALL verify the branch exists and is ACTIVE.
4. THE System SHALL support attaching documents to an employee profile with types: CV, DEGREE, CERTIFICATE, CONTRACT, MEDICAL_CERT, ID_SCAN, PASSPORT_SCAN, and OTHER.
5. WHEN an employee document is uploaded, THE System SHALL store the file reference, document type, upload date, and the uploading user.
6. THE System SHALL support employee lifecycle actions: Transfer, Promote, Rehire, Activate, Deactivate, in addition to the existing Create, Update, Terminate.
7. WHEN an employee is Transferred, THE System SHALL record the old and new branch_id and department_id, effective date, and reason in a dedicated transfer history table.
8. WHEN an employee is Promoted, THE System SHALL record the old and new position_id, old and new salary, effective date, and approver in a promotion history table.
9. WHEN an employee is Rehired after termination, THE System SHALL create a new employment record linked to the existing employee profile and preserve all prior history.
10. THE System SHALL auto-generate a sequential employee_number using a configurable prefix per branch (e.g. KNH-0001).

---

### Requirement 5: HR Dashboard KPIs and Charts

**User Story:** As an HR_Manager, I want a real-time dashboard showing workforce statistics and trends, so that I can make informed staffing decisions at a glance.

#### Acceptance Criteria

1. THE System SHALL provide a dashboard endpoint returning: total employees, active employees, inactive employees, new hires in the current month, employees currently on approved leave, employees on duty today, employees on night shift today, permanent vs contract headcount, upcoming birthdays within 7 days, upcoming work anniversaries within 7 days, open vacancies count, pending approval count (leave + recruitment + promotions), monthly payroll total, today's attendance summary (present, absent, late).
2. THE System SHALL provide chart-data endpoints for: employee headcount growth (monthly, 12-month rolling), gender distribution (pie), department distribution (bar), age distribution (histogram with configurable buckets), leave trends (monthly stacked bar by leave type), recruitment funnel statistics, annualised turnover rate, and daily attendance analytics.
3. WHEN dashboard data is requested, THE System SHALL scope all KPIs to the requesting user's accessible branches.
4. IF no data exists for a metric, THE System SHALL return a zero value rather than an error or null.


---

## Phase 3 — Attendance and Shift Management

### Requirement 6: Shift Definitions (Configurable)

**User Story:** As an HR_Admin, I want to define named shifts with configurable start/end times, so that attendance and rosters can reference them without any hardcoded values.

#### Acceptance Criteria

1. THE System SHALL store shifts with name, code, start_time, end_time, break_duration_minutes, shift_type (MORNING, EVENING, NIGHT, ROTATIONAL, WEEKEND, HOLIDAY, EMERGENCY), grace_period_minutes, max_overtime_hours, and applicable_days (stored as a JSON array of weekday names).
2. WHEN a shift is created, THE System SHALL validate that end_time is after start_time or that the shift spans midnight (overnight flag).
3. THE System SHALL allow HR_Admin to assign a shift to an individual employee, a department, or a team, with an effective date and optional end date.
4. WHEN a shift assignment overlaps an existing active assignment for the same employee, THE System SHALL reject the new assignment with a conflict error.
5. THE System SHALL support a rotational shift pattern that cycles through a configurable list of shift_ids on a weekly or custom-day interval.

---

### Requirement 7: Enhanced Attendance Tracking

**User Story:** As an HR_Officer, I want to record attendance through multiple methods and track breaks, late arrivals, and overtime, so that time and pay calculations are accurate.

#### Acceptance Criteria

1. THE System SHALL extend hr_attendance to store: check_in_method (MANUAL, BIOMETRIC, RFID, FINGERPRINT, FACE, MOBILE_GPS), check_in_location (GPS coordinates or device name), break_start_time, break_end_time, late_minutes, early_departure_minutes, and shift_id.
2. WHEN an employee checks in after the shift start_time plus the shift's grace_period_minutes, THE System SHALL calculate and store the late_minutes value.
3. WHEN an employee checks out before the shift end_time, THE System SHALL calculate and store early_departure_minutes.
4. THE System SHALL compute overtime_hours as max(0, (actual_hours_worked - shift_duration_hours)) and cap it at the shift's max_overtime_hours.
5. THE System SHALL provide attendance summary reports with filters by employee, department, branch, date range, and status, returning totals for: present days, absent days, late days, total overtime hours.
6. IF an attendance record already exists for an employee on a given date, THEN THE System SHALL update it rather than create a duplicate.
7. WHERE the attendance check_in_method is MOBILE_GPS, THE System SHALL store the latitude and longitude of the check-in location.

---

### Requirement 8: Holiday Calendar (Configurable)

**User Story:** As an HR_Admin, I want to configure hospital-specific holiday calendars, so that attendance and leave calculations automatically respect public, religious, and institutional holidays.

#### Acceptance Criteria

1. THE System SHALL store holiday calendars with name, year, branch_id (null for global), and a list of holiday entries each containing: date, name, type (NATIONAL, RELIGIOUS, HOSPITAL, WEEKEND_OVERRIDE), and is_half_day.
2. WHEN calculating leave working days between two dates, THE System SHALL exclude all dates in the applicable holiday calendar.
3. THE System SHALL allow HR_Admin to copy a holiday calendar from one year to the next as a starting point.
4. WHEN the System calculates payroll for a period, THE System SHALL reference the holiday calendar to determine whether holiday premium pay rules apply.


---

### Requirement 9: Roster Scheduling

**User Story:** As a Nurse Manager or department head, I want to create and manage weekly or monthly rosters for clinical staff, so that minimum staffing levels are maintained at all times.

#### Acceptance Criteria

1. THE System SHALL store rosters with: roster_name, period_start, period_end, department_id, branch_id, status (DRAFT, PUBLISHED, ARCHIVED), and a list of roster_entries each containing: employee_id, date, shift_id, role_override.
2. WHEN a roster is published, THE System SHALL check that every day in the period has at least the department's configured minimum_staff_count covered, and warn (not block) if any day falls below the minimum.
3. THE System SHALL detect and flag conflicts when an employee is scheduled in two overlapping shifts on the same date.
4. WHEN an employee requests a shift swap, THE System SHALL create a ShiftSwapRequest with the two employee_ids, the affected dates, and route it through the configured approval workflow.
5. WHEN a ShiftSwapRequest is approved, THE System SHALL atomically update both employees' roster entries.
6. THE System SHALL support auto-scheduling that distributes available employees across shifts for a period while respecting leave approvals and skill requirements.

---

## Phase 4 — Enhanced Leave Management

### Requirement 10: Configurable Leave Types

**User Story:** As an HR_Admin, I want to define and configure all leave types in the system, so that any policy change (days per year, carryover rules, gender restrictions) is made in one place without code changes.

#### Acceptance Criteria

1. THE System SHALL store leave types in a configurable table with: name, code, annual_days, is_paid, requires_approval, requires_attachment, is_gender_specific, applicable_gender, max_carryover_days, min_service_months_required, accrual_frequency (MONTHLY, ANNUAL, NONE), and is_active.
2. WHEN a leave request is submitted, THE System SHALL validate the leave type against the employee's gender if the type is gender-specific.
3. WHEN a leave request is submitted, THE System SHALL check the employee's Leave_Balance for the requested type and reject the request if the balance is insufficient.
4. THE System SHALL compute and store Leave_Balance records per employee, per leave type, per leave cycle (typically a calendar year).
5. WHEN a leave request is approved, THE System SHALL deduct the approved days from the employee's Leave_Balance.
6. WHEN a leave request is cancelled after approval, THE System SHALL restore the deducted days to the employee's Leave_Balance.
7. AT the start of each new leave cycle, THE System SHALL carry forward unused days up to the leave type's max_carryover_days and reset remaining entitlement.

---

### Requirement 11: Leave Approval Workflow

**User Story:** As an Employee, I want my leave request to flow through the correct approval chain, so that the right people review and approve or reject it.

#### Acceptance Criteria

1. WHEN a leave request is submitted, THE System SHALL look up the applicable Approval_Chain for the LEAVE workflow and route the request to the first approver.
2. THE System SHALL support multi-level approval chains: Employee → Supervisor → HR_Officer → HR_Manager, with each level configurable per branch and department.
3. WHEN an approver at any level approves the request, THE System SHALL advance the request to the next approver in the chain.
4. WHEN the final approver approves, THE System SHALL mark the leave request as APPROVED and notify the employee.
5. WHEN any approver rejects, THE System SHALL mark the request as REJECTED, record the rejection_reason, and notify the employee.
6. IF a leave request has been pending at an approval step for more than the configured escalation_hours, THEN THE System SHALL escalate the request to the next level and notify the skipped approver.
7. THE System SHALL provide a leave calendar view returning approved leaves per department and date range for roster planning.


---

## Phase 5 — Enhanced Payroll

### Requirement 12: Configurable Salary Components

**User Story:** As an HR_Admin, I want to define salary components (earnings and deductions) and tax brackets in the admin settings, so that payroll calculations automatically reflect current pay policy without code changes.

#### Acceptance Criteria

1. THE System SHALL store salary_components with: name, code, component_type (EARNING, DEDUCTION), calculation_method (FIXED, PERCENTAGE_OF_BASIC, FORMULA), value, is_taxable, is_mandatory, and applies_to_employee_types.
2. THE System SHALL store tax_brackets with: name, min_income, max_income, rate_percentage, currency, and effective_date.
3. WHEN payroll is generated for an employee, THE System SHALL resolve all applicable salary_components for that employee's grade and employment type.
4. THE System SHALL calculate gross_pay as the sum of all EARNING components, net_pay as gross_pay minus all DEDUCTION components (including computed tax), and store each component's computed amount in a payroll_line_items table.
5. THE System SHALL apply the correct tax_bracket to the employee's gross_pay based on the bracket effective_date closest to the pay period end date.

---

### Requirement 13: Payroll Processing and Payslips

**User Story:** As an HR_Manager, I want to generate, review, and finalise payroll for a pay period, and distribute payslips to employees, so that salary is paid accurately and on time.

#### Acceptance Criteria

1. WHEN payroll is run for a period, THE System SHALL generate a payroll record for every ACTIVE employee who has not already been paid for that period.
2. THE System SHALL incorporate attendance-based deductions for: absent days (configurable daily_rate_deduction), late_penalty_per_occurrence, and unapproved leave.
3. THE System SHALL incorporate approved overtime hours, converting them to monetary value using the employee's overtime_rate (configurable as a multiplier of the hourly rate).
4. WHEN payroll is in DRAFT status, THE System SHALL allow HR_Manager to make adjustments to individual line items before finalisation.
5. WHEN payroll is finalised (status changes to PROCESSED), THE System SHALL lock all line items from further editing.
6. THE System SHALL generate a Payslip for each employee in a processed payroll run, containing all earnings, deductions, net pay, pay period, and employee details.
7. WHEN a payslip is generated, THE System SHALL store it and make it available via the ESS portal and via a secure download link.
8. THE System SHALL provide a payroll summary report grouping total payroll cost by department.

---

### Requirement 14: Loan Management

**User Story:** As an Employee, I want to apply for a salary advance or staff loan, so that it can be approved and automatically deducted from future payroll runs.

#### Acceptance Criteria

1. THE System SHALL store employee_loans with: employee_id, loan_type, principal_amount, approved_amount, interest_rate, repayment_months, monthly_installment, status (PENDING, APPROVED, ACTIVE, SETTLED, REJECTED), start_date, and remaining_balance.
2. WHEN a loan is approved, THE System SHALL schedule monthly deductions by creating pending loan_installments linked to the loan.
3. WHEN payroll is generated, THE System SHALL automatically include any due loan_installments as DEDUCTION line items.
4. THE System SHALL update the loan's remaining_balance after each payroll deduction.
5. IF an employee has an existing ACTIVE loan, THEN THE System SHALL prevent approval of a second loan unless the HR_Admin explicitly overrides this restriction.


---

## Phase 6 — Recruitment and Onboarding

### Requirement 15: Enhanced Recruitment Pipeline

**User Story:** As a Recruitment_Officer, I want to manage the full applicant lifecycle from vacancy creation to hiring, so that we fill positions with the best candidates efficiently.

#### Acceptance Criteria

1. THE System SHALL store job_vacancies with: title, position_id, department_id, branch_id, number_of_positions, description, requirements, responsibilities, salary_range, employment_type, posting_date, closing_date, status (DRAFT, OPEN, CLOSED, FILLED, CANCELLED), and published_by.
2. WHEN a vacancy is published (status → OPEN), THE System SHALL validate that closing_date is in the future.
3. THE System SHALL store applicants with: vacancy_id, first_name, last_name, email, phone, nationality, cv_url, cover_letter_url, application_date, current_stage, and overall_status.
4. THE System SHALL support a configurable recruitment pipeline with the following stages (each stage activatable per vacancy): REGISTERED, SCREENING, SHORTLISTED, INTERVIEW_SCHEDULED, INTERVIEW_COMPLETED, SELECTED, OFFER_SENT, OFFER_ACCEPTED, HIRED, REJECTED, WITHDRAWN.
5. WHEN an applicant moves from one stage to the next, THE System SHALL record the stage transition with timestamp and actor.
6. THE System SHALL store interview records with: applicant_id, interviewer_employee_id, scheduled_date_time, location, interview_type (IN_PERSON, VIDEO, PHONE), status (SCHEDULED, COMPLETED, CANCELLED, NO_SHOW), score, and feedback.
7. THE System SHALL store offer letters with: applicant_id, vacancy_id, proposed_salary, proposed_start_date, offer_expiry_date, status (PENDING, ACCEPTED, REJECTED, EXPIRED), and offer_document_url.
8. WHEN an offer is ACCEPTED and the applicant is hired, THE System SHALL create a new Employee record pre-populated with the applicant's data and link it to the applicant record.
9. THE System SHALL provide recruitment funnel statistics: applicants per stage, average time per stage, offer acceptance rate, time-to-hire in days.

---

### Requirement 16: Employee Onboarding

**User Story:** As an HR_Officer, I want to assign and track onboarding checklists for new hires, so that no step is missed and the new employee is productive from day one.

#### Acceptance Criteria

1. THE System SHALL store onboarding_templates with a list of onboarding_tasks, each having: task_name, description, assigned_to_role, due_days_from_hire, is_mandatory, and task_category (DOCUMENT, EQUIPMENT, ORIENTATION, TRAINING, SYSTEM_ACCOUNT, POLICY).
2. WHEN a new employee record is created, THE System SHALL auto-generate an onboarding_checklist from the applicable template, assigning each task a due_date based on the hire_date.
3. WHEN a task is marked complete, THE System SHALL record the completed_by user, completion_date, and any notes.
4. THE System SHALL send a reminder notification to the assigned role when an onboarding task's due_date is within 24 hours and it is not yet complete.
5. THE System SHALL provide a progress summary showing percentage of completed mandatory tasks for each new hire.
6. IF all mandatory tasks are complete, THEN THE System SHALL mark the onboarding_checklist as COMPLETE and notify the HR_Officer.


---

## Phase 7 — Performance Management (Enhanced)

### Requirement 17: Goal Setting and KPI Tracking

**User Story:** As a Manager, I want to set goals and KPIs for my team members and track their progress, so that performance reviews are data-driven.

#### Acceptance Criteria

1. THE System SHALL store employee_goals with: employee_id, set_by_employee_id, title, description, target_value, unit_of_measure, start_date, due_date, status (DRAFT, ACTIVE, COMPLETED, CANCELLED), achievement_percentage, and review_cycle_id.
2. WHEN a goal is created, THE System SHALL link it to a review cycle so appraisals reference current goals.
3. THE System SHALL support 360-degree evaluations where feedback is collected from: self, direct manager, peers (configurable count), and subordinates.
4. WHEN a performance review is submitted, THE System SHALL calculate a weighted performance_score using configurable weights per evaluation source.
5. THE System SHALL store promotion_recommendations linked to a performance review, with: recommended_position_id, recommended_salary, justification, and approval status.

---

## Phase 8 — Training and Certification (Enhanced)

### Requirement 18: Training Calendar and CPD Tracking

**User Story:** As an HR_Officer, I want to manage a training calendar with assessments and certificates, and track mandatory CPD/CME hours for clinical staff, so that all regulatory requirements are met.

#### Acceptance Criteria

1. THE System SHALL extend hr_training with: trainer_name, trainer_external (boolean), assessment_passing_score, certificate_validity_months, cpd_hours, is_mandatory, and mandatory_for_roles.
2. WHEN an employee completes a training with assessment_passing_score set, THE System SHALL require a recorded assessment_score before marking the enrollment as COMPLETED.
3. IF an employee's assessment_score is below the passing score, THEN THE System SHALL mark the enrollment as FAILED and allow re-enrollment.
4. WHEN an enrollment is marked COMPLETED, THE System SHALL automatically create or renew a compliance record for the employee if the training maps to a compliance requirement.
5. THE System SHALL track cumulative CPD/CME hours per employee per calendar year and alert HR_Admin when an employee is below the regulatory minimum (configurable per role).
6. THE System SHALL support certificate expiry tracking and send renewal reminders at configurable intervals (e.g. 90, 30, 7 days before expiry).

---

## Phase 9 — ESS / MSS Portal

### Requirement 19: Employee Self-Service

**User Story:** As an Employee, I want to access and manage my own HR information through a self-service portal, so that I can handle routine tasks without contacting HR.

#### Acceptance Criteria

1. WHILE authenticated as an Employee, THE System SHALL allow the employee to view their own complete profile, attendance history, leave balances, payslips, training history, and compliance documents.
2. WHEN an Employee submits a leave request through the ESS portal, THE System SHALL route it through the same multi-level Approval_Chain as HR-submitted requests.
3. THE System SHALL allow an Employee to update their personal contact information, bank account details, and emergency contacts, subject to HR review (status: PENDING_REVIEW) before being applied.
4. THE System SHALL display announcements relevant to the employee's branch and department on the ESS home page.
5. THE System SHALL show the employee's upcoming schedule (roster entries) for the next 14 days.

---

### Requirement 20: Manager Self-Service

**User Story:** As a Manager, I want a dedicated view to manage my direct reports and act on pending approvals, so that I can fulfil my supervisory responsibilities efficiently.

#### Acceptance Criteria

1. WHILE authenticated as a Manager, THE System SHALL show a team overview listing all direct reports with their current status, shift, and today's attendance.
2. THE System SHALL surface all pending approval items (leave, attendance corrections, overtime, shift swaps) requiring the manager's action in a single inbox.
3. WHEN a Manager approves or rejects an item, THE System SHALL advance it through the Approval_Chain and notify the Employee.
4. THE System SHALL provide a team performance dashboard showing goal completion rates and last review scores for all direct reports.


---

## Phase 10 — Asset, Document, and Contract Management

### Requirement 21: Asset Management

**User Story:** As an HR_Officer, I want to assign and track physical assets to employees, so that we know what hospital equipment is with whom at all times.

#### Acceptance Criteria

1. THE System SHALL store employee_assets with: asset_type (LAPTOP, PHONE, TABLET, UNIFORM, MEDICAL_EQUIPMENT, ID_CARD, KEY, OTHER), asset_code, employee_id, assigned_date, return_date, condition (GOOD, DAMAGED, LOST), status (ASSIGNED, RETURNED, LOST, DAMAGED), and notes.
2. WHEN an asset is returned or marked LOST/DAMAGED, THE System SHALL record the event date and actor.
3. THE System SHALL provide a report of all currently assigned assets per employee and per asset type.
4. WHEN an employee is terminated or resigned, THE System SHALL flag all ASSIGNED assets for return in the exit checklist.

---

### Requirement 22: Document Expiry and Contract Management

**User Story:** As an HR_Officer, I want the system to track contract expiry dates and document validity, so that renewals are actioned before they lapse.

#### Acceptance Criteria

1. THE System SHALL store employment_contracts with: employee_id, contract_type (PERMANENT, TEMPORARY, INTERN, CONSULTANT), start_date, end_date, notice_period_days, file_url, status (ACTIVE, EXPIRED, TERMINATED, RENEWED), and renewal_reminder_days.
2. WHEN a contract's end_date is within renewal_reminder_days, THE System SHALL send a notification to HR_Officer and the employee's supervisor.
3. THE System SHALL support contract renewal by creating a new contract record and automatically updating the previous contract status to RENEWED.
4. THE System SHALL track expiry dates for all compliance documents and send notifications at 90, 30, and 7 days before expiry.
5. WHERE a document type has an associated regulatory body, THE System SHALL store the issuing authority and the registration/licence number.

---

## Phase 11 — Analytics, Audit Logging, Notifications, and Workflow Engine

### Requirement 23: Audit Logging

**User Story:** As an HR_Admin, I want every data-changing action recorded in an immutable audit log, so that I have full traceability for compliance and forensic purposes.

#### Acceptance Criteria

1. THE System SHALL record an Audit_Log entry for every CREATE, UPDATE, and DELETE action on any HR entity, containing: entity_name, entity_id, action_type, performed_by_user_id, performed_at timestamp, old_value (JSON), new_value (JSON), ip_address, and user_agent.
2. THE Audit_Log SHALL be append-only; no audit records SHALL be modified or deleted through any application interface.
3. THE System SHALL provide a filtered audit log search by entity_name, entity_id, performed_by, and date range.
4. THE System SHALL retain audit records for a minimum of 7 years (configurable).

---

### Requirement 24: Notifications

**User Story:** As an HR_Admin, I want the system to send configurable notifications by email, SMS, and in-app channels for key HR events, so that stakeholders are always informed.

#### Acceptance Criteria

1. THE System SHALL store notification_templates with: event_type, channel (EMAIL, SMS, PUSH, IN_APP), subject_template, body_template, and is_active.
2. WHEN a notifiable HR event occurs (leave approved, payslip available, contract expiring, task due), THE System SHALL resolve the applicable template, substitute dynamic variables, and dispatch the notification.
3. THE System SHALL store a record of every dispatched notification with: recipient, channel, sent_at, status (SENT, FAILED), and error_message if applicable.
4. IF notification dispatch fails, THEN THE System SHALL retry up to 3 times with exponential back-off before marking the notification as FAILED.

---

### Requirement 25: Configurable Workflow Engine

**User Story:** As an HR_Admin, I want to configure multi-step approval chains for any workflow type, so that approval processes match our hospital's organisational structure without code changes.

#### Acceptance Criteria

1. THE System SHALL store workflow_configs with: workflow_type (LEAVE, RECRUITMENT, PROMOTION, TRANSFER, EXPENSE, LOAN, PAYROLL, TERMINATION, SHIFT_SWAP), branch_id, department_id (optional for department-specific overrides), and an ordered list of approval_steps each with: step_order, approver_role, escalation_hours.
2. WHEN a workflow instance is created, THE System SHALL clone the current workflow_config into an immutable snapshot so that config changes do not affect in-progress approvals.
3. THE System SHALL track the current step, approval history, and timestamps for every workflow instance.
4. THE System SHALL support parallel approval steps where multiple approvers must all approve before the workflow advances.

---

### Requirement 26: HR Analytics

**User Story:** As an HR_Manager, I want analytics reports covering headcount, attrition, recruitment efficiency, and payroll trends, so that I can present data-driven insights to hospital leadership.

#### Acceptance Criteria

1. THE System SHALL compute and expose the following metrics: headcount by department/branch/month, attrition rate (annualised), retention rate, recruitment funnel conversion rates, average time-to-hire in days, cost-per-hire, overtime costs by department, leave utilisation rate by type, payroll cost trend (12-month), training completion rate, diversity metrics (gender ratio, nationality distribution), vacancy rate, absenteeism rate.
2. WHEN an analytics report is requested, THE System SHALL accept date_from, date_to, branch_id, and department_id as optional filters.
3. THE System SHALL support data export in CSV and JSON formats for all analytics endpoints.

---

## Phase Cross-Cutting: Hospital-Specific Compliance

### Requirement 27: Medical Licence and Credential Tracking

**User Story:** As an HR_Officer, I want to track professional registrations and medical licences for clinical staff, so that we are always compliant with healthcare regulations.

#### Acceptance Criteria

1. THE System SHALL extend the existing hr_compliance table to include: licence_number, issuing_authority, professional_body, specialty, subspecialty, registration_type (MEDICAL_LICENCE, NURSING_REGISTRATION, PHARMACY_LICENCE, LAB_TECHNICIAN, SPECIALIST_CERTIFICATION), and last_verified_date.
2. THE System SHALL store vaccination and occupational health records for employees with: vaccine_name, dose_number, administered_date, next_due_date, and administered_by.
3. THE System SHALL store exposure_incident_reports with: employee_id, incident_type, incident_date, description, reported_by, and follow_up_status.
4. THE System SHALL provide a minimum staffing level monitor that alerts HR_Admin when a department's current confirmed present count falls below the configured minimum.
5. THE System SHALL maintain emergency callback lists per specialty and department, showing available staff sorted by on-call priority.

---

### Requirement 28: Role-Based Access Control

**User Story:** As an HR_Admin, I want fine-grained role-based access control for all HR features, so that each user sees and can act on only what they are authorised for.

#### Acceptance Criteria

1. THE System SHALL define the following HR-specific permissions, building on the existing HR_READ / HR_WRITE / HR_ADMIN set: HR_PAYROLL_READ, HR_PAYROLL_WRITE, HR_RECRUITMENT_READ, HR_RECRUITMENT_WRITE, HR_REPORTS_READ, HR_SETTINGS_WRITE, HR_COMPLIANCE_READ, HR_COMPLIANCE_WRITE.
2. THE System SHALL restrict payroll data (salary amounts, bank details) to users with HR_PAYROLL_READ permission; employees with the ESS role SHALL only see their own payslips.
3. THE System SHALL enforce branch-level data scoping: users assigned to a branch SHALL only see employees and records belonging to branches they are authorised for.
4. THE System SHALL log every access to payroll and compliance data in the Audit_Log regardless of whether data is modified.

