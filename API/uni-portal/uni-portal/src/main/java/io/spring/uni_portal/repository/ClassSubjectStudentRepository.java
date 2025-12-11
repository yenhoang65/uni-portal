package io.spring.uni_portal.repository;

import io.spring.uni_portal.dto.ClassSubjectStudent.ClassSubjectStudentRequestDTO;
import io.spring.uni_portal.model.ClassStudent;
import io.spring.uni_portal.model.ClassSubjectStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassSubjectStudentRepository extends JpaRepository<ClassSubjectStudent, Long> {
    long countByClassStudent_ClassStudentId(Long classStudentId);
    boolean existsByClassStudent_ClassStudentIdAndStudent_UserId(Long classStudentId, Long userId);


    List<ClassSubjectStudent> findByStudent_UserId(Long userId);


//    @Query("""
//    SELECT css FROM ClassSubjectStudent css
//    JOIN FETCH css.classStudent cs
//    JOIN FETCH cs.teachingScheduleRequest tsr
//    JOIN FETCH tsr.assignment a
//    JOIN FETCH a.subject s
//    JOIN FETCH a.termClass tc
//    JOIN FETCH tsr.classroom c
//    WHERE css.student.userId = :userId
//    AND cs.status = 'success'
//""")
//    List<ClassSubjectStudent> findRegisteredClassesByUserId(@Param("userId") Long userId);

    @Query("""
    SELECT css FROM ClassSubjectStudent css
    JOIN FETCH css.classStudent cs
    JOIN FETCH cs.teachingScheduleRequest tsr
    JOIN FETCH tsr.assignment a
    JOIN FETCH a.subject s
    JOIN FETCH a.termClass tc
    JOIN FETCH tsr.classroom c
    WHERE css.student.userId = :userId
""")
    List<ClassSubjectStudent> findRegisteredClassesByUserId(@Param("userId") Long userId);


    boolean existsByStudent_UserIdAndClassStudent_TeachingScheduleRequest_Assignment_Subject_SubjectIdAndRegistrationTimeBetween(
            Long studentId, Long subjectId, LocalDateTime startDate, LocalDateTime endDate);


    long countByClassStudent_ClassStudentIdAndStatus(Long classStudentId, String status);

    List<ClassSubjectStudent> findByClassStudent_ClassStudentIdAndStatus(Long classStudentId, String status);


    List<ClassSubjectStudent> findByClassStudent_ClassStudentId(Long classStudentId);


    @Query("SELECT css FROM ClassSubjectStudent css WHERE css.classStudent.classStudentId = :classStudentId AND css.student.userId = :userId")
    Optional<ClassSubjectStudent> findByClassStudentIdAndUserId(Long classStudentId, Long userId);


    @Query("""
        SELECT css FROM ClassSubjectStudent css
        JOIN FETCH css.classStudent cs
        JOIN FETCH cs.teachingScheduleRequest ts
        WHERE css.student.userId = :userId
    """)
    List<ClassSubjectStudent> findByStudentUserId(@Param("userId") Long userId);


    @Query("SELECT DISTINCT css FROM ClassSubjectStudent css " +
            "JOIN FETCH css.classStudent cs " +
            "JOIN FETCH cs.teachingScheduleRequest tsr " +
            "JOIN FETCH tsr.assignment ta " +
            "JOIN FETCH ta.subject s " +
            "WHERE css.student.userId = :studentId AND css.status = 'success' AND cs.status = 'success'")
    List<ClassSubjectStudent> findAllEnrolledSubjects(@Param("studentId") Long studentId);



    @Query("""
    SELECT css FROM ClassSubjectStudent css
    WHERE css.student.userId = :userId
      AND css.classStudent.teachingScheduleRequest.assignment.termClass.progress = :progress
      AND css.classStudent.teachingScheduleRequest.assignment.termClass.semester = :semester
      AND css.classStudent.teachingScheduleRequest.assignment.termClass.schoolyears = :schoolyears
""")
    List<ClassSubjectStudent> findByStudent_UserIdAndClassStudent_TeachingScheduleRequest_Assignment_TermClass_ProgressAndTermClass_SemesterAndTermClass_Schoolyears(
            @Param("userId") Long userId,
            @Param("progress") String progress,
            @Param("semester") String semester,
            @Param("schoolyears") String schoolyears);


    Optional<ClassSubjectStudent> findByClassStudent_ClassStudentIdAndStudent_UserId(Long classStudentId, Long studentId);



    @Query("""
    SELECT new io.spring.uni_portal.dto.ClassSubjectStudent.ClassSubjectStudentRequestDTO(
        cs.classStudentId,
        tc.classname,
        new io.spring.uni_portal.dto.Subject.SubjectDTO(
            s.subjectId,
            s.subjectName,
            s.ltCredits,
            s.thCredits,
            s.subjectDescription,
            s.subjectCoefficient
        )
    )
    FROM ClassSubjectStudent css
    JOIN css.classStudent cs
    JOIN cs.teachingScheduleRequest tsr
    JOIN tsr.assignment ta
    JOIN ta.termClass tc
    JOIN ta.subject s
    WHERE css.student.userId = :studentId
    AND css.status = 'success'
""")
    List<ClassSubjectStudentRequestDTO> findSuccessClassesByStudent(@Param("studentId") Long studentId);


    @Query("""
    SELECT new io.spring.uni_portal.dto.ClassSubjectStudent.ClassSubjectStudentRequestDTO(
        cs.classStudentId,
        tc.classname,
        new io.spring.uni_portal.dto.Subject.SubjectDTO(
            s.subjectId,
            s.subjectName,
            s.ltCredits,
            s.thCredits,
            s.subjectDescription,
            s.subjectCoefficient
        )
    )
    FROM ClassSubjectStudent css
    JOIN css.classStudent cs
    JOIN cs.teachingScheduleRequest tsr
    JOIN tsr.assignment ta
    JOIN ta.termClass tc
    JOIN ta.subject s
    WHERE css.student.userId = :studentId
    AND css.status = 'success'
    AND (:semester IS NULL OR tc.semester = :semester)
    AND (:schoolYear IS NULL OR tc.schoolyears = :schoolYear)
""")
    List<ClassSubjectStudentRequestDTO> findSuccessClassesByStudentAndTerm(
            @Param("studentId") Long studentId,
            @Param("semester") String semester,
            @Param("schoolYear") String schoolYear
    );


}
