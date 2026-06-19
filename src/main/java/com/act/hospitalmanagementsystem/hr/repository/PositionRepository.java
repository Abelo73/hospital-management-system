package com.act.hospitalmanagementsystem.hr.repository;

import com.act.hospitalmanagementsystem.hr.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {

    List<Position> findByDeletedFalseOrderByTitleAsc();

    List<Position> findByDepartmentIdAndDeletedFalse(UUID departmentId);

    List<Position> findByIsActiveAndDeletedFalse(boolean isActive);

    boolean existsByCodeAndDeletedFalse(String code);
}
