package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainingRepository extends JpaRepository<Training, UUID> {

    List<Training> findByStatus(String status);

    List<Training> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
}
