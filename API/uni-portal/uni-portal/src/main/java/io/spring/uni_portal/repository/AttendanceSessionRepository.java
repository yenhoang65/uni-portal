package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {
    List<AttendanceSession> findByClassStudent_ClassStudentId(Long classStudentId);

    @Query("SELECT as FROM AttendanceSession as WHERE DATE(as.scheduledDate) = :date AND as.classStudent.teachingScheduleRequest.classroom.id = :classroomId")
    List<AttendanceSession> findByScheduledDateAndClassroomId(@Param("date") LocalDate date, @Param("classroomId") Long classroomId);
}
