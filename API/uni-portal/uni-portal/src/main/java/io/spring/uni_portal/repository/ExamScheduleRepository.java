package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.ExamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, Long> {
    @Query("SELECT es FROM ExamSchedule es WHERE es.classStudent.id = :classStudentId")
    List<ExamSchedule> findByClassStudentId(@Param("classStudentId") Long classStudentId);

    @Query("SELECT es FROM ExamSchedule es WHERE es.classroom.id = :classroomId AND es.startDate = :startDate")
    List<ExamSchedule> findByClassroomIdAndStartDate(@Param("classroomId") Long classroomId, @Param("startDate") LocalDate startDate);
}
