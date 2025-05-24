package io.spring.uni_portal.repository;

import io.spring.uni_portal.dto.ClassStudent.ClassStudentDTO;
import io.spring.uni_portal.dto.ClassStudent.ClassStudentRequestDTO;
import io.spring.uni_portal.dto.ClassStudent.ClassStudentResponseDTO;
import io.spring.uni_portal.dto.ClassStudent.OpenedClassFullDTO;
import io.spring.uni_portal.model.ClassStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClassStudentRepository extends JpaRepository<ClassStudent, Long> {
    // Lấy tất cả bản ghi với phân trang và tìm kiếm
    @Query(value = "SELECT cs.* " +
            "FROM class_student cs " +
            "JOIN teaching_schedule_request tsr ON cs.schedule_id = tsr.schedule_id " +
            "WHERE :searchValue IS NULL OR cs.status LIKE CONCAT('%', :searchValue, '%') " +
            "OR cs.materials LIKE CONCAT('%', :searchValue, '%')", nativeQuery = true)
    Page<ClassStudent> findAllBySearchValue(@Param("searchValue") String searchValue, Pageable pageable);

    // Lấy bản ghi cho student với phân trang và tìm kiếm
    @Query(value = "SELECT cs.* " +
            "FROM class_student cs " +
            "JOIN teaching_schedule_request tsr ON cs.schedule_id = tsr.schedule_id " +
            "JOIN teaching_assignment ta ON tsr.assignment_id = ta.assignment_id " +
            "JOIN subject s ON ta.subject_id = s.subject_id " +
            "JOIN intermediary i ON s.subject_id = i.subject_id " +
            "JOIN class c ON i.training_program_id = c.training_program_id " +
            "WHERE cs.student_id = (SELECT user_id FROM student WHERE user_id = :userId) " +
            "AND cs.class_id = c.class_id " +
            "AND (:searchValue IS NULL OR cs.status LIKE CONCAT('%', :searchValue, '%') " +
            "OR cs.materials LIKE CONCAT('%', :searchValue, '%'))", nativeQuery = true)
    Page<ClassStudent> findClassStudentsForStudent(@Param("userId") Long userId,
                                                   @Param("searchValue") String searchValue,
                                                   Pageable pageable);

    @Query("""
    SELECT cs FROM ClassStudent cs
    JOIN FETCH cs.teachingScheduleRequest tsr
    JOIN FETCH tsr.assignment a
    JOIN FETCH a.termClass
    JOIN FETCH a.subject
    JOIN FETCH a.lecturer l
    JOIN FETCH l.user
    WHERE a.subject.subjectId = :subjectId
""")
    List<ClassStudent> findFullOpenedClassesBySubjectId(@Param("subjectId") Long subjectId);


    @Query("""
    SELECT cs FROM ClassStudent cs
    WHERE cs.registrationPeriod.id = :periodId
""")
    List<ClassStudent> findAllByRegistrationPeriodId(@Param("periodId") Long periodId);

    List<ClassStudent> findByTeachingScheduleRequest_Status(String status);

    List<ClassStudent> findByStatus(String status);

    @Query("SELECT cs FROM ClassStudent cs WHERE cs.status = 'success' AND cs.teachingScheduleRequest.classroom.id = :classroomId")
    List<ClassStudent> findSuccessByClassroomId(@Param("classroomId") Long classroomId);

    Optional<ClassStudent> findById(Long id);

    @Query(value = "SELECT cs.class_student_id AS classStudentId, tc.classname AS className, s.subject_name AS subjectName " +
            "FROM class_student cs " +
            "JOIN teaching_schedule_request tsr ON cs.schedule_id = tsr.schedule_id " +
            "JOIN teaching_assignment ta ON tsr.assignment_id = ta.assignment_id " +
            "JOIN term_class tc ON ta.termclass_id = tc.termclass_id " +
            "JOIN subject s ON ta.subject_id = s.subject_id " +
            "WHERE cs.status = 'success' AND ta.user_id = :lecturerId", nativeQuery = true)
    List<ClassStudentRequestDTO> findAllSuccessfulClassesByLecturerId(@Param("lecturerId") Long lecturerId);
}
