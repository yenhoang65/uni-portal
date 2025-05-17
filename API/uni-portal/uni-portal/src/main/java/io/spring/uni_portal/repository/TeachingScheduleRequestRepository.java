package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.model.TeachingScheduleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TeachingScheduleRequestRepository extends JpaRepository<TeachingScheduleRequest, Long> {
    TeachingScheduleRequest findByAssignment(TeachingAssignment assignment);
    boolean existsByAssignment_AssignmentId(Long assignmentId);
    Optional<TeachingScheduleRequest> findByAssignment_AssignmentId(Long assignmentId);
    @Query("SELECT tsr FROM TeachingScheduleRequest tsr " +
            "JOIN tsr.assignment a " +
            "JOIN a.lecturer l " +
            "JOIN a.subject s " +
            "WHERE s.subjectName LIKE %:searchValue% " +
            "OR l.user.userName LIKE %:searchValue% " +  // Truy vấn username từ User
            "OR tsr.lesson LIKE %:searchValue%")
    Page<TeachingScheduleRequest> findBySearchValue(Pageable pageable, @Param("searchValue") String searchValue);


    List<TeachingScheduleRequest> findAllByAssignment_AssignmentId(Long assignmentId);


    @Query(value = "SELECT * FROM teaching_schedule_request tsr " +
            "WHERE tsr.classroom_id = :classroomId " +
            "AND EXTRACT(DOW FROM tsr.date_time) = EXTRACT(DOW FROM CAST(:dateTime AS TIMESTAMP)) " +
            "AND (tsr.date_time <= :endDate AND tsr.end_date >= :dateTime)", nativeQuery = true)
    List<TeachingScheduleRequest> findConflictingSchedules(
            @Param("classroomId") Long classroomId,
            @Param("dateTime") LocalDateTime dateTime,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT tsr FROM TeachingScheduleRequest tsr WHERE tsr.classroom.id = :classroomId " +
            "AND tsr.dateTime BETWEEN :startDate AND :endDate AND tsr.id != :scheduleId")
    List<TeachingScheduleRequest> findByClassroomIdAndDateTimeBetweenAndIdNot(
            @Param("classroomId") Long classroomId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("scheduleId") Long scheduleId);



    @Query("SELECT tsr FROM TeachingScheduleRequest tsr " +
            "WHERE tsr.assignment.lecturer.user.userId = :userId " +
            "AND tsr.status IN :statuses")
    Page<TeachingScheduleRequest> findByLecturerAndStatus(
            @Param("userId") Long userId,
            @Param("statuses") List<String> statuses,
            Pageable pageable);

    @Query("SELECT tsr FROM TeachingScheduleRequest tsr " +
            "WHERE tsr.assignment.lecturer.user.userId = :userId " +
            "AND tsr.status IN :statuses " +
            "AND (LOWER(tsr.assignment.subject.subjectName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(tsr.assignment.termClass.classname) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<TeachingScheduleRequest> findByLecturerAndStatusAndSearchValue(
            @Param("userId") Long userId,
            @Param("statuses") List<String> statuses,
            @Param("search") String search,
            Pageable pageable);


    @Query("SELECT t FROM TeachingScheduleRequest t WHERE t.assignment.lecturer.userId = :userId AND t.status = 'success'")
    List<TeachingScheduleRequest> findByLecturerAndSuccessStatus(@Param("userId") Long userId);



    @Query("SELECT r FROM TeachingScheduleRequest r " +
            "WHERE r.assignment.lecturer.userId = :lecturerId " +
            "AND r.dateTime BETWEEN :startOfDay AND :endOfDay")
    List<TeachingScheduleRequest> findByLecturerIdAndDate(
            @Param("lecturerId") Long lecturerId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );


}
