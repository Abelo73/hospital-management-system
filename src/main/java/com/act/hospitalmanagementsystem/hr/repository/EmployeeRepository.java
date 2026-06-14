package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.Employee;
import com.act.hospitalmanagementsystem.hr.enums.EmployeeStatus;
import com.act.hospitalmanagementsystem.hr.enums.EmployeeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByEmployeeNumber(String employeeNumber);

    Optional<Employee> findByEmail(String email);

    Page<Employee> findByEmployeeType(EmployeeType employeeType, Pageable pageable);

    Page<Employee> findByDepartment(String department, Pageable pageable);

    Page<Employee> findByStatus(EmployeeStatus status, Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE e.deleted = false AND (e.firstName LIKE %:query% OR e.lastName LIKE %:query% OR e.email LIKE %:query%)")
    Page<Employee> searchEmployees(@Param("query") String query, Pageable pageable);
}
