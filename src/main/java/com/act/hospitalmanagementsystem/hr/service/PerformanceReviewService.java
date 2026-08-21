package com.act.hospitalmanagementsystem.hr.service;

import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.act.hospitalmanagementsystem.hr.dto.PerformanceReviewDTO;
import com.act.hospitalmanagementsystem.hr.entity.PerformanceReview;
import com.act.hospitalmanagementsystem.hr.enums.PerformanceRating;
import com.act.hospitalmanagementsystem.hr.mapper.PerformanceReviewMapper;
import com.act.hospitalmanagementsystem.hr.repository.PerformanceReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceReviewService {

    private final PerformanceReviewRepository performanceReviewRepository;
    private final PerformanceReviewMapper performanceReviewMapper;

    @Transactional
    public BaseResponseDTO<PerformanceReviewDTO> createPerformanceReview(UUID employeeId, UUID reviewerId,
            String reviewPeriodStart, String reviewPeriodEnd, String reviewDate, PerformanceRating rating,
            String goalsAchieved, String areasForImprovement, String strengths, String comments, String createdBy) {
        try {
            PerformanceReview review = new PerformanceReview();
            review.setEmployeeId(employeeId);
            review.setReviewerId(reviewerId);
            review.setReviewPeriodStart(java.time.LocalDate.parse(reviewPeriodStart));
            review.setReviewPeriodEnd(java.time.LocalDate.parse(reviewPeriodEnd));
            review.setReviewDate(java.time.LocalDate.parse(reviewDate));
            review.setRating(rating);
            review.setGoalsAchieved(goalsAchieved);
            review.setAreasForImprovement(areasForImprovement);
            review.setStrengths(strengths);
            review.setComments(comments);
            review.setCreatedBy(createdBy);

            PerformanceReview saved = performanceReviewRepository.save(review);
            return BaseResponseDTO.success("Performance review created successfully", performanceReviewMapper.toDTO(saved));
        } catch (Exception e) {
            log.error("Error creating performance review", e);
            return BaseResponseDTO.error("Failed to create performance review: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<List<PerformanceReviewDTO>> getPerformanceReviews(UUID employeeId, UUID reviewerId,
            String startDate, String endDate, Pageable pageable) {
        try {
            Page<PerformanceReview> reviews;
            if (employeeId != null) {
                reviews = performanceReviewRepository.findByEmployeeId(employeeId, pageable);
            } else if (reviewerId != null) {
                reviews = performanceReviewRepository.findByReviewerId(reviewerId, pageable);
            } else if (startDate != null && endDate != null) {
                reviews = performanceReviewRepository.findByReviewPeriodBetween(
                        java.time.LocalDate.parse(startDate), java.time.LocalDate.parse(endDate), pageable);
            } else {
                reviews = performanceReviewRepository.findAll(pageable);
            }
            return BaseResponseDTO.success("Performance reviews retrieved", performanceReviewMapper.toDTOList(reviews.getContent()));
        } catch (Exception e) {
            log.error("Error getting performance reviews", e);
            return BaseResponseDTO.error("Failed to get performance reviews: " + e.getMessage());
        }
    }
}
