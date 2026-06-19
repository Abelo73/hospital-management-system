package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.SalaryGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SalaryGradeRepository extends JpaRepository<SalaryGrade, UUID> {

    List<SalaryGrade> findByDeletedFalseOrderByNameAsc();

    boolean existsByCodeAndDeletedFalse(String code);
}
