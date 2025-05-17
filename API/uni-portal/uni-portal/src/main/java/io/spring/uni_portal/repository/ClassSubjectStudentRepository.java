package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.ClassStudent;
import io.spring.uni_portal.model.ClassSubjectStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassSubjectStudentRepository extends JpaRepository<ClassSubjectStudent, Long> {
    long countByClassStudent_ClassStudentId(Long classStudentId);
    boolean existsByClassStudent_ClassStudentIdAndStudent_UserId(Long classStudentId, Long userId);
    List<ClassSubjectStudent> findByClassStudent_ClassStudentId(Long classStudentId);



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

    // Lấy danh sách sinh viên theo classStudentId và status
    List<ClassSubjectStudent> findByClassStudent_ClassStudentIdAndStatus(Long classStudentId, String status);

    // Đếm số lượng sinh viên theo classStudentId và status
    long countByClassStudent_ClassStudentIdAndStatus(Long classStudentId, String status);

    Optional<ClassSubjectStudent> findByClassStudent_ClassStudentIdAndStudent_UserId(Long classStudentId, Long userId);


}
