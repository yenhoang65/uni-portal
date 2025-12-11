package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.ClassSubjectStudent;
import io.spring.uni_portal.model.GradeEvent;
import io.spring.uni_portal.model.StudentGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentGradeRepository extends JpaRepository<StudentGrade, Long> {

    boolean existsByClassSubjectStudent_ClassSubjectStudentIdAndGradeEvent_GradeEventId(
            Long classSubjectStudentId, Long gradeEventId);

    List<StudentGrade> findByGradeEvent_GradeEventId(Long gradeEventId);

    Optional<StudentGrade> findByClassSubjectStudent_ClassSubjectStudentIdAndGradeEvent_GradeEventId
            (Long classSubjectStudentId, Long gradeEventId);

    @Query("SELECT sg FROM StudentGrade sg JOIN GradeEvent ge ON sg.gradeEvent.id = ge.id " +
            "JOIN ClassSubjectStudent css ON sg.classSubjectStudent.id = css.id " +
            "WHERE css.student.userId = :studentId")
    List<StudentGrade> findAllByStudentId(@Param("studentId") Long studentId);

//    List<StudentGrade> findByClassSubjectStudent(ClassSubjectStudent css);
}
