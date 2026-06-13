# Analytics Module

## Overview

The Analytics module provides comprehensive data analysis and reporting capabilities for the Hospital Management System. It enables data-driven decision making through dashboards, reports, and advanced analytics on hospital operations, patient care, financial performance, and resource utilization.

---

## Features

### 1. Dashboard Analytics
- **Executive Dashboard** - High-level overview of hospital performance
- **Operational Dashboard** - Real-time operational metrics
- **Financial Dashboard** - Revenue, expenses, and financial KPIs
- **Clinical Dashboard** - Patient care quality metrics
- **Resource Dashboard** - Staff and resource utilization

### 2. Patient Analytics
- **Patient Demographics** - Age, gender, location analysis
- **Patient Flow** - Patient journey and flow analysis
- **Readmission Rates** - Track and analyze readmission patterns
- **Length of Stay** - Analyze hospital stay durations
- **Patient Satisfaction** - Track patient feedback and satisfaction scores

### 3. Clinical Analytics
- **Diagnosis Trends** - Most common diagnoses and trends
- **Treatment Outcomes** - Treatment success rates
- **Medication Usage** - Prescription patterns and drug utilization
- **Lab Results Analysis** - Laboratory test trends and patterns
- **Consultation Analytics** - Doctor consultation patterns

### 4. Operational Analytics
- **Appointment Analytics** - Booking patterns, no-show rates
- **Resource Utilization** - Bed occupancy, equipment usage
- **Staff Performance** - Doctor and nurse productivity metrics
- **Wait Time Analysis** - Patient wait times across departments
- **Throughput Metrics** - Department throughput and efficiency

### 5. Financial Analytics
- **Revenue Analysis** - Revenue by department, service, and period
- **Expense Tracking** - Operational and capital expenses
- **Profitability Analysis** - Service and department profitability
- **Billing Analytics** - Billing cycle time, collection rates
- **Insurance Analytics** - Insurance claim processing and reimbursement

### 6. Inventory Analytics
- **Stock Levels** - Current inventory status
- **Consumption Patterns** - Item usage trends
- **Expiry Management** - Track expiring items
- **Cost Analysis** - Inventory cost optimization
- **Procurement Analytics** - Purchase order analysis

### 7. HR Analytics
- **Staff Demographics** - Age, gender, role distribution
- **Attendance Analysis** - Attendance and punctuality metrics
- **Performance Metrics** - Staff performance evaluations
- **Turnover Analysis** - Staff retention and turnover rates
- **Training Analytics** - Training effectiveness and completion

### 8. Advanced Analytics
- **Predictive Analytics** - Predict patient admissions, readmissions
- **Trend Analysis** - Identify trends and patterns
- **Comparative Analysis** - Compare periods, departments
- **Correlation Analysis** - Find relationships between metrics
- **Anomaly Detection** - Identify unusual patterns

---

## Architecture

### Components

```
analytics/
├── controller/
│   ├── DashboardController.java          # Dashboard analytics endpoints
│   ├── PatientAnalyticsController.java   # Patient analytics endpoints
│   ├── ClinicalAnalyticsController.java  # Clinical analytics endpoints
│   ├── OperationalAnalyticsController.java # Operational analytics endpoints
│   ├── FinancialAnalyticsController.java # Financial analytics endpoints
│   ├── InventoryAnalyticsController.java # Inventory analytics endpoints
│   ├── HRAnalyticsController.java         # HR analytics endpoints
│   └── ReportController.java              # Report generation endpoints
├── service/
│   ├── DashboardService.java             # Dashboard analytics business logic
│   ├── PatientAnalyticsService.java      # Patient analytics business logic
│   ├── ClinicalAnalyticsService.java     # Clinical analytics business logic
│   ├── OperationalAnalyticsService.java  # Operational analytics business logic
│   ├── FinancialAnalyticsService.java    # Financial analytics business logic
│   ├── InventoryAnalyticsService.java    # Inventory analytics business logic
│   ├── HRAnalyticsService.java           # HR analytics business logic
│   ├── ReportService.java                # Report generation business logic
│   └── DataAggregationService.java       # Data aggregation and processing
├── repository/
│   ├── AnalyticsMetricRepository.java   # Analytics metrics data access
│   ├── ReportRepository.java             # Report data access
│   └── DashboardConfigRepository.java   # Dashboard configuration data access
├── entity/
│   ├── AnalyticsMetric.java              # Analytics metric entity
│   ├── Report.java                       # Report entity
│   ├── DashboardConfig.java              # Dashboard configuration entity
│   └── MetricDefinition.java             # Metric definition entity
├── enums/
│   ├── MetricType.java                   # Metric type enum
│   ├── AggregationType.java              # Aggregation type enum
│   ├── ReportFormat.java                  # Report format enum
│   ├── ReportStatus.java                  # Report status enum
│   └── TimePeriod.java                   # Time period enum
├── dto/
│   ├── DashboardDTO.java                # Dashboard data DTO
│   ├── MetricDTO.java                    # Metric data DTO
│   ├── AnalyticsFilterDTO.java           # Analytics filter DTO
│   ├── ReportRequestDTO.java             # Report request DTO
│   ├── ReportResponseDTO.java            # Report response DTO
│   ├── TrendDataDTO.java                 # Trend data DTO
│   └── ComparisonDTO.java                # Comparison data DTO
└── mapper/
    ├── AnalyticsMetricMapper.java        # Analytics metric mapper
    ├── ReportMapper.java                # Report mapper
    └── DashboardConfigMapper.java        # Dashboard configuration mapper
```

### Data Model

#### AnalyticsMetric Entity
- `id` (UUID) - Primary key
- `metricName` (String) - Name of the metric
- `metricType` (MetricType) - Type of metric (PATIENT, CLINICAL, OPERATIONAL, FINANCIAL, etc.)
- `metricValue` (Double) - Metric value
- `unit` (String) - Unit of measurement
- `aggregationType` (AggregationType) - Aggregation type (SUM, AVG, COUNT, MAX, MIN)
- `period` (TimePeriod) - Time period (DAILY, WEEKLY, MONTHLY, YEARLY)
- `periodStart` (LocalDate) - Start of the period
- `periodEnd` (LocalDate) - End of the period
- `dimension1` (String) - First dimension (e.g., department)
- `dimension2` (String) - Second dimension (e.g., doctor)
- `dimension3` (String) - Third dimension (e.g., diagnosis)
- `metadata` (String) - Additional metadata as JSON
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### Report Entity
- `id` (UUID) - Primary key
- `reportName` (String) - Name of the report
- `reportType` (String) - Type of report (PATIENT, FINANCIAL, OPERATIONAL, etc.)
- `reportFormat` (ReportFormat) - Format (PDF, EXCEL, CSV)
- `status` (ReportStatus) - Current status (PENDING, GENERATING, COMPLETED, FAILED)
- `parameters` (String) - Report parameters as JSON
- `generatedBy` (UUID) - User who requested the report
- `generatedAt` (LocalDateTime) - When the report was generated
- `fileUrl` (String) - URL to the generated file
- `fileSize` (Long) - File size in bytes
- `errorMessage` (String) - Error message if generation failed
- `expiresAt` (LocalDateTime) - When the report file expires
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### DashboardConfig Entity
- `id` (UUID) - Primary key
- `dashboardName` (String) - Name of the dashboard
- `dashboardType` (String) - Type of dashboard (EXECUTIVE, OPERATIONAL, etc.)
- `config` (String) - Dashboard configuration as JSON
- `layout` (String) - Dashboard layout configuration
- `widgets` (String) - Widget configurations as JSON
- `isDefault` (Boolean) - Whether this is the default dashboard
- `accessRoles` (String) - Roles that can access this dashboard
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

#### MetricDefinition Entity
- `id` (UUID) - Primary key
- `metricKey` (String) - Unique metric key
- `metricName` (String) - Display name of the metric
- `metricType` (MetricType) - Type of metric
- `description` (String) - Metric description
- `dataSource` (String) - Data source (table/view)
- `query` (String) - SQL query to calculate metric
- `aggregationType` (AggregationType) - Default aggregation type
- `unit` (String) - Unit of measurement
- `category` (String) - Metric category
- `isPublic` (Boolean) - Whether metric is publicly accessible
- `thresholds` (String) - Alert thresholds as JSON
- Audit fields: `createdAt`, `createdBy`, `updatedAt`, `updatedBy`, `deleted`, `version`

---

## API Endpoints

### Dashboard Endpoints

#### Get Executive Dashboard
```http
GET /api/analytics/dashboard/executive?period=MONTHLY&startDate=2026-01-01&endDate=2026-12-31
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "period": "MONTHLY",
    "startDate": "2026-01-01",
    "endDate": "2026-12-31",
    "metrics": {
      "totalPatients": 15234,
      "totalAdmissions": 8456,
      "totalRevenue": 12500000.00,
      "averageLengthOfStay": 4.5,
      "bedOccupancyRate": 78.5,
      "patientSatisfaction": 4.2
    },
    "trends": [
      {
        "metric": "totalPatients",
        "values": [1200, 1350, 1420, 1380, 1450, 1520],
        "labels": ["Jan", "Feb", "Mar", "Apr", "May", "Jun"]
      }
    ]
  }
}
```

#### Get Operational Dashboard
```http
GET /api/analytics/dashboard/operational?period=DAILY&date=2026-06-13
Authorization: Bearer {token}
```

#### Get Financial Dashboard
```http
GET /api/analytics/dashboard/financial?period=MONTHLY&year=2026
Authorization: Bearer {token}
```

### Patient Analytics Endpoints

#### Get Patient Demographics
```http
GET /api/analytics/patient/demographics?groupBy=AGE_GROUP&period=MONTHLY
Authorization: Bearer {token}
```

#### Get Patient Flow Analysis
```http
GET /api/analytics/patient/flow?startDate=2026-01-01&endDate=2026-12-31
Authorization: Bearer {token}
```

#### Get Readmission Rates
```http
GET /api/analytics/patient/readmission?period=MONTHLY&year=2026
Authorization: Bearer {token}
```

#### Get Length of Stay Analysis
```http
GET /api/analytics/patient/length-of-stay?department=ALL&period=MONTHLY
Authorization: Bearer {token}
```

### Clinical Analytics Endpoints

#### Get Diagnosis Trends
```http
GET /api/analytics/clinical/diagnosis?top=10&period=MONTHLY
Authorization: Bearer {token}
```

#### Get Treatment Outcomes
```http
GET /api/analytics/clinical/outcomes?department=ALL&period=QUARTERLY
Authorization: Bearer {token}
```

#### Get Medication Usage
```http
GET /api/analytics/clinical/medication?top=20&period=MONTHLY
Authorization: Bearer {token}
```

### Operational Analytics Endpoints

#### Get Appointment Analytics
```http
GET /api/analytics/operational/appointments?period=MONTHLY&year=2026
Authorization: Bearer {token}
```

#### Get Resource Utilization
```http
GET /api/analytics/operational/utilization?resourceType=BED&period=DAILY
Authorization: Bearer {token}
```

#### Get Wait Time Analysis
```http
GET /api/analytics/operational/wait-times?department=ALL&period=WEEKLY
Authorization: Bearer {token}
```

### Financial Analytics Endpoints

#### Get Revenue Analysis
```http
GET /api/analytics/financial/revenue?groupBy=DEPARTMENT&period=MONTHLY&year=2026
Authorization: Bearer {token}
```

#### Get Expense Analysis
```http
GET /api/analytics/financial/expenses?groupBy=CATEGORY&period=MONTHLY&year=2026
Authorization: Bearer {token}
```

#### Get Profitability Analysis
```http
GET /api/analytics/financial/profitability?period=QUARTERLY&year=2026
Authorization: Bearer {token}
```

### Inventory Analytics Endpoints

#### Get Stock Levels
```http
GET /api/analytics/inventory/stock?category=ALL
Authorization: Bearer {token}
```

#### Get Consumption Patterns
```http
GET /api/analytics/inventory/consumption?period=MONTHLY
Authorization: Bearer {token}
```

#### Get Expiry Management
```http
GET /api/analytics/inventory/expiry?days=30
Authorization: Bearer {token}
```

### HR Analytics Endpoints

#### Get Staff Demographics
```http
GET /api/analytics/hr/demographics?groupBy=ROLE
Authorization: Bearer {token}
```

#### Get Attendance Analysis
```http
GET /api/analytics/hr/attendance?period=MONTHLY&year=2026
Authorization: Bearer {token}
```

#### Get Performance Metrics
```http
GET /api/analytics/hr/performance?department=ALL&period=QUARTERLY
Authorization: Bearer {token}
```

### Report Endpoints

#### Generate Report
```http
POST /api/analytics/reports
Authorization: Bearer {token}
Content-Type: application/json

{
  "reportName": "Monthly Patient Report",
  "reportType": "PATIENT",
  "reportFormat": "PDF",
  "parameters": {
    "startDate": "2026-06-01",
    "endDate": "2026-06-30",
    "groupBy": "DEPARTMENT"
  }
}
```

#### Get Report Status
```http
GET /api/analytics/reports/{reportId}/status
Authorization: Bearer {token}
```

#### Download Report
```http
GET /api/analytics/reports/{reportId}/download
Authorization: Bearer {token}
```

#### Get Report History
```http
GET /api/analytics/reports/history?page=0&size=20
Authorization: Bearer {token}
```

---

## Testing Flow Scenarios

### Scenario 1: Executive Dashboard

**Steps:**
1. Login as admin or user with ANALYTICS_READ permission
2. Request executive dashboard data
3. Verify all key metrics are returned
4. Check trend data for visualization
5. Compare with previous period

**Test Commands:**
```bash
# Login as admin
ADMIN_TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Get executive dashboard
curl -X GET "http://localhost:8080/api/analytics/dashboard/executive?period=MONTHLY&startDate=2026-01-01&endDate=2026-12-31" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Expected: 200 OK with dashboard metrics and trends
```

---

### Scenario 2: Patient Demographics Analysis

**Steps:**
1. Login as user with ANALYTICS_READ permission
2. Request patient demographics grouped by age
3. Verify data is correctly aggregated
4. Request demographics grouped by gender
5. Compare different groupings

**Test Commands:**
```bash
# Login
TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "doctor", "password": "password"}' \
  | jq -r '.data.accessToken')

# Get demographics by age
curl -X GET "http://localhost:8080/api/analytics/patient/demographics?groupBy=AGE_GROUP&period=MONTHLY" \
  -H "Authorization: Bearer $TOKEN"

# Expected: 200 OK with demographic breakdown
```

---

### Scenario 3: Financial Revenue Analysis

**Steps:**
1. Login as user with BILLING_READ permission
2. Request revenue analysis by department
3. Verify revenue totals are correct
4. Request revenue by service type
5. Compare periods for trend analysis

**Test Commands:**
```bash
# Login
TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "billing_officer", "password": "password"}' \
  | jq -r '.data.accessToken')

# Get revenue analysis
curl -X GET "http://localhost:8080/api/analytics/financial/revenue?groupBy=DEPARTMENT&period=MONTHLY&year=2026" \
  -H "Authorization: Bearer $TOKEN"

# Expected: 200 OK with revenue breakdown by department
```

---

### Scenario 4: Report Generation

**Steps:**
1. Login as user with ANALYTICS_READ permission
2. Request a new report generation
3. Check report status
4. Wait for report to complete
5. Download the generated report

**Test Commands:**
```bash
# Login
TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Generate report
REPORT_RESPONSE=$(curl -X POST http://localhost:8080/api/analytics/reports \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "reportName": "Monthly Patient Report",
    "reportType": "PATIENT",
    "reportFormat": "PDF",
    "parameters": {
      "startDate": "2026-06-01",
      "endDate": "2026-06-30",
      "groupBy": "DEPARTMENT"
    }
  }')

REPORT_ID=$(echo $REPORT_RESPONSE | jq -r '.data.id')

# Check status
curl -X GET http://localhost:8080/api/analytics/reports/$REPORT_ID/status \
  -H "Authorization: Bearer $TOKEN"

# Expected: Report status (PENDING, GENERATING, COMPLETED)
```

---

### Scenario 5: Resource Utilization

**Steps:**
1. Login as user with ANALYTICS_READ permission
2. Request bed utilization data
3. Request equipment utilization data
4. Compare utilization across departments
5. Identify underutilized resources

**Test Commands:**
```bash
# Login
TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "Admin@123"}' \
  | jq -r '.data.accessToken')

# Get bed utilization
curl -X GET "http://localhost:8080/api/analytics/operational/utilization?resourceType=BED&period=DAILY" \
  -H "Authorization: Bearer $TOKEN"

# Expected: 200 OK with utilization data
```

---

## Data Aggregation Strategy

### Scheduled Jobs
- **Daily Aggregation** - Runs daily at midnight to aggregate daily metrics
- **Weekly Aggregation** - Runs weekly to aggregate weekly metrics
- **Monthly Aggregation** - Runs monthly to aggregate monthly metrics
- **Quarterly Aggregation** - Runs quarterly to aggregate quarterly metrics

### Aggregation Process
1. Extract raw data from source tables
2. Apply transformations and calculations
3. Aggregate by specified dimensions
4. Store aggregated metrics in analytics tables
5. Update materialized views if applicable

### Performance Optimization
- Use database indexes for fast queries
- Implement caching for frequently accessed metrics
- Use batch processing for large datasets
- Implement query result caching
- Use parallel processing for complex aggregations

---

## Security Considerations

### Access Control
- Analytics endpoints require ANALYTICS_READ permission
- Financial analytics require BILLING_READ permission
- HR analytics require HR_READ permission
- Report generation requires ANALYTICS_WRITE permission

### Data Privacy
- Patient data is anonymized in analytics
- Sensitive financial data is protected
- HR data is restricted to authorized users
- Audit logging for all analytics access

### Rate Limiting
- Analytics queries are rate limited
- Report generation is rate limited
- Large data exports are rate limited
- Complex queries have longer timeouts

---

## Dependencies

### Internal Dependencies
- `patient` - For patient data analytics
- `medical` - For clinical data analytics
- `appointment` - For operational analytics
- `billing` - For financial analytics
- `inventory` - For inventory analytics
- `hr` - For HR analytics
- `doctor` - For clinical analytics
- `nursing` - For nursing analytics
- `laboratory` - For lab analytics
- `auth` - For authentication and authorization
- `common` - For shared utilities

### External Dependencies
- Spring Batch - For batch data processing
- Apache POI - For Excel report generation
- iText - For PDF report generation
- JasperReports - For advanced reporting
- Chart.js / D3.js - For frontend visualization
- Elasticsearch - For advanced search and analytics (optional)

---

## Future Enhancements

### Planned Features
- Real-time streaming analytics
- Machine learning for predictive analytics
- Natural language query interface
- Custom dashboard builder
- Automated anomaly detection and alerting
- Integration with external BI tools (Tableau, Power BI)
- Advanced visualizations and charts
- Mobile analytics dashboard

### Performance Improvements
- Implement columnar database for analytics
- Use materialized views for complex queries
- Implement query optimization
- Add data caching layers
- Use distributed processing for large datasets

---

## Notes

- This module is critical for data-driven decision making
- Consider implementing data warehouse for better analytics performance
- Implement proper data validation and quality checks
- Consider implementing GDPR compliance for patient data analytics
- Regularly review and optimize analytics queries for performance
