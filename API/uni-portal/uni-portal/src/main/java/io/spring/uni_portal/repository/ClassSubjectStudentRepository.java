package io.spring.uni_portal.repository;

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


}
