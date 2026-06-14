package com.act.hospitalmanagementsystem.hr.mapper;

import com.act.hospitalmanagementsystem.hr.dto.PerformanceReviewDTO;
import com.act.hospitalmanagementsystem.hr.entity.PerformanceReview;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PerformanceReviewMapper {

    public PerformanceReviewDTO toDTO(PerformanceReview performanceReview) {
        if (performanceReview == null) {
            return null;
        }

        PerformanceReviewDTO dto = new PerformanceReviewDTO();
        dto.setId(performanceReview.getId());
        dto.setEmployeeId(performanceReview.getEmployeeId());
        dto.setReviewerId(performanceReview.getReviewerId());
        dto.setReviewPeriodStart(performanceReview.getReviewPeriodStart());
        dto.setReviewPeriodEnd(performanceReview.getReviewPeriodEnd());
        dto.setReviewDate(performanceReview.getReviewDate());
        dto.setRating(performanceReview.getRating());
        dto.setGoalsAchieved(performanceReview.getGoalsAchieved());
        dto.setAreasForImprovement(performanceReview.getAreasForImprovement());
        dto.setStrengths(performanceReview.getStrengths());
        dto.setComments(performanceReview.getComments());
        dto.setEmployeeComments(performanceReview.getEmployeeComments());
        dto.setDevelopmentPlan(performanceReview.getDevelopmentPlan());
        dto.setCreatedAt(performanceReview.getCreatedAt());
        dto.setCreatedBy(performanceReview.getCreatedBy());
        return dto;
    }

    public List<PerformanceReviewDTO> toDTOList(List<PerformanceReview> performanceReviews) {
        return performanceReviews.stream().map(this::toDTO).toList();
    }
}
