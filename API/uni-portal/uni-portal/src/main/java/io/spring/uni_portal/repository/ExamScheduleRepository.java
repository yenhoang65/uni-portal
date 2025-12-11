package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.ExamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, Long> {
    @Query("SELECT es FROM ExamSchedule es WHERE es.classStudent.id = :classStudentId")
    List<ExamSchedule> findByClassStudentId(@Param("classStudentId") Long classStudentId);

    @Query("SELECT es FROM ExamSchedule es WHERE es.classroom.id = :classroomId AND es.startDate = :startDate")
    List<ExamSchedule> findByClassroomIdAndStartDate(@Param("classroomId") Long classroomId, @Param("startDate") LocalDate startDate);

//    @Query("""
//    SELECT es FROM ExamSchedule es
//    JOIN ClassSubjectStudent css ON css.classStudent = es.classStudent
//    WHERE css.student.user.userId = :userId
//""")
//    List<ExamSchedule> findAllByStudentUserId(@Param("userId") Long userId);

    @Query("""
    SELECT es FROM ExamSchedule es
    JOIN ClassSubjectStudent css ON css.classStudent = es.classStudent
    JOIN css.classStudent.teachingScheduleRequest tsr
    JOIN tsr.assignment a
    JOIN a.termClass tc
    WHERE css.student.user.userId = :userId
    AND (:semester IS NULL OR tc.semester = :semester)
    AND (:schoolyear IS NULL OR tc.schoolyears = :schoolyear)
""")
    List<ExamSchedule> findAllByStudentUserIdAndTerm(
            @Param("userId") Long userId,
            @Param("semester") String semester,
            @Param("schoolyear") String schoolyear
    );

    @Query("""
    SELECT es FROM ExamSchedule es
    JOIN es.classStudent cs
    JOIN cs.teachingScheduleRequest tsr
    JOIN tsr.assignment a
    JOIN a.termClass tc
    WHERE (:semester IS NULL OR tc.semester = :semester)
      AND (:schoolyear IS NULL OR tc.schoolyears = :schoolyear)
""")
    List<ExamSchedule> findAllBySemesterAndSchoolyear(
            @Param("semester") String semester,
            @Param("schoolyear") String schoolyear
    );


    @Query("SELECT e FROM ExamSchedule e WHERE e.classStudent.classStudentId = :classStudentId AND e.gradeType.gradeTypeId = :gradeTypeId")
    ExamSchedule findByClassStudentIdAndGradeTypeId(@Param("classStudentId") Long classStudentId, @Param("gradeTypeId") Long gradeTypeId);

    Optional<ExamSchedule> findByClassStudent_ClassStudentIdAndGradeType_GradeTypeId(Long classStudentId, Long gradeTypeId);

}
