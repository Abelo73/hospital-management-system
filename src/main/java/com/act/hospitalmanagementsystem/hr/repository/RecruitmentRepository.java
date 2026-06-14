package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, UUID> {

    Page<Recruitment> findByStatus(String status, Pageable pageable);

    Page<Recruitment> findByDepartment(String department, Pageable pageable);

    List<Recruitment> findByClosingDateAfter(java.time.LocalDate date);
}
