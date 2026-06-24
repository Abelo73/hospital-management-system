package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TrainingRepository extends JpaRepository<Training, UUID> {

    Page<Training> findByStatus(String status, Pageable pageable);

    List<Training> findByStatus(String status);

    Page<Training> findByStartDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    List<Training> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
}
