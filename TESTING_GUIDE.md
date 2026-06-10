# Testing Guide for Hospital Management System

## Overview

This document provides comprehensive information about the testing strategy, test structure, and how to run tests for the Hospital Management System.

## Test Structure

```
src/test/java/com/act/hospitalmanagementsystem/
├── config/
│   └── TestConfig.java                    # Test configuration
├── integration/
│   └── AuthIntegrationTest.java           # Integration tests
├── auth/
│   ├── controller/
│   │   └── AuthControllerTest.java        # Auth controller tests
│   ├── repository/
│   │   └── UserRepositoryTest.java        # User repository tests
│   └── service/
│       ├── AuthServiceTest.java           # Auth service tests
│       └── JwtServiceTest.java            # JWT service tests
├── patient/
│   ├── controller/
│   │   └── PatientControllerTest.java     # Patient controller tests
│   └── service/
│       └── PatientServiceTest.java        # Patient service tests
├── appointment/
│   └── service/
│       └── AppointmentServiceTest.java    # Appointment service tests
├── doctor/
│   └── service/
│       └── ConsultationServiceTest.java   # Consultation service tests
└── HospitalManagementSystemApplicationTests.java  # Main application test
```

## Test Configuration

### Test Profile
Tests use a dedicated test profile configured in `src/test/resources/application-test.properties`:

- **Database**: H2 in-memory database for fast test execution
- **Flyway**: Disabled (uses `ddl-auto=create-drop` for schema management)
- **Redis**: Disabled (uses in-memory caching)
- **Mail**: Disabled (no external dependencies)
- **MinIO**: Disabled (uses local file storage)

### Test Dependencies
The following test dependencies are included in `pom.xml`:
- `spring-boot-starter-test`: Core Spring Boot testing support
- `spring-security-test`: Security testing support
- `testcontainers`: Integration testing with real containers
- `junit-jupiter`: JUnit 5 testing framework
- `mockito`: Mocking framework

## Running Tests

### Run All Tests
```bash
./mvnw test
```

### Run Specific Test Class
```bash
./mvnw test -Dtest=AuthServiceTest
```

### Run Tests in Specific Package
```bash
./mvnw test -Dtest=com.act.hospitalmanagementsystem.auth.*
```

### Run Tests with Coverage Report
```bash
./mvnw clean test jacoco:report
```

The coverage report will be generated in `target/site/jacoco/index.html`.

### Run Integration Tests Only
```bash
./mvnw test -Dtest=*IntegrationTest
```

### Run Unit Tests Only
```bash
./mvnw test -Dtest=*Test -Dtest=!*IntegrationTest
```

## Test Categories

### Unit Tests
Unit tests test individual components in isolation using mocks:
- **Service Tests**: Test business logic with mocked repositories
- **Controller Tests**: Test HTTP endpoints with mocked services
- **Repository Tests**: Test database operations with test database
- **Utility Tests**: Test utility classes and helpers

### Integration Tests
Integration tests test the interaction between multiple components:
- **AuthIntegrationTest**: Tests the complete authentication flow (register → login → logout)
- **API Integration Tests**: Test API endpoints with real database

## Test Coverage Goals

- **Overall Coverage**: > 70%
- **Service Layer**: > 80%
- **Controller Layer**: > 75%
- **Repository Layer**: > 85%

## Writing New Tests

### Service Test Template
```java
@ExtendWith(MockitoExtension.class)
class YourServiceTest {

    @Mock
    private YourRepository repository;

    @Mock
    private YourMapper mapper;

    @InjectMocks
    private YourService service;

    @BeforeEach
    void setUp() {
        // Setup test data
    }

    @Test
    void testYourMethod_Success() {
        // Given
        when(repository.findById(any())).thenReturn(Optional.of(testEntity));

        // When
        var result = service.yourMethod(testId);

        // Then
        assertNotNull(result);
        verify(repository, times(1)).findById(testId);
    }
}
```

### Controller Test Template
```java
@WebMvcTest(YourController.class)
class YourControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private YourService service;

    @Test
    @WithMockUser
    void testYourEndpoint_Success() throws Exception {
        when(service.yourMethod(any())).thenReturn(testDTO);

        mockMvc.perform(post("/your-endpoint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
```

### Integration Test Template
```java
@SpringBootTest(classes = HospitalManagementSystemApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class YourIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testYourFlow() throws Exception {
        // Test complete flow with real database
    }
}
```

## Best Practices

### 1. Test Naming
- Use descriptive test names: `testMethod_Scenario_ExpectedResult`
- Example: `testLogin_Success`, `testLogin_InvalidCredentials`

### 2. Test Structure (Given-When-Then)
```java
@Test
void testLogin_Success() {
    // Given - Setup test data and mocks
    when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

    // When - Execute the method being tested
    LoginResponse response = authService.login(loginRequest);

    // Then - Verify the results
    assertNotNull(response);
    assertEquals("accessToken", response.getAccessToken());
}
```

### 3. Use Appropriate Assertions
- Use specific assertions: `assertEquals`, `assertTrue`, `assertNotNull`
- Avoid generic assertions like `assertTrue(result != null)`

### 4. Mock External Dependencies
- Mock repositories in service tests
- Mock services in controller tests
- Use real database in integration tests

### 5. Keep Tests Independent
- Each test should be independent of others
- Use `@BeforeEach` to set up fresh test data
- Avoid shared state between tests

### 6. Test Edge Cases
- Test success scenarios
- Test failure scenarios
- Test edge cases (null values, empty strings, etc.)
- Test validation errors

### 7. Use @Transactional for Integration Tests
- Rollback changes after each test
- Keep database clean between tests

## Common Test Scenarios

### Authentication Flow
```java
@Test
void testRegisterAndLoginFlow() throws Exception {
    // Register new user
    mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(registerRequest)))
            .andExpect(status().isOk());

    // Login with registered user
    mockMvc.perform(post("/auth/login")
            .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.accessToken").exists());
}
```

### CRUD Operations
```java
@Test
void testCreateReadUpdateDelete() {
    // Create
    PatientDTO created = patientService.createPatient(createRequest);
    assertNotNull(created.getId());

    // Read
    PatientDTO read = patientService.getPatientById(created.getId());
    assertEquals(created.getId(), read.getId());

    // Update
    PatientDTO updated = patientService.updatePatient(created.getId(), updateRequest);
    assertEquals("Jane", updated.getFirstName());

    // Delete
    patientService.deletePatient(created.getId());
    assertThrows(Exception.class, () -> patientService.getPatientById(created.getId()));
}
```

## Troubleshooting

### Test Failures

**Issue**: Tests fail with database connection errors
- **Solution**: Ensure PostgreSQL is running or use H2 in-memory database for tests

**Issue**: Tests fail with authentication errors
- **Solution**: Use `@WithMockUser` annotation or set up proper authentication in tests

**Issue**: Tests fail with Flyway migration errors
- **Solution**: Disable Flyway in test profile or ensure migrations are compatible

**Issue**: Tests are slow
- **Solution**: Use H2 in-memory database, mock external services, run tests in parallel

### Coverage Issues

**Issue**: Low coverage in service layer
- **Solution**: Add more unit tests for business logic

**Issue**: Low coverage in controller layer
- **Solution**: Add more controller tests with various request scenarios

**Issue**: Low coverage in repository layer
- **Solution**: Add repository tests for custom queries

## Continuous Integration

Tests are configured to run automatically in CI/CD pipeline:
- Run on every pull request
- Run on every merge to main branch
- Fail build if coverage drops below threshold
- Generate coverage reports for analysis

## Next Steps

To improve test coverage:
1. Add tests for remaining services (MedicalRecordService, NursingService, etc.)
2. Add more integration tests for complex workflows
3. Add performance tests for critical endpoints
4. Add security tests for authorization
5. Add contract tests for API documentation

## Resources

- [Spring Boot Testing Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Testcontainers Documentation](https://www.testcontainers.org/)
