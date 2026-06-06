# Security Testing Guide for Hospital Management System

## Overview
This guide provides comprehensive testing procedures for the security fixes implemented in the Hospital Management System.

## Pre-Testing Setup

### 1. Environment Configuration
```bash
# Copy the example environment file
cp .env.example .env

# Edit .env with your actual values
nano .env
```

### 2. Required Services
Ensure the following services are running:
- PostgreSQL (default: localhost:5432)
- Redis (default: localhost:6379)
- MinIO (default: localhost:9000)

### 3. Generate Secure JWT Secret
```bash
# Generate a secure 256-bit secret key
openssl rand -base64 32
```

## Security Fixes Implemented

### 1. Environment Variable Configuration
**What was fixed:**
- Database credentials moved to environment variables
- JWT secret moved to environment variables
- MinIO credentials moved to environment variables
- CORS configuration moved to environment variables
- Actuator endpoints secured

**How to test:**

#### Test 1: Verify Environment Variables are Loaded
```bash
# Start the application
./mvnw spring-boot:run

# Check logs for environment variable usage
# Look for: "Using environment variable for DB_HOST"
```

#### Test 2: Verify Actuator Endpoints are Secured
```bash
# Test that sensitive endpoints are NOT accessible
curl -X GET http://localhost:8080/api/actuator/env
# Expected: 403 Forbidden or 404 Not Found

curl -X GET http://localhost:8080/api/actuator/beans
# Expected: 403 Forbidden or 404 Not Found

# Test that safe endpoints ARE accessible
curl -X GET http://localhost:8080/api/actuator/health
# Expected: 200 OK with health status

curl -X GET http://localhost:8080/api/actuator/metrics
# Expected: 200 OK with metrics

curl -X GET http://localhost:8080/api/actuator/prometheus
# Expected: 200 OK with Prometheus metrics
```

#### Test 3: Verify CORS Configuration from Environment
```bash
# Set custom CORS origins in .env
CORS_ALLOWED_ORIGINS=http://localhost:3000,http://custom-domain.com

# Restart application
./mvnw spring-boot:run

# Test CORS with curl
curl -X OPTIONS http://localhost:8080/api/auth/login \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: POST" \
  -v
# Expected: Access-Control-Allow-Origin header present

# Test with disallowed origin
curl -X OPTIONS http://localhost:8080/api/auth/login \
  -H "Origin: http://malicious-site.com" \
  -H "Access-Control-Request-Method: POST" \
  -v
# Expected: No Access-Control-Allow-Origin header
```

### 2. Database DDL Strategy
**What was fixed:**
- Changed from `ddl-auto: update` to `ddl-auto: validate` (configurable via environment)

**How to test:**

#### Test 4: Verify DDL Strategy
```bash
# Set DDL_AUTO to validate in .env
DDL_AUTO=validate

# Start application
./mvnw spring-boot:run

# Expected: Application starts if schema matches
# If schema doesn't match, application fails with validation error

# For development, you can still use update:
DDL_AUTO=update
```

## Security Testing Checklist

### Authentication & Authorization
- [ ] Test login with valid credentials
- [ ] Test login with invalid credentials (should fail)
- [ ] Test access to protected endpoints without token (should fail)
- [ ] Test access to protected endpoints with valid token (should succeed)
- [ ] Test token expiration
- [ ] Test refresh token mechanism

### Input Validation
- [ ] Test SQL injection attempts
- [ ] Test XSS attempts in form fields
- [ ] Test CSRF protection (if implemented)
- [ ] Test file upload validation

### Session Management
- [ ] Test session timeout
- [ ] Test concurrent session handling
- [ ] Test logout functionality
- [ ] Verify tokens are invalidated after logout

### API Security
- [ ] Test rate limiting (if implemented)
- [ ] Test API versioning
- [ ] Test request size limits
- [ ] Test response size limits

### Data Protection
- [ ] Verify sensitive data is encrypted at rest
- [ ] Verify data is encrypted in transit (HTTPS)
- [ ] Test for information disclosure in error messages
- [ ] Verify audit logs are properly maintained

## Automated Security Testing

### Using OWASP ZAP
```bash
# Install OWASP ZAP
# Run automated scan against your application
zap-cli quick-scan --self-contained --start-options '-config api.disablekey=true' \
  http://localhost:8080/api
```

### Using Security Headers Scanner
```bash
# Install securityheaders
npx -y securityheaders scan http://localhost:8080/api
```

### Using SQLMap
```bash
# Test for SQL injection vulnerabilities
sqlmap -u "http://localhost:8080/api/patients/1" \
  --cookie="accessToken=your_token_here" \
  --level=3 --risk=2
```

## Manual Security Testing

### Test 1: JWT Secret Verification
```bash
# Extract JWT from login response
TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin@123"}' \
  | jq -r '.data.accessToken')

# Decode JWT (without verification)
echo $TOKEN | cut -d'.' -f2 | base64 -d | jq

# Verify the secret used matches your environment variable
# If you can decode it without the secret, it's not properly signed
```

### Test 2: Database Connection Security
```bash
# Test that database credentials are not exposed in logs
./mvnw spring-boot:run 2>&1 | grep -i password

# Expected: No passwords in logs
```

### Test 3: Environment Variable Leakage
```bash
# Check that environment variables are not exposed via API
curl http://localhost:8080/api/actuator/env 2>&1 | grep -i password
# Expected: 403 Forbidden or 404 Not Found

curl http://localhost:8080/api/actuator/beans 2>&1 | grep -i password
# Expected: 403 Forbidden or 404 Not Found
```

## Frontend Security Testing

### Test 4: Token Storage Verification
```bash
# Open browser DevTools (F12)
# Go to Application tab > Local Storage
# Check if tokens are stored in localStorage (security risk)
# Check if tokens are stored in cookies (more secure)
```

### Test 5: XSS Protection
```html
<!-- Test XSS in input fields -->
<script>alert('XSS')</script>
<img src=x onerror=alert('XSS')>
```

### Test 6: CSRF Token
```bash
# Check if CSRF tokens are included in forms
# Look for _csrf parameter in form submissions
```

## Performance & Load Testing

### Using Apache Bench
```bash
# Test login endpoint under load
ab -n 1000 -c 10 -p login.json -T application/json \
  http://localhost:8080/api/auth/login
```

### Using JMeter
```bash
# Create JMeter test plan for:
# - User registration
# - Login
# - Token refresh
# - API access with token
```

## Continuous Security Testing

### GitHub Actions Integration
Create `.github/workflows/security-scan.yml`:
```yaml
name: Security Scan
on: [push, pull_request]
jobs:
  security:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Run Trivy vulnerability scanner
        uses: aquasecurity/trivy-action@master
        with:
          scan-type: 'fs'
          scan-ref: '.'
          format: 'sarif'
          output: 'trivy-results.sarif'
```

## Post-Deployment Verification

### Production Checklist
- [ ] All secrets are in environment variables or secret manager
- [ ] DDL_AUTO is set to 'validate' or 'none'
- [ ] Actuator sensitive endpoints are not exposed
- [ ] CORS is configured for production domains only
- [ ] HTTPS is enforced
- [ ] Security headers are configured
- [ ] Rate limiting is enabled
- [ ] Logging is configured for security events
- [ ] Backup and recovery procedures are tested
- [ ] Incident response plan is in place

## Reporting Security Issues

If you discover a security vulnerability:
1. Do not disclose it publicly
2. Report it to the security team
3. Provide steps to reproduce
4. Include impact assessment
5. Suggested remediation

## Additional Resources
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [OWASP ZAP](https://www.zaproxy.org/)
- [Security Headers](https://securityheaders.com/)
