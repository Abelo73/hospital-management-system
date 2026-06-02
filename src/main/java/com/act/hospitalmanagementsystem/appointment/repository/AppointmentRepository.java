package com.act.hospitalmanagementsystem.appointment.repository;

import com.act.hospitalmanagementsystem.appointment.entity.Appointment;
import com.act.hospitalmanagementsystem.appointment.enums.AppointmentStatus;
import com.act.hospitalmanagementsystem.appointment.enums.AppointmentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    Optional<Appointment> findByIdAndDeletedFalse(UUID id);

    List<Appointment> findByPatientIdAndDeletedFalseOrderByAppointmentDateDescStartTimeAsc(UUID patientId);

    Page<Appointment> findByPatientIdAndDeletedFalse(UUID patientId, Pageable pageable);

    List<Appointment> findByDoctorIdAndDeletedFalseOrderByAppointmentDateDescStartTimeAsc(UUID doctorId);

    Page<Appointment> findByDoctorIdAndDeletedFalse(UUID doctorId, Pageable pageable);

    Page<Appointment> findByStatusAndDeletedFalse(AppointmentStatus status, Pageable pageable);

    Page<Appointment> findByAppointmentTypeAndDeletedFalse(AppointmentType appointmentType, Pageable pageable);

    Page<Appointment> findByPatientIdAndStatusAndDeletedFalse(UUID patientId, AppointmentStatus status, Pageable pageable);

    Page<Appointment> findByDoctorIdAndStatusAndDeletedFalse(UUID doctorId, AppointmentStatus status, Pageable pageable);

    Page<Appointment> findByPatientIdAndDoctorIdAndDeletedFalse(UUID patientId, UUID doctorId, Pageable pageable);

    @Query("SELECT a FROM Appointment a WHERE a.deleted = false AND " +
           "a.appointmentDate = :date AND " +
           "((a.startTime < :endTime AND a.endTime > :startTime)) AND " +
           "a.doctorId = :doctorId AND a.status NOT IN ('CANCELLED', 'NO_SHOW')")
    List<Appointment> findOverlappingAppointments(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("doctorId") UUID doctorId
    );

    @Query("SELECT a FROM Appointment a WHERE a.deleted = false AND " +
           "(LOWER(a.reason) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(a.symptoms) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(a.notes) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Appointment> searchAppointments(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT a FROM Appointment a WHERE a.deleted = false AND a.patientId = :patientId AND " +
           "(LOWER(a.reason) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(a.symptoms) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(a.notes) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Appointment> searchPatientAppointments(@Param("patientId") UUID patientId, @Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT a FROM Appointment a WHERE a.deleted = false AND a.doctorId = :doctorId AND " +
           "(LOWER(a.reason) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(a.symptoms) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(a.notes) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Appointment> searchDoctorAppointments(@Param("doctorId") UUID doctorId, @Param("searchTerm") String searchTerm, Pageable pageable);

    Page<Appointment> findByAppointmentDateBetweenAndDeletedFalse(LocalDate startDate, LocalDate endDate, Pageable pageable);

    List<Appointment> findByAppointmentDateAndDeletedFalse(LocalDate appointmentDate);

    List<Appointment> findByAppointmentDateAndDoctorIdAndDeletedFalse(LocalDate appointmentDate, UUID doctorId);

    @Query("SELECT a FROM Appointment a WHERE a.deleted = false AND a.appointmentDate = :date AND a.doctorId = :doctorId AND a.status NOT IN ('CANCELLED', 'NO_SHOW') ORDER BY a.startTime")
    List<Appointment> findDoctorAppointmentsForDate(@Param("date") LocalDate date, @Param("doctorId") UUID doctorId);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.deleted = false AND a.doctorId = :doctorId AND a.appointmentDate = :date AND a.status NOT IN ('CANCELLED', 'NO_SHOW')")
    Long countDoctorAppointmentsForDate(@Param("doctorId") UUID doctorId, @Param("date") LocalDate date);
}
