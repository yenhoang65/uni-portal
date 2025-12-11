package io.spring.uni_portal.repository;

import io.spring.uni_portal.dto.TeachingSlide.AttendanceSessionWithSlideResponseDTO;
import io.spring.uni_portal.model.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {
    List<AttendanceSession> findByClassStudent_ClassStudentId(Long classStudentId);

    @Query("SELECT as FROM AttendanceSession as WHERE DATE(as.scheduledDate) = :date AND as.classStudent.teachingScheduleRequest.classroom.id = :classroomId")
    List<AttendanceSession> findByScheduledDateAndClassroomId(@Param("date") LocalDate date, @Param("classroomId") Long classroomId);

    @Query("""
    SELECT s FROM AttendanceSession s
    JOIN FETCH s.classStudent cs
    JOIN FETCH cs.teachingScheduleRequest tsr
    JOIN FETCH tsr.assignment a
    JOIN FETCH a.termClass tc
    JOIN FETCH a.subject subj
    JOIN FETCH a.lecturer l
    JOIN FETCH l.user u
    JOIN FETCH tsr.classroom cr
    WHERE s.scheduledDate >= :start AND s.scheduledDate < :end
""")
    List<AttendanceSession> findByScheduledDateBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );


    @Query("""
    SELECT new io.spring.uni_portal.dto.TeachingSlide.AttendanceSessionWithSlideResponseDTO(
        s.sessionId,
        s.scheduledDate,
        slide.title,
        slide.fileUrl,
        slide.teachingSlideId
    )
    FROM AttendanceSession s
    LEFT JOIN TeachingSlide slide ON slide.session.sessionId = s.sessionId
    WHERE s.classStudent.classStudentId = :classStudentId
""")
    List<AttendanceSessionWithSlideResponseDTO> findSessionsWithSlideByClassStudentId(@Param("classStudentId") Long classStudentId);



}
